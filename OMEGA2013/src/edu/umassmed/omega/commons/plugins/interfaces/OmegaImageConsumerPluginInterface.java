package edu.umassmed.omega.commons.plugins.interfaces;

import java.util.List;

import edu.umassmed.omega.data.coreElements.OmegaImage;

public interface OmegaImageConsumerPluginInterface {
	public List<OmegaImage> getLoadedImages();

	public void setLoadedImages(final List<OmegaImage> images);
}
