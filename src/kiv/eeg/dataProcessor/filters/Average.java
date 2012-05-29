package kiv.eeg.dataProcessor.filters;

import java.util.ArrayList;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.file.BinaryDataFormat;
import kiv.eeg.dataProcessor.file.Vmrk;
import kiv.eeg.dataProcessor.other.Marker;

import org.apache.log4j.Logger;

/**
 * Data processing class.
 * Makes average from number of epochs. Number is defined by user.
 * 
 * @author Bazinga
 *
 */
public class Average implements DataProcessor {
	
	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());
	/** Number of epochs to be averaged into one epoch. */
	private int averagedEpochsNumber;
	
	/** Group of O stimuls. */
	private ArrayList<double[][]> stimulsO;
	/** Group of Q stimuls. */
	private ArrayList<double[][]> stimulsQ;
	/** New, averaged data to return. */
	private ArrayList<double[][]> outputData;
	/** List of new markers to be set into VHDR file. */
	private ArrayList<Marker> markers;

	/**
	 * Data processing method.
	 * At first epochs are divided into O and Q groups. Then it takes specified number of epochs,
	 * sums their values at i and j positions of matrixes and then divides those sums by number of epochs.
	 * Makes new Markers, because data are changed.
	 * 
	 * @param data Data to process by averaging.
	 * @return Averaged data. Contains only a few of (averaged) epochs.
	 */
	public Data process(Data data) {
		stimulsO = new ArrayList<double[][]>();
		stimulsQ = new ArrayList<double[][]>();
		outputData = new ArrayList<double[][]>();
		markers = new ArrayList<Marker>();
		
		Vmrk vmrkFile = data.getVmrkFile();	
		vmrkFile.setEpochsOriginally(vmrkFile.getMarkers().size() - 1);
//		System.out.println("average" + vmrkFile.getMarkers().size() );
		
		for (int i = 0; i < data.getData().size(); i++) {
			int index = (data.getSelectedEpochs()[i] - 1);	//because of index, it has to be "x - 1"
			
			if (vmrkFile.getMarkers().get(index).getDescription().equals("S  2")) {
				stimulsQ.add(data.getData().get(index));
			}else if (vmrkFile.getMarkers().get(index).getDescription().equals("S  1")) {
				stimulsO.add(data.getData().get(index));
			}else{
				log.warn("Selected epoch (" + (i+1) + ") is not \"S  1\" neither \"S  2\" stimul.");
			}
		}
		int avGroupsOfO = (stimulsO.size() / averagedEpochsNumber) + 1; //how many averaged "epochs" of O there will be
		int avGroupsOfQ = (stimulsQ.size() / averagedEpochsNumber) + 1;	//how many averaged "epochs" of Q there will be
		
		double[][] avgData = null;
		
		if(stimulsO.size() == 0){
			log.error("No O-stimuls selected! Ending program.");
			System.exit(-1);
		}else if(stimulsQ.size() == 0){
			log.error("No Q-stimuls selected! Ending program.");
			System.exit(-1);
		}

		
//Q stimuls first		

		avgData = new double[stimulsQ.get(0).length][stimulsQ.get(0)[0].length];

		for (int x = 0; x < avGroupsOfQ; x++) {
			int y = 0;
			for (int n = (x * averagedEpochsNumber); n < stimulsQ.size(); n++) {
				if(y >= averagedEpochsNumber){
					break;
				}
				for (int i = 0; i < stimulsQ.get(0).length; i++) {
					for (int j = 0; j < stimulsQ.get(0)[0].length; j++) {
						avgData[i][j] += stimulsQ.get(n)[i][j];
					}
				}
				y++;
			}
			
			for (int i = 0; i < stimulsQ.get(0).length; i++) {
				for (int j = 0; j < stimulsQ.get(0)[0].length; j++) {
					avgData[i][j] = avgData[i][j] / y;
				}
			}
			outputData.add(avgData);
			
			Marker marker = new Marker(1, "New Segment", "", 1, 1, 0);
			int position = (x  * data.getData().get(0)[0].length) + data.getVhdrFile().getMillisBeforeStimul() + 1;
			marker = new Marker(x+2, "Stimulus", "S  2", position, 1, 0);
			markers.add(marker);
		}
		
		
//O stimuls next		
		avgData = new double[stimulsO.get(0).length][stimulsO.get(0)[0].length];
		
		
		for (int x = 0; x < avGroupsOfO; x++) {
			int y = 0;
			for (int n = (x * averagedEpochsNumber); n < stimulsO.size(); n++) {
				if(y >= averagedEpochsNumber){
					break;
				}
				for (int i = 0; i < stimulsO.get(0).length; i++) {
					for (int j = 0; j < stimulsO.get(0)[0].length; j++) {
						avgData[i][j] += stimulsO.get(n)[i][j];
					}
				}
				y++;
			}
			
			for (int i = 0; i < stimulsO.get(0).length; i++) {
				for (int j = 0; j < stimulsO.get(0)[0].length; j++) {
					avgData[i][j] = avgData[i][j] / y;
				}
			}
			outputData.add(avgData);
			int position = ((x + avGroupsOfQ) * data.getData().get(0)[0].length) + data.getVhdrFile().getMillisBeforeStimul() + 1;
			Marker marker = new Marker(x+avGroupsOfQ+2, "Stimulus", "S  1", position, 1, 0);
			markers.add(marker);
		}
		
		data = new Data(outputData, 
						vmrkFile, 
						data.getChannels(), 
						data.getSelectedElectrodes(), 
						data.getVhdrFile(), 
						data.getSelectedEpochs());
		
//		data.setData(outputData);
		data.getVmrkFile().setMarkers(markers);
		data.getVhdrFile().setAveraged(true);
		data.getVhdrFile().setBinaryDataFormat(BinaryDataFormat.IEEE_FLOAT_32);
		

		log.info("Data successfully processed with Average.");
		markers = null;
		return data;
	}

	/**
	 * @param averagedEpochsNumber the averagedEpochsNumber to set
	 */
	public void setAveragedEpochsNumber(int averagedEpochsNumber) {
		this.averagedEpochsNumber = averagedEpochsNumber;
	}

	/**
	 * @return the averagedEpochsNumber
	 */
	public int getAveragedEpochsNumber() {
		return averagedEpochsNumber;
	}

}
