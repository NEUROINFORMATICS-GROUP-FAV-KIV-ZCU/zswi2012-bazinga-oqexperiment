package kiv.eeg.dataProcessor.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.Data;
import kiv.eeg.dataProcessor.file.BinaryDataFormat;
import kiv.eeg.dataProcessor.file.DataFormat;
import kiv.eeg.dataProcessor.file.DataOrientation;
import kiv.eeg.dataProcessor.file.Vhdr;
import kiv.eeg.dataProcessor.file.Vmrk;
import kiv.eeg.dataProcessor.other.ChannelInfo;
import kiv.eeg.dataProcessor.other.ChannelInfoList;
import kiv.eeg.dataProcessor.other.Marker;

/**
 * Main reading class.
 * Reads from three types of files. VHDR, VMRK and EEG file.
 * 
 * @author Bazinga
 *
 */
public class BvdefReader implements DataReader {
	
	/** For logging purposes. */
	private static Logger log = Logger.getLogger(Logger.class);
	/** String of selected electrode names. */
	private String selectedElectrodeNames;
	/** Overall length of epoch in milliseconds. */
	private int epochLength;
	/** Milliseconds of data before stimul that are to be processed as well. */
	private int millisBeforeStimul;
	
	/** String of selected epoch numbers. */
	private String stringSelectedEpochs; //convert to int[]
	/** Numbers of selected epochs parsed from "stringSelectedEpochs". */
	private int[] selectedEpochs;
	
	/** Contains info from .vhdr file. */
	private Vhdr vhdrFile;
	/** Contains info from .vmrk file. */
	private Vmrk vmrkFile;
	/** Contains info about channels. */
	private ChannelInfoList channels;
	/** EEG reader that reads from .eeg file. */
	private EEGReader eegReader;


	public BvdefReader(){		
		vhdrFile = new Vhdr();
		channels = new ChannelInfoList();
	}
	
	
	/**
	 * Data reading method.
	 * Reads info from .vhdr file,  
	 */
	@Override
	public Data read(File file) {

		FileReader fr;
		try {
			vhdrFile.setVhdrFilepath(file.getParentFile().getAbsolutePath());
			vhdrFile.setMillisBeforeStimul(-millisBeforeStimul);
			
			fr = new FileReader(file);
			
			Scanner sc = new Scanner(fr);

			String[] temp;
			
//reading VHDR file
			while (sc.hasNext()) {
				String line = sc.nextLine();

				if (line.contains("Codepage")) {
					temp = line.split("=");
					vhdrFile.setCodePage(temp[1]);
				} else if (line.contains("DataFile")) {
					temp = line.split("=");
					vhdrFile.setDataFilename(temp[1]);
				} else if (line.contains("MarkerFile")) {
					temp = line.split("=");
					vhdrFile.setMarkerFilename(temp[1]);
				} else if (line.contains("DataFormat")) {
					temp = line.split("=");
					vhdrFile.setDataFormat(DataFormat.valueOf(temp[1]));
				} else if (line.contains("DataOrientation")) {
					temp = line.split("=");
					vhdrFile.setDataOrientation(DataOrientation.valueOf(temp[1]));
				} else if (line.contains("NumberOfChannels")) {
					temp = line.split("=");
					vhdrFile.setNumberOfChannels(Integer.parseInt(temp[1]));
				} else if (line.contains("SamplingInterval")) {
					temp = line.split("=");
					vhdrFile.setSamplingInterval(Integer.parseInt(temp[1]));
				} else if (line.contains("BinaryFormat")) {
					temp = line.split("=");
					vhdrFile.setBinaryDataFormat(BinaryDataFormat.valueOf(temp[1]));
					
				} else if (line.startsWith("Ch1=")) {
					
					ChannelInfo chInfo;
					
					for (int index = 1; index <= vhdrFile.getNumberOfChannels(); index++) {
						temp = line.split("=");  // example: Ch1=Fp1,,0.1,µV
						temp = temp[1].split(",");		
						
						chInfo = new ChannelInfo(temp[0], index, temp[1], temp[2], temp[3]);
						channels.addItem(chInfo);
						
						line = sc.nextLine();
					}

					vhdrFile.setChannelInfoList(channels);
				}

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//execution of  vmrk reader
		VmrkReader vmrkReader = new VmrkReader();
		vmrkReader.setVmrkFilename(vhdrFile.getVhdrFilepath()+"\\"+vhdrFile.getMarkerFilename());
		vmrkReader.read();
		vmrkFile = vmrkReader.getVmrkFile();
		
		
//execution of EEGReader
		eegReader = new EEGReader(vhdrFile, vmrkFile,
				selectedElectrodeNames, selectedEpochs);
		
		ArrayList<double[][]> tempData = eegReader.getData(millisBeforeStimul, epochLength);
		
//create new markers (data are stripped, position in file is changed)
		ArrayList<Marker> markers = vmrkFile.getMarkers();
		ArrayList<Marker> newMarkers = new ArrayList<Marker>();
		
//		System.out.println("bvdef " + selectedEpochs.length);
		for(int i = 1; i <= selectedEpochs.length; i++){
			int position = ((i-1) * epochLength) - millisBeforeStimul + 1;
			markers.get(i).setPosition(position);
			newMarkers.add(markers.get(i));
		}
		vmrkFile.setMarkers(newMarkers);

		Data data = new Data(tempData, vmrkFile,
				channels, selectedElectrodeNames, vhdrFile, selectedEpochs);		
		
		return data;
	}


	/**
	 * @param vhdrFile the vhdrFile to set
	 */
	public void setVhdrFile(Vhdr vhdrFile) {
		this.vhdrFile = vhdrFile;
	}

	/**
	 * @return the vhdrFile
	 */
	public Vhdr getVhdrFile() {
		return vhdrFile;
	}

	/**
	 * @return the channels
	 */
	public ChannelInfoList getchannels() {
		return channels;
	}
	



	/**
	 * @return the selectedElectrodeNames
	 */
	public String getSelectedElectrodeNames() {
		return selectedElectrodeNames;
	}



	/**
	 * @param selectedElectrodeNames the selectedElectrodeNames to set
	 */
	public void setSelectedElectrodeNames(String xselectedElectrodeNames) {
		this.selectedElectrodeNames = xselectedElectrodeNames;
	}



	/**
	 * @return the millisBeforeStimul
	 */
	public int getMillisBeforeStimul() {
		return millisBeforeStimul;
	}



	/**
	 * @param millisBeforeStimul the millisBeforeStimul to set
	 */
	public void setMillisBeforeStimul(int millisBeforeStimul) {
		this.millisBeforeStimul = millisBeforeStimul;
	}


	/**
	 * @param stringEpochs the stringEpochs to set
	 */
	public void setStringSelectedEpochs(String stringSelEpochs) {
		this.stringSelectedEpochs = stringSelEpochs;
		this.setSelectedEpochs(this.stringSelectedEpochs);
	}

	/**
	 * @return the stringEpochs
	 */
	public String getStringSelectedEpochs() {
		return stringSelectedEpochs;
	}
	
	/**
	 * @return the selectedEpochs
	 */
	public int[] getSelectedEpochs() {
		return selectedEpochs;
	}



	/**
	 * Sets number of selected epochs from String value.
	 * @param selectedEpochs the selectedEpochs to set
	 */
	public void setSelectedEpochs(String selEpochs) {
		
		String[] temp = selEpochs.split("[^0-9]+");	//strip anything but 0-9
		
		this.selectedEpochs = new int[temp.length];
		
		for (int i = 0; i < temp.length; i++) {
			this.selectedEpochs[i] = Integer.valueOf(temp[i]);
		}
		log.info("Selected epoch numbers are: " + Arrays.toString(temp));
	}


	/**
	 * @return the epochLength
	 */
	public int getEpochLength() {
		return epochLength;
	}


	/**
	 * @param epochLength the epochLength to set
	 */
	public void setEpochLength(int epochLength) {
		this.epochLength = epochLength;
	}


	/**
	 * @return the eegReader
	 */
	public EEGReader getEegReader() {
		return eegReader;
	}


	/**
	 * @param eegReader the eegReader to set
	 */
	public void setEegReader(EEGReader eegReader) {
		this.eegReader = eegReader;
	}


	/**
	 * @param selectedEpochs the selectedEpochs to set
	 */
	public void setSelectedEpochs(int[] selectedEpochs) {
		this.selectedEpochs = selectedEpochs;
	}
	
}
