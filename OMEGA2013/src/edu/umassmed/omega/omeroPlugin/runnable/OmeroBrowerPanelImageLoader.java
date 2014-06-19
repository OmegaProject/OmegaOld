/*******************************************************************************
 * Copyright (C) 2014 University of Massachusetts Medical School
 * Alessandro Rigano (Program in Molecular Medicine)
 * Caterina Strambio De Castillia (Program in Molecular Medicine)
 *
 * Created by the Open Microscopy Environment inteGrated Analysis (OMEGA) team: 
 * Alex Rigano, Caterina Strambio De Castillia, Jasmine Clark, Vanni Galli, 
 * Raffaello Giulietti, Loris Grossi, Eric Hunter, Tiziano Leidi, Jeremy Luban, 
 * Ivo Sbalzarini and Mario Valle.
 *
 * Key contacts:
 * Caterina Strambio De Castillia: caterina.strambio@umassmed.edu
 * Alex Rigano: alex.rigano@umassmed.edu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package edu.umassmed.omega.omeroPlugin.runnable;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pojos.DatasetData;
import pojos.ImageData;
import pojos.ProjectData;
import edu.umassmed.omega.commons.OmegaConstants;
import edu.umassmed.omega.omeroPlugin.OmeroGateway;
import edu.umassmed.omega.omeroPlugin.data.OmeroDatasetWrapper;
import edu.umassmed.omega.omeroPlugin.data.OmeroImageWrapper;
import edu.umassmed.omega.omeroPlugin.data.OmeroThumbnailImageInfo;
import edu.umassmed.omega.omeroPlugin.gui.OmeroBrowserPanel;

public class OmeroBrowerPanelImageLoader implements Runnable {

	private final OmeroBrowserPanel browserPanel;
	private final OmeroGateway gateway;
	private final ProjectData projectData;
	private final DatasetData datasetData;
	private final List<ImageData> images;
	private volatile ArrayList<OmeroThumbnailImageInfo> imageInfo;
	private final int imagesToLoad;
	private volatile int imagesLoaded;

	public OmeroBrowerPanelImageLoader(final OmeroBrowserPanel browserPanel,
	        final OmeroGateway gateway, final OmeroDatasetWrapper omeDataset) {
		this.browserPanel = browserPanel;
		this.gateway = gateway;

		this.projectData = omeDataset.getProject();
		this.datasetData = omeDataset.getDatasetData();
		this.images = new ArrayList<ImageData>();
		for (final Object obj : this.datasetData.getImages()) {
			this.images.add((ImageData) obj);
		}

		// this.images = null;
		// try {
		// this.images = new ArrayList<ImageData>(gateway.getImages(
		// this.datasetData, null));
		// } catch (final ServerError e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		this.imageInfo = new ArrayList<OmeroThumbnailImageInfo>();

		this.imagesToLoad = this.images.size();
		this.imagesLoaded = 0;
	}

	public int getImageToLoad() {
		return this.imagesToLoad;
	}

	public int getImageLoaded() {
		return this.imagesLoaded;
	}

	public ArrayList<OmeroThumbnailImageInfo> getImagesInfo() {
		return this.imageInfo;
	}

	@Override
	public void run() {
		boolean error = false;

		if (this.images == null)
			return;

		for (int i = 0; i < this.images.size(); i++) {
			final int currentlyLoading = 1 + this.imagesLoaded;
			this.updateLoadingStatus(currentlyLoading);
			final ImageData imageData = this.images.get(i);

			final Long pixelID = imageData.getDefaultPixels().getId();

			final List<Long> pixelIDs = new ArrayList<Long>();
			pixelIDs.add(pixelID);

			List<BufferedImage> bufferedImages = null;

			try {
				bufferedImages = this.gateway.getThumbnailSet(pixelIDs,
				        OmegaConstants.THUMBNAIL_SIZE);
				final OmeroImageWrapper image = new OmeroImageWrapper(pixelID,
				        imageData.getName(), this.projectData,
				        this.datasetData, imageData);
				this.imageInfo.add(new OmeroThumbnailImageInfo(image,
				        bufferedImages.get(0)));
			} catch (final Exception e) {
				error = true;
			}

			this.imagesLoaded++;
		}

		if (error) {
			JOptionPane.showMessageDialog(null,
			        OmegaConstants.ERROR_LOADING_THE_DS,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.updateLoadingStatus(this.imagesLoaded);
	}

	private void updateLoadingStatus(final int currentlyLoading) {
		final String loadingStatus = currentlyLoading + "/"
		        + OmeroBrowerPanelImageLoader.this.imagesToLoad
		        + "...loaded image(s) for" + this.datasetData.getName();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					OmeroBrowerPanelImageLoader.this.browserPanel
					        .updateLoadingStatus(loadingStatus);
					if (((OmeroBrowerPanelImageLoader.this.imagesLoaded % 10) == 0)
					        || (OmeroBrowerPanelImageLoader.this.imagesLoaded == OmeroBrowerPanelImageLoader.this.imagesToLoad)) {
						OmeroBrowerPanelImageLoader.this.browserPanel
						        .setImages(OmeroBrowerPanelImageLoader.this.imageInfo);
					}
				}
			});
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}