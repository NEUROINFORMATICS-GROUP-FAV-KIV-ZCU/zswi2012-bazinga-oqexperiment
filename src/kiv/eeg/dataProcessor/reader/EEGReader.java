package kiv.eeg.dataProcessor.reader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.file.BinaryDataFormat;
import kiv.eeg.dataProcessor.file.Vhdr;
import kiv.eeg.dataProcessor.file.Vmrk;

/**
 * EEG reader that reads data from .eeg data file.
 * @author Bazinga
 *
 */
public class EEGReader {

	/** For logging purposes. */
	private static Logger log = Logger.getLogger(Logger.class);
	/** Contains info from .vhdr file. */
	private Vhdr vhdrFile;
	/** Contains info from .vmrk file. */
	private	Vmrk vmrkFile;
	/** String of selected electrode names. */
	private String selectedElectrodeNames;
	/** Numbers of selected electrodes. */
	private int[] selectedElectrodes;
	/** Double 2D field that contains read data. */ 
	private double[][] data;
	/** Numbers of selected epochs. */
	private int[] selectedEpochs;
	
	/**
	 * Public constructor that creates instance by defined parameters.
	 * @param file Info from .vhdr file.
	 * @param vmrkFile Info from .vmrk file.
	 * @param selectedElectrodeNames Names of selected electrodes.
	 * @param selectedEpochs Numbers of selected epochs.
	 */
	public EEGReader(Vhdr file, Vmrk vmrkFile, String selectedElectrodeNames, int[] selectedEpochs){
		this.vhdrFile = file;
		this.vmrkFile = vmrkFile;
		this.selectedElectrodeNames = selectedElectrodeNames;
		this.selectedEpochs = selectedEpochs;
		convertElNamesToIndexes();
	}
	
	
	/**
	 * Method that reads data from .eeg file.
	 * @param millisBeforeStimul Milliseconds before stimul to read data from.
	 * @param epochLength Overall length of epoch.
	 * @return List of double 2D arrays containing data from .eeg file.
	 */
	public ArrayList<double[][]> getData(int millisBeforeStimul, int epochLength){
								
		DataInputStream dis;
		BufferedInputStream bis;
		
		int numberOfChannels = vhdrFile.getNumberOfChannels();
		int numberOfSelectedChannels = selectedElectrodes.length;
		int numberOfEpochs = selectedEpochs.length;
		ArrayList<double[][]> listOfStimuls = new ArrayList<double[][]>();
		
		
		

		try {
			bis = new BufferedInputStream(new FileInputStream(vhdrFile
					.getVhdrFilepath()
					+ "\\" + vhdrFile.getDataFilename()));
			dis = new DataInputStream(bis);
			dis.mark(Integer.MAX_VALUE);

			for (int n = 0; n < numberOfEpochs; n++) { // !!!
											
					data = new double[numberOfSelectedChannels][epochLength];
					
					int positionN = (int) ((vmrkFile.getMarkers().get(selectedEpochs[n])
							.getPosition()) - 1); // !!!

					int skip = 0;
					if (vhdrFile.getBinaryDataFormat() == (BinaryDataFormat.INT_16)) {
						skip = (positionN + millisBeforeStimul)
								* numberOfChannels * 2;
					}else if(vhdrFile.getBinaryDataFormat() == (BinaryDataFormat.IEEE_FLOAT_32)){
						skip = (positionN + millisBeforeStimul)
						* numberOfChannels * 4;
					}

					int actualSkip = dis.skipBytes(skip);
					if(skip != actualSkip) {
						System.out.println("ERROR:  skipBytes skace blbe o "+ (actualSkip-skip) +" bytù !!!");
					}

					if (skip != actualSkip) {
						System.out.println("ERROR!   "
								+ (int) (skip - actualSkip)
								+ " rozdil skipu");
					}

					for (int i = 0; i < epochLength; i++) {
						int x = 0;
						for (int j = 0; j < numberOfChannels; j++) {
							if(isSelectedElectrode(j+1)){
								data[x][i] = (double) dis
										.readShort();
								x++;
							}else{
								dis.readShort(); //throw away not-needed values 
							}
						}
					}

					listOfStimuls.add(data);
					dis.reset();
				}
							
		} catch (EOFException e2) {
			log.warn("Reached end of file. Data may be incomplete!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return listOfStimuls;
	}
	
	/**
	 * Checks if index is within selected electrodes.
	 * @param j Index to check.
	 * @return True if index is within selected electrodes. 
	 */
	private boolean isSelectedElectrode(int j) {
		if(Arrays.binarySearch(selectedElectrodes, j) >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Converts electrode names to indexes from "selectedElectrodeNames".
	 */
	private void convertElNamesToIndexes(){
		String[] temp = selectedElectrodeNames.split("[^A-Za-z0-9]+");	//strip anything but A-Z, a-z, 0-9
		
		selectedElectrodes = new int[temp.length];
		for(int i = 0; i < vhdrFile.getChannelInfoList().getList().size(); i++){
			for(int j = 0; j < temp.length; j++){
				if (temp[j].equals(vhdrFile.getChannelInfoList().getList().get(i).getName())){
					selectedElectrodes[j] = vhdrFile.getChannelInfoList().getList().get(i).getIndex();
				}
			}
		}
		vhdrFile.setSelectedElectrodes(selectedElectrodes);
		log.info("Selected electrodes are: " + Arrays.toString(selectedElectrodes));
	}


	/**
	 * @return the selectedElectrodes
	 */
	public int[] getSelectedElectrodes() {
		return selectedElectrodes;
	}


	/**
	 * @param selectedElectrodes the selectedElectrodes to set
	 */
	public void setSelectedElectrodes(int[] selectedElectrodes) {
		this.selectedElectrodes = selectedElectrodes;
	}


	/**
	 * @return the vhdrFile
	 */
	public Vhdr getVhdrFile() {
		return vhdrFile;
	}


	/**
	 * @param vhdrFile the vhdrFile to set
	 */
	public void setVhdrFile(Vhdr vhdrFile) {
		this.vhdrFile = vhdrFile;
	}
}
