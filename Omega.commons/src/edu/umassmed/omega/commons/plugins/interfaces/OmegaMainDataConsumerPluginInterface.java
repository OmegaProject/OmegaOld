package edu.umassmed.omega.commons.plugins.interfaces;

import edu.umassmed.omega.commons.data.OmegaData;

public interface OmegaMainDataConsumerPluginInterface {
	public void setMainData(final OmegaData omegaData);

	public OmegaData getMainData();
}
