package kiv.eeg.dataProcessor.filters;

import kiv.eeg.dataProcessor.Data;

/** 
 * Data processing interface.
 * Has one method to implement - process().
 * 
 * @author Bazinga
 *
 */
public interface DataProcessor {

	/**
	 * Processing method.
	 * This method processes the data as user needs.
	 * @param data Data to process
	 * @return Processed data.
	 */
	public Data process(Data data);
}
