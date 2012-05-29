package kiv.eeg.dataProcessor.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.other.Marker;
 /**
  * Stores data in BVDEF format - three files: VHDR, VMRK and EEG.
  *
  * @author Bazinga
  */
public class BvdefWriter implements DataWriter {

	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());
	/** Path to a .vhdr file. */
	private String filePath;
	/** Path to a .eeg file. */
	private String eegFilePath;
	/** Filename of .eeg file. */
	private String eegFilename;
	/** Filename of .vmrk file. */
	private String vmrkFilename;
	/** Printwriter that writes data into a file. */
	private PrintWriter pw;
	/** Suffix of a .vhdr file. */
	private static final String VHDRSUFFIX = ".vhdr";
	/** Suffix of a .vmrk file. */
	private static final String VMRKSUFFIX = ".vmrk";
	/** Suffix of a .eeg file. */
	private static final String EEGSUFFIX = ".eeg";
	
	
	/**
	 * Write method that stores data into .vhdr file, .vmrk file and executes BinaryWriter.
	 * 
	 * @param data Data to be stored.
	 */
	@Override
	public void write(Data data) {
		
		eegFilename = data.getVhdrFile().getDataFilename();
		eegFilePath = filePath + eegFilename;
		BinaryWriter bWriter = new BinaryWriter(data);
		bWriter.outputEEG(eegFilePath);
		
		int s = eegFilePath.indexOf(EEGSUFFIX);
		String filePathStripped = eegFilePath.substring(0, s);
		String[] onlyFilename = filePathStripped.split("(\0x20-\0x7E)*/+"); //strip everything (dir name), leave filename
		eegFilename = onlyFilename[onlyFilename.length-1] + EEGSUFFIX;
		vmrkFilename = onlyFilename[onlyFilename.length-1] + VMRKSUFFIX;
		
		//vhdr write
		try {
			pw = new PrintWriter(new File((filePathStripped + VHDRSUFFIX)));
		} catch (FileNotFoundException e) {
			log.error("Could not create output VHDR file. Ending program.");
			System.exit(-1);
		}
		pw.write("Brain Vision Data Exchange Header File Version 1.0\n" +
				"; Data created by the Vision Recorder, processed with KIV/EEG/DataProcessor\n\n");
		
		pw.write("[Common Infos]\n");
		pw.write("Codepage=" + data.getVhdrFile().getCodePage() + "\n");
		pw.write("DataFile=" + eegFilename + "\n");
		pw.write("MarkerFile=" + vmrkFilename + "\n");
		pw.write("DataFormat=" + data.getVhdrFile().getDataFormat() + "\n");
		pw.write("; Data orientation: MULTIPLEXED=ch1,pt1, ch2,pt1 ...\n");
		pw.write("DataOrientation=" + data.getVhdrFile().getDataOrientation() + "\n");
		pw.write("NumberOfChannels=" + data.getVhdrFile().getSelectedElectrodes().length + "\n");
		pw.write("; Sampling interval in microseconds\n");
		pw.write("SamplingInterval=" + data.getVhdrFile().getSamplingInterval() + "\n");
		if(data.getVhdrFile().isAveraged()){
			pw.write("Averaged=YES\nAveragedSegments=" + data.getVmrkFile().getEpochsOriginally() + "\n"); // !!!
		}
		
		pw.write("\n[Binary Infos]\n");
		pw.write("BinaryFormat=" + data.getVhdrFile().getBinaryDataFormat() + "\n\n");
		
		pw.write("[Channel Infos]\n");
		pw.write("; Each entry: Ch<Channel number>=<Name>,<Reference channel name>,\n" +
				"; <Resolution in \"Unit\">,<Unit>, Future extensions..\n" +
				"; Fields are delimited by commas, some fields might be omitted (empty).\n" +
				"; Commas in channel names are coded as \"\\1\".\n");
		
		int[] selEl = data.getVhdrFile().getSelectedElectrodes();
		for(int i = 0; i < selEl.length; i++){
			pw.write("Ch" + (i + 1) + 
					"=" + data.getVhdrFile().getChannelInfoList().getList().get(selEl[i]-1).getName() +
					"," + data.getVhdrFile().getChannelInfoList().getList().get(selEl[i]-1).getRefName() +
					"," + data.getVhdrFile().getChannelInfoList().getList().get(selEl[i]-1).getResUnit() + 
					"," + data.getVhdrFile().getChannelInfoList().getList().get(selEl[i]-1).getUnit() + "\n");
		}
		pw.write("\n\n");
		
		pw.close();
		
		
		
		//vmrk write
		try {
			pw = new PrintWriter(new File((filePathStripped + ".vmrk")));
		} catch (FileNotFoundException e) {
			log.error("Could not create output VMRK file. Ending program.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		pw.write("Brain Vision Data Exchange Marker File, Version 1.0\n\n");
		
		pw.write("[Common Infos]\n");
		pw.write("Codepage=" + data.getVmrkFile().getCodepage() + "\n");
		pw.write("DataFile=" + data.getVmrkFile().getDataFilename() + "\n\n");
		
		pw.write("[Marker Infos]\n");
		pw.write("; Each entry: Mk<Marker number>=<Type>,<Description>,<Position in data points>,\n" +
				"; <Size in data points>, <Channel number (0 = marker is related to all channels)>\n" +
				"; Fields are delimited by commas, some fields might be omitted (empty).\n" +
				"; Commas in type or description text are coded as \"\\1\".\n");
		
		String dateFormat = "yyyyMMddHHmmss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		
	    pw.write("Mk1=New Segment,,1,1,0," + sdf.format(cal.getTime()) + "\n");

		for(Marker mk : data.getVmrkFile().getMarkers()){
			pw.write("Mk" + (mk.getIndex()) + "=" + mk.getType() + "," +
					mk.getDescription() + "," + mk.getPosition() + "," +
					mk.getSize() + "," + mk.getChannelNumber() + "\n");
			
		}

		
		
		pw.close();
		
		log.info("Data (binary) stored to directory: " + filePath);
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

}
