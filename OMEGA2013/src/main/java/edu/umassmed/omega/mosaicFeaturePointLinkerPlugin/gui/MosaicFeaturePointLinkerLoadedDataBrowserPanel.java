/*******************************************************************************
 * Copyright (C) 2014 University of Massachusetts Medical School Alessandro
 * Rigano (Program in Molecular Medicine) Caterina Strambio De Castillia
 * (Program in Molecular Medicine)
 *
 * Created by the Open Microscopy Environment inteGrated Analysis (OMEGA) team:
 * Alex Rigano, Caterina Strambio De Castillia, Jasmine Clark, Vanni Galli,
 * Raffaello Giulietti, Loris Grossi, Eric Hunter, Tiziano Leidi, Jeremy Luban,
 * Ivo Sbalzarini and Mario Valle.
 *
 * Key contacts: Caterina Strambio De Castillia: caterina.strambio@umassmed.edu
 * Alex Rigano: alex.rigano@umassmed.edu
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package edu.umassmed.omega.mosaicFeaturePointLinkerPlugin.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.umassmed.omega.commons.constants.OmegaGUIConstants;
import edu.umassmed.omega.commons.data.analysisRunElements.OmegaAnalysisRun;
import edu.umassmed.omega.commons.data.analysisRunElements.OmegaParticleDetectionRun;
import edu.umassmed.omega.commons.data.analysisRunElements.OmegaParticleLinkingRun;
import edu.umassmed.omega.commons.data.coreElements.OmegaElement;
import edu.umassmed.omega.commons.data.coreElements.OmegaNamedElement;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaROI;
import edu.umassmed.omega.commons.eventSystem.events.OmegaMessageEvent;
import edu.umassmed.omega.commons.gui.GenericPanel;
import edu.umassmed.omega.commons.gui.checkboxTree.CheckBoxNode;
import edu.umassmed.omega.commons.gui.checkboxTree.CheckBoxStatus;
import edu.umassmed.omega.mosaicFeaturePointLinkerPlugin.MosaicFeaturePointLinkerPluginConstants;

public class MosaicFeaturePointLinkerLoadedDataBrowserPanel extends GenericPanel {

	private static final long serialVersionUID = -7554854467725521545L;

	private final MosaicFeaturePointLinkerPluginPanel plPanel;

	private final Map<String, OmegaElement> nodeMap;
	private final DefaultMutableTreeNode root;

	private JTree dataTree;

	private boolean adjusting = false;

	public MosaicFeaturePointLinkerLoadedDataBrowserPanel(final RootPaneContainer parentContainer,
			final MosaicFeaturePointLinkerPluginPanel plPanel) {
		super(parentContainer);

		this.plPanel = plPanel;

		this.root = new DefaultMutableTreeNode();
		this.root.setUserObject(OmegaGUIConstants.PLUGIN_LOADED_DATA);
		this.nodeMap = new HashMap<String, OmegaElement>();
		// this.updateTree(images);

		this.setLayout(new BorderLayout());

		this.createAndAddWidgets();
		this.addListeners();
	}

	private void createAndAddWidgets() {

		this.dataTree = new JTree(this.root);
		this.dataTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// this.dataTreeBrowser.setRootVisible(false);
		// final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		// this.dataTree.setCellRenderer(renderer);
		// this.dataTree.setCellEditor(new CheckBoxNodeEditor());

		this.dataTree.setEditable(false);

		this.dataTree.expandRow(0);
		this.dataTree.setRootVisible(false);
		// this.dataTree.setEditable(true);

		final JScrollPane scrollPane = new JScrollPane(this.dataTree);
		scrollPane.setBorder(new TitledBorder(
				OmegaGUIConstants.PLUGIN_LOADED_DATA));

		this.add(scrollPane, BorderLayout.CENTER);
	}

	private void addListeners() {
		this.dataTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent event) {
				MosaicFeaturePointLinkerLoadedDataBrowserPanel.this.handleMouseClick(event.getPoint());
			}
		});
		this.dataTree.getModel().addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(final TreeModelEvent event) {
				MosaicFeaturePointLinkerLoadedDataBrowserPanel.this.handleTreeChanged(event);
			}

			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void handleTreeChanged(final TreeModelEvent event) {
		final TreePath parent = event.getTreePath();
		final Object[] children = event.getChildren();
		final DefaultTreeModel model = (DefaultTreeModel) event.getSource();
		this.handleTreeNodeChanged(parent, children, model);
	}

	private void handleMouseClick(final Point clickP) {
		final TreePath path = this.dataTree.getPathForLocation(clickP.x,
				clickP.y);
		this.plPanel.deselectNotListener(this);
		if (path == null) {
			this.plPanel.updateSelectedParticleDetectionRun(null);
			this.plPanel.updateSelectedParticleLinkingRun(null);
			this.deselect();
			return;
		}
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		final String s = node.toString();
		final OmegaElement element = this.nodeMap.get(s);
		if (element instanceof OmegaParticleDetectionRun) {
			final OmegaParticleDetectionRun pd = (OmegaParticleDetectionRun) element;
			final Map<OmegaROI, Map<String, Object>> valuesMap = pd
					.getResultingParticlesValues();
			boolean enabled = true;
			for (final OmegaROI roi : valuesMap.keySet()) {
				final Map<String, Object> values = valuesMap.get(roi);
				final boolean containsM0 = values
						.containsKey(MosaicFeaturePointLinkerPluginConstants.REQUIRED_VALUE_M0);
				final boolean containsM2 = values
						.containsKey(MosaicFeaturePointLinkerPluginConstants.REQUIRED_VALUE_M2);
				if (!containsM0 || !containsM2) {
					this.plPanel
							.updateMessageStatus(new OmegaMessageEvent(
									pd.getName()
											+ " does not contains the required m0 or m2 value to run "
											+ this.plPanel.getPlugin()
													.getName()));
					enabled = false;
				}
				break;
			}
			this.plPanel
					.updateSelectedParticleDetectionRun((OmegaParticleDetectionRun) element);
			this.plPanel.setAddButtonEnabled(enabled);
		} else if (element instanceof OmegaParticleLinkingRun) {
			final DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node
					.getParent();
			final String parentString = parentNode.toString();
			final OmegaElement parentElement = this.nodeMap.get(parentString);
			this.plPanel
					.updateSelectedParticleDetectionRun((OmegaParticleDetectionRun) parentElement);
			this.plPanel
					.updateSelectedParticleLinkingRun((OmegaParticleLinkingRun) element);
		}
	}
	
	public void selectTreeElement(final OmegaNamedElement element) {
		if (this.dataTree.getRowCount() == 0)
			return;
		final String name = element.getName();
		final String string = "[" + element.getElementID() + "] " + name;
		final TreePath path = this.dataTree.getNextMatch(string, 0,
				Position.Bias.Forward);
		this.dataTree.expandPath(path);
		this.dataTree.setSelectionPath(path);
	}

	private void handleTreeNodeChanged(final TreePath parent,
			final Object[] children, final DefaultTreeModel model) {
		if (this.adjusting)
			return;
		this.adjusting = true;

		DefaultMutableTreeNode node;
		CheckBoxNode c; // = (CheckBoxNode)node.getUserObject();
		if ((children != null) && (children.length == 1)) {
			node = (DefaultMutableTreeNode) children[0];
			c = (CheckBoxNode) node.getUserObject();
			final DefaultMutableTreeNode n = (DefaultMutableTreeNode) parent
					.getLastPathComponent();

			model.nodeChanged(n);
		} else {
			node = (DefaultMutableTreeNode) model.getRoot();
			c = (CheckBoxNode) node.getUserObject();
		}

		model.nodeChanged(node);

		this.adjusting = false;

		c.getStatus();
		// TODO update something here
	}

	@Override
	public void updateParentContainer(final RootPaneContainer parent) {
		super.updateParentContainer(parent);
	}

	public void updateTree(final List<OmegaAnalysisRun> analysisRuns,
			final List<OmegaAnalysisRun> loadedAnalysisRuns) {
		this.dataTree.setRootVisible(true);
		
		String s = null;
		final CheckBoxStatus status = CheckBoxStatus.DESELECTED;
		this.root.removeAllChildren();
		((DefaultTreeModel) this.dataTree.getModel()).reload();
		this.nodeMap.clear();
		if (analysisRuns != null) {
			for (final OmegaAnalysisRun analysisRun : analysisRuns) {
				if (!(analysisRun instanceof OmegaParticleDetectionRun)
						|| !loadedAnalysisRuns.contains(analysisRun)) {
					continue;
				}
				final DefaultMutableTreeNode particleDetRunNode = new DefaultMutableTreeNode();
				s = "[" + analysisRun.getElementID() + "] "
						+ analysisRun.getName();
				this.nodeMap.put(s, analysisRun);
				// status = this.loadedData.containsImage(image) ?
				// CheckBoxStatus.SELECTED
				// : CheckBoxStatus.DESELECTED;
				particleDetRunNode.setUserObject(new CheckBoxNode(s, status));
				
				for (final OmegaAnalysisRun innerAnalysisRun : analysisRun
						.getAnalysisRuns()) {
					if (!(innerAnalysisRun instanceof OmegaParticleLinkingRun)
							|| !loadedAnalysisRuns.contains(innerAnalysisRun)) {
						continue;
					}
					final OmegaParticleLinkingRun linkingRun = (OmegaParticleLinkingRun) innerAnalysisRun;
					// TODO pensare se questo e' il sistema migliore per
					// verificare il corretto funzionamento!
					if (!this.plPanel.checkIfThisAlgorithm(linkingRun)) {
						continue;
					}
					final DefaultMutableTreeNode linkingAnalysisRunNode = new DefaultMutableTreeNode();
					s = "[" + linkingRun.getElementID() + "] "
							+ linkingRun.getName();
					this.nodeMap.put(s, linkingRun);
					linkingAnalysisRunNode.setUserObject(new CheckBoxNode(s,
							status));
					particleDetRunNode.add(linkingAnalysisRunNode);
				}
				
				this.root.add(particleDetRunNode);
			}
		}
		this.dataTree.expandRow(0);
		this.dataTree.setRootVisible(false);
		this.dataTree.repaint();
	}

	public void deselect() {
		this.dataTree.setSelectionRow(-1);
	}
}
