package kiv.eeg.dataProcessor.filters;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.file.BinaryDataFormat;

/**
 * Data processing class.
 * Corrects baseline of each epoch.
 * 
 * @author Bazinga
 *
 */
public class BaselineCorrection implements DataProcessor {
	
	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * Data processing method.
	 * Computes average value from data before stimul and substract it from all values through
	 * the epoch. (this is done for each electrode separately).
	 * That means that the values are shifted closer to "baseline" - zero.
	 * 
	 * @param data Data to process by baseline correction.
	 * @return Corrected data.
	 */
	@Override
	public Data process(Data inputData) {
		int numberOfStimuls = inputData.getData().size(); //number of stimuls
		int numberOfElectrodes = inputData.getData().get(0).length; //number of rows
		int epochLength = inputData.getData().get(0)[0].length; //number of columns
		int millisBeforeStimul = inputData.getVhdrFile().getMillisBeforeStimul(); //time before stimul to calculate from;
		
		ArrayList<double[][]> outputArrayList = new ArrayList<double[][]>();
		
		for(int i = 0; i < numberOfStimuls; i++){						//for each stimul
			
			/* average computing */
			double[] sumField = new double[numberOfElectrodes];			//new field for sum count
			for(int j = 0; j < numberOfElectrodes; j++){				//for each row (electrode)
				for(int k = 0; k < millisBeforeStimul; k++){			//for (usually) 100 millis
					sumField[j] += inputData.getData().get(i)[j][k];			//sum all those numbers!!
				}
				sumField[j] = sumField[j] / millisBeforeStimul; 		//average it
			}
		
			
			
			/* average substracting - "baseline correction" */
			double[][] tempField = new double[numberOfElectrodes][epochLength];	//new field to fill
			for(int j = 0; j < numberOfElectrodes; j++){						//for each row (electrode)
				for(int k = 0; k < epochLength; k++){							//for whole epoch length
					tempField[j][k] =											
						inputData.getData().get(i)[j][k] - sumField[j];			//substract from WHOLE epoch
				}
			}
			outputArrayList.add(tempField);				//add field to arrayList, then...
		}
		
		Data outputData = new Data(outputArrayList,
									inputData.getVmrkFile(),
									inputData.getChannels(),
									inputData.getSelectedElectrodes(),
									inputData.getVhdrFile(),
									inputData.getSelectedEpochs());	//create output data
		
//now the data are at least as Float32		
		outputData.getVhdrFile().setBinaryDataFormat(BinaryDataFormat.IEEE_FLOAT_32);
		log.info("Data successfully processed with Baseline Correction.");
		return outputData;								//and return them!
	}

}
