package kiv.eeg.dataProcessor.writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.file.BinaryDataFormat;
import kiv.eeg.dataProcessor.file.DataOrientation;

/**
 * Binary writer of .eeg file. Executed in BvdefWriter.
 * @author Bazinga
 *
 */
public class BinaryWriter {

	/** Orientation of stored data. */
	DataOrientation dataOrientation;
	/** Determines use of big endian. */
	boolean useBigEndianOrder;
	/** Format of binary data. */
	BinaryDataFormat binaryDataFormat;
	/** List of 2D double array of data to be stored. */
	private ArrayList<double[][]> dataArray;
	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * Constructor of BinaryWriter defined by parameter.
	 * @param data Data to be written.
	 */
	BinaryWriter(Data data) {
		this.dataArray = data.getData();
		this.dataOrientation = data.getVhdrFile().getDataOrientation();
		this.binaryDataFormat = data.getVhdrFile().getBinaryDataFormat();
	}

	/**
	 * Method that writes binary data into file.
	 * @param filepath Path of file where data are stored.
	 */
	public void outputEEG(String filepath) {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
		} catch (FileNotFoundException e) {
			log.error("Could not create output EEG file. Ending program.");
			e.printStackTrace();
		}
		for (int n = 0; n < dataArray.size(); n++) {
			for (int i = 0; i < dataArray.get(n)[0].length; i++) {
				for (int j = 0; j < dataArray.get(n).length; j++) {
					try {
						if(binaryDataFormat == BinaryDataFormat.IEEE_FLOAT_32){
							dos.writeFloat((float)dataArray.get(n)[j][i]);
						}else if(binaryDataFormat == BinaryDataFormat.INT_16){
							dos.writeShort((short)dataArray.get(n)[j][i]);
						}else{
							log.error("Unknown \"BinaryDataFormat\". Data incomplete.");
							return;
						}
						
					} catch (IOException e) {
						log.error("Error while writing binary EEG file. Ending program.");
						System.exit(-1);
					} 
				}
			}
		}
		try {
			dos.close();
		} catch (IOException e) {
			log.error("Couldn't close output file. File may not be complete.");
		}
	}
}
