package kiv.eeg.dataProcessor;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.filters.DataProcessor;
import kiv.eeg.dataProcessor.reader.DataReader;
import kiv.eeg.dataProcessor.writer.DataWriter;

public class Runner {
	/** Suffix of .vhdr file. */
	private static final String VHDRSUFFIX = ".vhdr";
	
	/** Instance of class Data. Holds every important information and data, is pushed into filters and returned by them. */ 
	Data data;
	/** Path to a .vhdr file OR to a directory containing .vhdr file(s). 
	 * Can be either file or directory - if directory, program reads every .vhdr file in it.*/
	private String vhdrFilenamePath; 
	

	/** Data reader (must implement DataReader interface), defined in config.xml file.
	 * Usually BvdefReader, that reads from .vhdr, .vmrk and .eeg files. */
	private DataReader dataReader;
	/** List of processors (each pr. must implement DataProcessor interface), that process data. */
	private List<DataProcessor> processors;
	/** Data writers (must implement DataWriter interface) to store data into file(s). */
	private List<DataWriter> writers;
	
	
	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());

	/**
	 * Branching method of Runner.
	 * Checks if vhdrFile is file or directory and starts processing of data defined either by
	 * single .vhdr file or by all .vhdr files in directory.
	 */
	public void run() {
		
		File vhdrFile = new File(vhdrFilenamePath);
		if (vhdrFile.isDirectory()) {					// reads whole directory
			
			String[] fileNames = vhdrFile.list();
			File[] files = vhdrFile.listFiles();
			
			for (int i = 0; i < files.length; i++) {	//For every file check if it is .vhdr file
				if (fileNames[i].endsWith(VHDRSUFFIX)) {	//and if it is,
					log.info("Reading from file: " + files[i].getName());
					read(files[i]);							//start reading from it.
				}
				
			}
			
		} else {										//reads only one file
			read(new File(vhdrFilenamePath));
		}
		
	}
	
	
	/**
	 * Executive method of Runner.
	 * Manages processing of data by passing them into filters and collecting data from them.
	 * At first it reads data defined by one .vhdr file,
	 * then it processes them with filters
	 * and in the end stores them by writer(s) into file(s).
	 * @param file File (.vhdr) to read informations from.
	 */
	private void read(File file) {
		//readers
		log.debug("Loading data from reader: " + dataReader);
		data = dataReader.read(file);
		//processors
		for (DataProcessor pr : processors) {
			log.debug("Processing data with: " + pr);
			data = pr.process(data);
		}
		//writers
		for (DataWriter wr : writers) {
			log.debug("Storing data to " + wr);
			wr.write(data);
		}
		
		data = null; //null the data before using them again for another file
		
	}


	/**
	 * @return the dataReader
	 */
	public DataReader getDataReader() {
		return dataReader;
	}


	/**
	 * @param dataReader the dataReader to set
	 */
	public void setDataReader(DataReader dataReader) {
		this.dataReader = dataReader;
	}


	/**
	 * @return the vhdrFilename
	 */
	public String getVhdrFilenamePath() {
		return vhdrFilenamePath;
	}


	/**
	 * @param vhdrFilename the vhdrFilename to set
	 */
	public void setVhdrFilenamePath(String vhdrFilename) {
		this.vhdrFilenamePath = vhdrFilename;
	}


	/**
	 * @return the processors
	 */
	public List<DataProcessor> getProcessors() {
		return processors;
	}


	/**
	 * @param processors the processors to set
	 */
	public void setProcessors(List<DataProcessor> processors) {
		this.processors = processors;
	}


	/**
	 * @return the writers
	 */
	public List<DataWriter> getWriters() {
		return writers;
	}


	/**
	 * @param writers the writers to set
	 */
	public void setWriters(List<DataWriter> writers) {
		this.writers = writers;
	}


	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}	
	
}
