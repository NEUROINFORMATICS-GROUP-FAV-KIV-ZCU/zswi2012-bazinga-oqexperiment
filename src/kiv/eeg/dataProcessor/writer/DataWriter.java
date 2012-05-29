package kiv.eeg.dataProcessor.writer;

import kiv.eeg.dataProcessor.Data;

/** 
 * Interface for data writing.  
 * @author Bazinga
 *
 */
public interface DataWriter {

	/**
	 * Method that writes data in parameter.
	 * @param data Data to be stored.
	 */
	public void write(Data data);

}
