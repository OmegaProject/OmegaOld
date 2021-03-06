package edu.umassmed.omega.omegaTrackingMeasuresIntensityPlugin.runnable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import edu.umassmed.omega.commons.OmegaLogFileManager;
import edu.umassmed.omega.commons.constants.GraphLabelConstants;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaROI;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaSegment;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaSegmentationTypes;
import edu.umassmed.omega.commons.data.trajectoryElements.OmegaTrajectory;
import edu.umassmed.omega.commons.runnable.StatsGraphProducer;
import edu.umassmed.omega.omegaTrackingMeasuresIntensityPlugin.gui.OmegaTrackingMeasuresIntensityGraphPanel;

public class OmegaTrackingMeasuresIntensityGraphProducer extends StatsGraphProducer {

	private final OmegaTrackingMeasuresIntensityGraphPanel intensityGraphPanel;

	private final int peakMeanBgSnrOption, minMeanMaxOption;
	private final boolean isTimepointsGraph;
	private final int maxT;

	private final Map<OmegaSegment, Double[]> peakSignalsMap;
	private final Map<OmegaSegment, Double[]> centroidSignalsMap;

	private final Map<OmegaROI, Double> peakSignalsLocMap;
	private final Map<OmegaROI, Double> centroidSignalsLocMap;
	
	// SNR related START
	private final Map<OmegaSegment, Double[]> meanSignalsMap;
	private final Map<OmegaSegment, Double[]> backgroundsMap;
	private final Map<OmegaSegment, Double[]> noisesMap;
	private final Map<OmegaSegment, Double[]> areasMap;
	private final Map<OmegaSegment, Double[]> snrsMap;

	private final Map<OmegaROI, Double> meanSignalsLocMap;
	private final Map<OmegaROI, Double> backgroundsLocMap;
	private final Map<OmegaROI, Double> noisesLocMap;
	private final Map<OmegaROI, Double> areasLocMap;
	private final Map<OmegaROI, Double> snrsLocMap;
	// SNR related END

	private boolean itsLocal;

	public OmegaTrackingMeasuresIntensityGraphProducer(final OmegaTrackingMeasuresIntensityGraphPanel intensityGraphPanel,
			final int graphType, final int peakMeanBgSnrOption,
			final int minMeanMaxOption, final boolean isTimepointsGraph,
			final int maxT,
			final Map<OmegaTrajectory, List<OmegaSegment>> segmentsMap,
			final OmegaSegmentationTypes segmTypes,
			final Map<OmegaSegment, Double[]> peakSignalsMap,
			final Map<OmegaSegment, Double[]> centroidSignalsMap,
			final Map<OmegaROI, Double> peakSignalsLocMap,
			final Map<OmegaROI, Double> centroidSignalsLocMap,
			final Map<OmegaSegment, Double[]> meanSignalsMap,
			final Map<OmegaSegment, Double[]> backgroundsMap,
			final Map<OmegaSegment, Double[]> noisesMap,
			final Map<OmegaSegment, Double[]> areasMap,
			final Map<OmegaSegment, Double[]> snrsMap,
			final Map<OmegaROI, Double> meanSignalsLocMap,
			final Map<OmegaROI, Double> backgroundsLocMap,
			final Map<OmegaROI, Double> noisesLocMap,
			final Map<OmegaROI, Double> areasLocMap,
			final Map<OmegaROI, Double> snrsLocMap, final int lineSize,
			final int shapeSize) {
		super(graphType, segmentsMap, segmTypes, lineSize, shapeSize);
		this.intensityGraphPanel = intensityGraphPanel;
		this.peakMeanBgSnrOption = peakMeanBgSnrOption;
		this.minMeanMaxOption = minMeanMaxOption;
		this.isTimepointsGraph = isTimepointsGraph;
		this.maxT = maxT;
		this.peakSignalsMap = peakSignalsMap;
		this.centroidSignalsMap = centroidSignalsMap;
		this.peakSignalsLocMap = peakSignalsLocMap;
		this.centroidSignalsLocMap = centroidSignalsLocMap;
		this.meanSignalsMap = meanSignalsMap;
		this.backgroundsMap = backgroundsMap;
		this.noisesMap = noisesMap;
		this.areasMap = areasMap;
		this.snrsMap = snrsMap;
		this.meanSignalsLocMap = meanSignalsLocMap;
		this.backgroundsLocMap = backgroundsLocMap;
		this.noisesLocMap = noisesLocMap;
		this.areasLocMap = areasLocMap;
		this.snrsLocMap = snrsLocMap;

		this.itsLocal = true;
	}

	// public OmegaTrackingMeasuresIntensityGraphProducer(final OmegaTrackingMeasuresIntensityGraphPanel intensityGraphPanel,
	// final int graphType, final int peakMeanBgSnrOption, final int maxT,
	// final Map<OmegaTrajectory, List<OmegaSegment>> segmentsMap,
	// final OmegaSegmentationTypes segmTypes) {
	// super(graphType, segmentsMap, segmTypes);
	// this.intensityGraphPanel = intensityGraphPanel;
	// this.peakMeanBgSnrOption = peakMeanBgSnrOption;
	// this.minMeanMaxOption = -1;
	// this.isTimepointsGraph = true;
	// this.maxT = maxT;
	// this.peakSignalMap = null;
	// this.centroidSignalsMap = null;
	// this.meanSignalsMap = null;
	// this.noisesMap = null;
	// this.areasMap = null;
	// this.snrsMap = null;
	// }

	@Override
	public void run() {
		this.itsLocal = false;
		this.doRun();
	}

	@Override
	public void doRun() {
		super.doRun();
		if (this.isTimepointsGraph) {
			this.prepareTimepointsGraph(this.maxT);
		} else {
			this.prepareTracksGraph(true);
		}
		this.updateStatus(true);
	}

	@Override
	public String getTitle() {
		String title = "";
		switch (this.minMeanMaxOption) {
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MIN:
				title += "Min ";
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MEAN:
				title += "Avg ";
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MAX:
				title += "Max ";
				break;
			default:
				break;
		}
		switch (this.peakMeanBgSnrOption) {
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_BACKGROUND:
				title += GraphLabelConstants.GRAPH_NAME_BACKGROUND;
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_SNR:
				title += GraphLabelConstants.GRAPH_NAME_SNR;
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_AREA:
				title += GraphLabelConstants.GRAPH_NAME_AREA;
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_NOISE:
				title += GraphLabelConstants.GRAPH_NAME_NOISE;
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MEAN_SIGNAL:
				title += GraphLabelConstants.GRAPH_NAME_INT_MEAN;
				break;
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_CENTROID_SIGNAL:
				title += GraphLabelConstants.GRAPH_NAME_INT_CENT;
				break;
			default:
				title += GraphLabelConstants.GRAPH_NAME_INT_PEAK;
		}
		return title;
	}

	@Override
	public String getYAxisTitle() {
		switch (this.peakMeanBgSnrOption) {
			case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_AREA:
				return GraphLabelConstants.GRAPH_LAB_Y_PIX;
			default:
				return GraphLabelConstants.GRAPH_LAB_Y_INT;
		}
	}

	@Override
	protected Double[] getValue(final OmegaSegment segment, final OmegaROI roi) {
		Double[] values = null;
		final Double[] value = new Double[1];
		value[0] = null;
		if (!this.isTimepointsGraph) {
			switch (this.peakMeanBgSnrOption) {
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_BACKGROUND:
					values = this.backgroundsMap.get(segment);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_AREA:
					values = this.areasMap.get(segment);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_SNR:
					values = this.snrsMap.get(segment);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_NOISE:
					values = this.noisesMap.get(segment);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MEAN_SIGNAL:
					values = this.meanSignalsMap.get(segment);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_CENTROID_SIGNAL:
					values = this.centroidSignalsMap.get(segment);
					break;
				default:
					values = this.peakSignalsMap.get(segment);
			}
			if (values != null) {
				value[0] = values[this.minMeanMaxOption];
			}
		} else {
			switch (this.peakMeanBgSnrOption) {
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_BACKGROUND:
					value[0] = this.backgroundsLocMap.get(roi);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_AREA:
					value[0] = this.areasLocMap.get(roi);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_SNR:
					value[0] = this.snrsLocMap.get(roi);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_NOISE:
					value[0] = this.noisesLocMap.get(roi);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_MEAN_SIGNAL:
					value[0] = this.meanSignalsLocMap.get(roi);
					break;
				case OmegaTrackingMeasuresIntensityGraphPanel.OPTION_CENTROID_SIGNAL:
					value[0] = this.centroidSignalsLocMap.get(roi);
					break;
				default:
					value[0] = this.peakSignalsLocMap.get(roi);
			}
		}
		return value;
	}

	@Override
	public void updateStatus(final boolean ended) {
		if (this.itsLocal) {
			this.intensityGraphPanel.updateStatus(this.getCompleted(), ended);
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						OmegaTrackingMeasuresIntensityGraphProducer.this.intensityGraphPanel.updateStatus(
								OmegaTrackingMeasuresIntensityGraphProducer.this.getCompleted(), ended);
					}
				});
			} catch (final InvocationTargetException | InterruptedException ex) {
				OmegaLogFileManager.handleUncaughtException(ex, true);
			}
		}
	}
}
