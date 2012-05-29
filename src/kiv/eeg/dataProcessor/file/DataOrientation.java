package kiv.eeg.dataProcessor.file;

/**
 * Enumeration of orientation of data.
 * Multiplexed means ch1,t1, ch2,t1, ... (first electrodes then time).
 * Vectorized means ch1,t1, ch1,t2, ... (first time then electrodes).
 * 
 * @author Bazinga
 *
 */
public enum DataOrientation {
	MULTIPLEXED, VECTORIZED
}
