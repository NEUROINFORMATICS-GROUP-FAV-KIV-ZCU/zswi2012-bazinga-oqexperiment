package kiv.eeg.dataProcessor.reader;

import java.io.File;

import kiv.eeg.dataProcessor.Data;

/** 
 * Data reading class.
 * Has one method read() to implement.
 * 
 * @author Bazinga
 *
 */
public interface DataReader {

	/**
	 * Method needed to implement. Reads data as the user specifies and returns them. 
	 * @param file BVDEF .vhdr file from which to read data.
	 * @return EEG data read from file.
	 */
	Data read(File file);
}
