package edu.umassmed.omega.commons.data.analysisRunElements;

import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.umassmed.omega.commons.data.coreElements.OmegaExperimenter;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaSegment;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaTrajectory;

public class OmegaTrackingMeasuresMobilityRun extends OmegaTrackingMeasuresRun {

	// Mobility
	private final Map<OmegaSegment, List<Double>> distancesMap;
	private final Map<OmegaSegment, List<Double>> displacementsMap;
	private final Map<OmegaSegment, Double> maxDisplacementesMap;
	private final Map<OmegaSegment, Integer> totalTimeTraveledMap;
	private final Map<OmegaSegment, List<Double>> confinementRatioMap;
	private final Map<OmegaSegment, List<Double[]>> anglesAndDirectionalChangesMap;

	public OmegaTrackingMeasuresMobilityRun(
	        final OmegaExperimenter owner,
	        final OmegaRunDefinition algorithmSpec,
			final Map<OmegaTrajectory, List<OmegaSegment>> segments,
	        final Map<OmegaSegment, List<Double>> distancesMap,
	        final Map<OmegaSegment, List<Double>> displacementsMap,
	        final Map<OmegaSegment, Double> maxDisplacementesMap,
	        final Map<OmegaSegment, Integer> totalTimeTraveledMap,
	        final Map<OmegaSegment, List<Double>> confinementRatioMap,
	        final Map<OmegaSegment, List<Double[]>> anglesAndDirectionalChangesMap) {
		super(owner, algorithmSpec,
				AnalysisRunType.OmegaTrackingMeasuresMobilityRun, segments,
		        TrackingMeasuresType.Mobility);
		this.distancesMap = distancesMap;
		this.displacementsMap = displacementsMap;
		this.maxDisplacementesMap = maxDisplacementesMap;
		this.totalTimeTraveledMap = totalTimeTraveledMap;
		this.confinementRatioMap = confinementRatioMap;
		this.anglesAndDirectionalChangesMap = anglesAndDirectionalChangesMap;
	}

	public OmegaTrackingMeasuresMobilityRun(
	        final OmegaExperimenter owner,
	        final OmegaRunDefinition algorithmSpec,
	        final String name,
			final Map<OmegaTrajectory, List<OmegaSegment>> segments,
	        final Map<OmegaSegment, List<Double>> distancesMap,
	        final Map<OmegaSegment, List<Double>> displacementsMap,
	        final Map<OmegaSegment, Double> maxDisplacementesMap,
	        final Map<OmegaSegment, Integer> totalTimeTraveledMap,
	        final Map<OmegaSegment, List<Double>> confinementRatioMap,
	        final Map<OmegaSegment, List<Double[]>> anglesAndDirectionalChangesMap) {
		super(owner, algorithmSpec,
				AnalysisRunType.OmegaTrackingMeasuresMobilityRun, name,
				segments, TrackingMeasuresType.Mobility);
		this.distancesMap = distancesMap;
		this.displacementsMap = displacementsMap;
		this.maxDisplacementesMap = maxDisplacementesMap;
		this.totalTimeTraveledMap = totalTimeTraveledMap;
		this.confinementRatioMap = confinementRatioMap;
		this.anglesAndDirectionalChangesMap = anglesAndDirectionalChangesMap;
	}

	public OmegaTrackingMeasuresMobilityRun(
	        final OmegaExperimenter owner,
	        final OmegaRunDefinition algorithmSpec,
	        final Date timeStamps,
	        final String name,
			final Map<OmegaTrajectory, List<OmegaSegment>> segments,
	        final Map<OmegaSegment, List<Double>> distancesMap,
	        final Map<OmegaSegment, List<Double>> displacementsMap,
	        final Map<OmegaSegment, Double> maxDisplacementesMap,
	        final Map<OmegaSegment, Integer> totalTimeTraveledMap,
	        final Map<OmegaSegment, List<Double>> confinementRatioMap,
	        final Map<OmegaSegment, List<Double[]>> anglesAndDirectionalChangesMap) {
		super(owner, algorithmSpec,
				AnalysisRunType.OmegaTrackingMeasuresMobilityRun, timeStamps,
				name, segments, TrackingMeasuresType.Mobility);
		this.distancesMap = distancesMap;
		this.displacementsMap = displacementsMap;
		this.maxDisplacementesMap = maxDisplacementesMap;
		this.totalTimeTraveledMap = totalTimeTraveledMap;
		this.confinementRatioMap = confinementRatioMap;
		this.anglesAndDirectionalChangesMap = anglesAndDirectionalChangesMap;
	}

	public Map<OmegaSegment, List<Double>> getDistancesResults() {
		return this.distancesMap;
	}

	public Map<OmegaSegment, List<Double>> getDisplacementsResults() {
		return this.displacementsMap;
	}

	public Map<OmegaSegment, Double> getMaxDisplacementsResults() {
		return this.maxDisplacementesMap;
	}

	public Map<OmegaSegment, Integer> getTotalTimeTraveledResults() {
		return this.totalTimeTraveledMap;
	}

	public Map<OmegaSegment, List<Double>> getConfinementRatioResults() {
		return this.confinementRatioMap;
	}

	public Map<OmegaSegment, List<Double[]>> getAnglesAndDirectionalChangesResults() {
		return this.anglesAndDirectionalChangesMap;
	}
}