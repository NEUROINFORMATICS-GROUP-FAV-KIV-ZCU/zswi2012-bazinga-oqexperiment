package kiv.eeg.dataProcessor.filters;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.file.BinaryDataFormat;
import pilsner.fir.FIRFilter;
import pilsner.fir.Filter.FilterTypes;
import pilsner.signalwindows.WindowFactory.WINDOWS;
import pilsner.utils.math.Convolution;

/**
 * Data processing class. (Created by 3rd party. Contact author for more info.)
 * FIR Filters process the data to filter unwanted frequencies from samples.
 *  
 * Original text:
 * Hlavni trida pro ukazku FIR filtru.
 * @author Michal Nykl, 2008
 */
public class DataFilter implements DataProcessor{
	
	private static int filterOrder;
	private static FilterTypes filterType;
	private static WINDOWS window;
	private static double bandPassFreqFrom;
	private static double bandPassFreqTo;
	private static double samplingFrequency;
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * Data filtration. 3rd Party code.
	 * @param args Nepredpoklada vstupni parametry
	 */
	public Data process(Data inputData) {
		Data outputData = null;
		
		DataFilter.setSamplingFrequency(inputData.getVhdrFile().getSamplingInterval());
		
		// zadani hodnot filtru
		FIRFilter filtr = new FIRFilter(filterOrder, filterType, window,
				bandPassFreqFrom, bandPassFreqTo, samplingFrequency);
		// vytvoreni filtru
		filtr.firFilterDesign();
		double[] filterCoeff = filtr.getAllFIRFilterCoeff();
		
		log.debug("FIR filter coeff.:");
		log.debug(filtr.allFIRFilterCoeffToString("\n"));
		
		log.debug("Signal Ratio: "+ filtr.getSignalRatio() +" [dB]");

		
		// zjistit dylku Listu
		// for cyklus pro kazdej prvek listu
		// for pro kazdou radku pole
		// zavolani per. convoluce na radku
		
		int numberOfStimuls = inputData.getData().size();
		int numberOfElectrodes = inputData.getData().get(0).length;
		int epochLength = inputData.getData().get(0)[0].length;
		
		ArrayList<double[][]> outputArrayList = new ArrayList<double[][]>();
		
		for(int i = 0; i < numberOfStimuls; i++){
			double[][] temporaryField = new double[numberOfElectrodes][epochLength];
			for(int j = 0; j < numberOfElectrodes; j++){
				temporaryField[j] = Convolution.periodicConvolution(inputData.getData().get(i)[j], filterCoeff);
			}
			outputArrayList.add(temporaryField);
		}
		
		
		
		outputData = new Data(outputArrayList,
				inputData.getVmrkFile(),
				inputData.getChannels(),
				inputData.getSelectedElectrodes(), 
				inputData.getVhdrFile(),
				inputData.getSelectedEpochs());
		outputData.getVhdrFile().setBinaryDataFormat(BinaryDataFormat.IEEE_FLOAT_32);
		
		log.info("Data successfully processed with FIR Filter.");
		return outputData;
	}

	public static int getFilterOrder() {
		return filterOrder;
	}
	
	//pro konfiguracni soubor musi byt setry NESTATICKE ve tvaru set<atribut>, 
	// staticke setry zmena nazvu
	public void setFilterOrder(int filterOrder) {
		DataFilter.filterOrder = filterOrder;
	}
	
	public static void setFilterOrder2(int filterOrder) {
		DataFilter.filterOrder = filterOrder;
	}
	

	
	public static FilterTypes getFilterType() {
		return filterType;
	}

	public void setFilterType(String enumText) {
		DataFilter.filterType = FilterTypes.valueOf(enumText);
	}
	
	public static void setFilterType(FilterTypes filterType) {
		DataFilter.filterType = filterType;
	}

	
	public static WINDOWS getWindow() {
		return window;
	}

	public void setWindow(String enumText) {
		DataFilter.window = WINDOWS.valueOf(enumText);
	}
	
	public static void setWindow(WINDOWS window) {
		DataFilter.window = window;
	}

	
	public static double getBandPassFreqFrom() {
		return bandPassFreqFrom;
	}

	public void setBandPassFreqFrom(double bandPassFreqFrom) {
		DataFilter.bandPassFreqFrom = bandPassFreqFrom;
	}
	
	public static void setBandPassFreqFrom2(double bandPassFreqFrom) {
		DataFilter.bandPassFreqFrom = bandPassFreqFrom;
	}

	
	public static double getBandPassFreqTo() {
		return bandPassFreqTo;
	}
	
	public void setBandPassFreqTo(double bandPassFreqTo) {
		DataFilter.bandPassFreqTo = bandPassFreqTo;
	}
	
	public static void setBandPassFreqTo2(double bandPassFreqTo) {
		DataFilter.bandPassFreqTo = bandPassFreqTo;
	}
	

	
	public static double getSamplingFrequency() {
		return samplingFrequency;
	}
	
	public static void setSamplingFrequency(double samplingFrequency) {
		DataFilter.samplingFrequency = samplingFrequency;
	}
	
	
}

