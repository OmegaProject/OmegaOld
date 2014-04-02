package edu.umassmed.omega.omeroPlugin;

import javax.swing.RootPaneContainer;

import edu.umassmed.omega.commons.OmegaLoaderPlugin;
import edu.umassmed.omega.commons.exceptions.MissingOmegaData;
import edu.umassmed.omega.commons.gui.GenericPluginPanel;
import edu.umassmed.omega.dataNew.OmegaData;
import edu.umassmed.omega.omeroPlugin.gui.OmeroPluginPanel;

public class OmeroPlugin extends OmegaLoaderPlugin {

	private OmeroPluginPanel panel;

	public OmeroPlugin() {
		super(new OmeroGateway());
		this.panel = null;
	}

	@Override
	public String getName() {
		return "Omero Browser";
	}

	@Override
	public GenericPluginPanel createNewPanel(final RootPaneContainer parent,
	        final int index) throws MissingOmegaData {

		final OmegaData omegaData = this.getOmegaData();
		if (omegaData == null)
			throw new MissingOmegaData(this);

		this.panel = new OmeroPluginPanel(parent, this,
		        (OmeroGateway) this.getGateway(), omegaData, index);

		return this.panel;
	}

	@Override
	public void run() {
		//
	}
}
