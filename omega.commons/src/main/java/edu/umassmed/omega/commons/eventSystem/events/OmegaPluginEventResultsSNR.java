/*******************************************************************************
 * Copyright (C) 2014 University of Massachusetts Medical School
 * Alessandro Rigano (Program in Molecular Medicine)
 * Caterina Strambio De Castillia (Program in Molecular Medicine)
 *
 * Created by the Open Microscopy Environment inteGrated Analysis (OMEGA) team:
 * Alex Rigano, Caterina Strambio De Castillia, Jasmine Clark, Vanni Galli,
 * Raffaello Giulietti, Loris Grossi, Eric Hunter, Tiziano Leidi, Jeremy Luban,
 * Ivo ErrorIndex and Mario Valle.
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
package edu.umassmed.omega.commons.eventSystem.events;

import java.util.List;
import java.util.Map;

import edu.umassmed.omega.commons.data.analysisRunElements.OmegaParameter;
import edu.umassmed.omega.commons.data.coreElements.OmegaElement;
import edu.umassmed.omega.commons.data.coreElements.OmegaPlane;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaROI;
import edu.umassmed.omega.commons.plugins.OmegaPlugin;

public class OmegaPluginEventResultsSNR extends OmegaPluginEventAlgorithm {

	private final Map<OmegaPlane, Double> resultingImageBGR;
	private final Map<OmegaPlane, Double> resultingImageNoise;
	private final Map<OmegaPlane, Double> resultingImageAvgSNR;
	private final Map<OmegaPlane, Double> resultingImageMinSNR;
	private final Map<OmegaPlane, Double> resultingImageMaxSNR;
	private final Map<OmegaPlane, Double> resultingImageAvgErrorIndexSNR;
	private final Map<OmegaPlane, Double> resultingImageMinErrorIndexSNR;
	private final Map<OmegaPlane, Double> resultingImageMaxErrorIndexSNR;
	private final Map<OmegaROI, Integer> resultingLocalCenterSignals;
	private final Map<OmegaROI, Double> resultingLocalMeanSignals;
	private final Map<OmegaROI, Integer> resultingLocalSignalSizes;
	private final Map<OmegaROI, Integer> resultingLocalPeakSignals;
	private final Map<OmegaROI, Double> resultingLocalNoises;
	private final Map<OmegaROI, Double> resultingLocalSNRs;
	private final Map<OmegaROI, Double> resultingLocalErrorIndexSNRs;

	public OmegaPluginEventResultsSNR(final OmegaElement element,
	        final List<OmegaParameter> params,
	        final Map<OmegaPlane, Double> resultingImageBGR,
	        final Map<OmegaPlane, Double> resultingImageNoise,
	        final Map<OmegaPlane, Double> resultingImageAverageSNR,
	        final Map<OmegaPlane, Double> resultingImageMinimumSNR,
	        final Map<OmegaPlane, Double> resultingImageMaximumSNR,
	        final Map<OmegaPlane, Double> resultingImageAverageErrorIndexSNR,
	        final Map<OmegaPlane, Double> resultingImageMinimumErrorIndexSNR,
	        final Map<OmegaPlane, Double> resultingImageMaximumErrorIndexSNR,
	        final Map<OmegaROI, Integer> resultingLocalCenterSignal,
	        final Map<OmegaROI, Double> resultingLocalMeanSignals,
	        final Map<OmegaROI, Integer> resultingLocalSignalSizes,
	        final Map<OmegaROI, Integer> resultingLocalPeakSignals,
	        final Map<OmegaROI, Double> resultingLocalNoises,
	        final Map<OmegaROI, Double> resultingLocalSNRs,
	        final Map<OmegaROI, Double> resultingLocalErrorIndexSNRs) {
		this(null, element, params, resultingImageBGR, resultingImageNoise,
		        resultingImageAverageSNR, resultingImageMinimumSNR,
		        resultingImageMaximumSNR, resultingImageAverageErrorIndexSNR,
		        resultingImageMinimumErrorIndexSNR,
				resultingImageMaximumErrorIndexSNR, resultingLocalCenterSignal,
				resultingLocalMeanSignals, resultingLocalSignalSizes,
				resultingLocalPeakSignals, resultingLocalNoises,
				resultingLocalSNRs, resultingLocalErrorIndexSNRs);
	}

	public OmegaPluginEventResultsSNR(final OmegaPlugin source,
	        final OmegaElement element, final List<OmegaParameter> params,
	        final Map<OmegaPlane, Double> resultingImageBGR,
	        final Map<OmegaPlane, Double> resultingImageNoise,
	        final Map<OmegaPlane, Double> resultingImageAverageSNR,
	        final Map<OmegaPlane, Double> resultingImageMinimumSNR,
	        final Map<OmegaPlane, Double> resultingImageMaximumSNR,
	        final Map<OmegaPlane, Double> resultingImageAverageErrorIndexSNR,
	        final Map<OmegaPlane, Double> resultingImageMinimumErrorIndexSNR,
	        final Map<OmegaPlane, Double> resultingImageMaximumErrorIndexSNR,
	        final Map<OmegaROI, Integer> resultingLocalCenterSignal,
	        final Map<OmegaROI, Double> resultingLocalMeanSignals,
	        final Map<OmegaROI, Integer> resultingLocalSignalSizes,
	        final Map<OmegaROI, Integer> resultingLocalPeakSignals,
	        final Map<OmegaROI, Double> resultingLocalNoises,
	        final Map<OmegaROI, Double> resultingLocalSNRs,
			final Map<OmegaROI, Double> resultingLocalErrorIndexSNRs) {
		super(source, element, params);

		this.resultingImageBGR = resultingImageBGR;
		this.resultingImageNoise = resultingImageNoise;
		this.resultingImageAvgSNR = resultingImageAverageSNR;
		this.resultingImageMinSNR = resultingImageMinimumSNR;
		this.resultingImageMaxSNR = resultingImageMaximumSNR;
		this.resultingImageAvgErrorIndexSNR = resultingImageAverageErrorIndexSNR;
		this.resultingImageMinErrorIndexSNR = resultingImageMinimumErrorIndexSNR;
		this.resultingImageMaxErrorIndexSNR = resultingImageMaximumErrorIndexSNR;
		this.resultingLocalCenterSignals = resultingLocalCenterSignal;
		this.resultingLocalMeanSignals = resultingLocalMeanSignals;
		this.resultingLocalSignalSizes = resultingLocalSignalSizes;
		this.resultingLocalPeakSignals = resultingLocalPeakSignals;
		this.resultingLocalNoises = resultingLocalNoises;
		this.resultingLocalSNRs = resultingLocalSNRs;
		this.resultingLocalErrorIndexSNRs = resultingLocalErrorIndexSNRs;
	}

	public Map<OmegaPlane, Double> getResultingImageBGR() {
		return this.resultingImageBGR;
	}

	public Map<OmegaPlane, Double> getResultingImageNoise() {
		return this.resultingImageNoise;
	}

	public Map<OmegaPlane, Double> getResultingImageAverageSNR() {
		return this.resultingImageAvgSNR;
	}

	public Map<OmegaPlane, Double> getResultingImageMinimumSNR() {
		return this.resultingImageMinSNR;
	}

	public Map<OmegaPlane, Double> getResultingImageMaximumSNR() {
		return this.resultingImageMaxSNR;
	}

	public Map<OmegaPlane, Double> getResultingImageAverageErrorIndexSNR() {
		return this.resultingImageAvgErrorIndexSNR;
	}

	public Map<OmegaPlane, Double> getResultingImageMinimumErrorIndexSNR() {
		return this.resultingImageMinErrorIndexSNR;
	}

	public Map<OmegaPlane, Double> getResultingImageMaximumErrorIndexSNR() {
		return this.resultingImageMaxErrorIndexSNR;
	}

	public Map<OmegaROI, Integer> getResultingLocalCenterSignals() {
		return this.resultingLocalCenterSignals;
	}

	public Map<OmegaROI, Double> getResultingLocalMeanSignals() {
		return this.resultingLocalMeanSignals;
	}

	public Map<OmegaROI, Integer> getResultingLocalSignalSizes() {
		return this.resultingLocalSignalSizes;
	}

	public Map<OmegaROI, Integer> getResultingLocalPeakSignals() {
		return this.resultingLocalPeakSignals;
	}

	public Map<OmegaROI, Double> getResultingLocalNoises() {
		return this.resultingLocalNoises;
	}

	public Map<OmegaROI, Double> getResultingLocalSNRs() {
		return this.resultingLocalSNRs;
	}

	public Map<OmegaROI, Double> getResultingLocalErrorIndexSNRs() {
		return this.resultingLocalErrorIndexSNRs;
	}
}