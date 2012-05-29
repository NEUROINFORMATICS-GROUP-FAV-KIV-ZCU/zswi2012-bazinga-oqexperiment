package kiv.eeg.dataProcessor.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;

/**
 * Writer class that writes data as ASCII .txt file.
 * @author Bazinga
 *
 */
public class TextWriter implements DataWriter {

	/** Path to a .txt file. */
	private String filePath;
	/** Name of a .txt file. */
	private String fileName;
	/** Separator between numbers. */
	private String numberSeparator;

	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());
	
	/* replace w/ ...? something else... */
	PrintWriter pw;

	/**
	 * Writing method that stores data into a .txt file.
	 * @param data Data to be stored.
	 */
	@Override
	public void write(Data data) {
		String[] file = data.getVhdrFile().getDataFilename().split(".eeg");
		fileName = filePath + file[0] + ".txt";
		try {
			pw = new PrintWriter(new File(fileName));

			int dataSize = data.getData().size();
			
			for (int n = 0; n < dataSize; n++) {
				for (int j = 0; j < data.getData().get(n)[0].length; j++) {
					for (int i = 0; i < data.getData().get(n).length; i++) {
						pw.write("" + String.valueOf((float)data.getData().get(n)[i][j])
								+ numberSeparator);
					}
				}
			}
			

		} catch (FileNotFoundException e) {
			log.error("Could not create output TXT file. Ending program.");
			System.exit(-1);
		} finally {
			pw.close();
		}
		log.info("Data (text) stored to directory: " + filePath);
	}
	
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param numberSeparator the numberSeparator to set
	 */
	public void setNumberSeparator(String numberSeparator) {
		this.numberSeparator = numberSeparator;
	}

	/**
	 * @return the numberSeparator
	 */
	public String getNumberSeparator() {
		return numberSeparator;
	}



}
