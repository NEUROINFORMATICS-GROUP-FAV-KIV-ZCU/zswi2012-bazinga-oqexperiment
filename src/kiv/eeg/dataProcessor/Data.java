package kiv.eeg.dataProcessor;

import java.util.ArrayList;

import kiv.eeg.dataProcessor.file.Vhdr;
import kiv.eeg.dataProcessor.file.Vmrk;
import kiv.eeg.dataProcessor.other.ChannelInfoList;

import org.apache.log4j.Logger;


/**
 * Main Data class.
 * This class holds all important data. Its instance is being put as parameter into all filter methods and is returned by them.
 *  
 * @author Bazinga
 */
public class Data {
	/** List of arrays. Each array in list is one epoch, first index defines electrode and second index development of electrode in time. */
	private ArrayList<double[][]> data;
	/** Holds important data from .vmrk file, such as Markers. */
	private Vmrk vmrkFile;
	/** Holds list of Channels, where info about each channel (electrode) can be found. */
	private ChannelInfoList channels;
	/** String representation of selected electrodes, which are defined by their names, divided by commas, usually. */
	private String selectedElectrodes;
	/** Indexes of selected epochs. */
	private int[] selectedEpochs;
	/** Holds important data from .vhdr file. */
	private Vhdr vhdrFile;

	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());

	/**
	 * Constructor of class Data. Also checks if some parameters are not null.
	 * 
	 * @param data List of 2D double fields with data from epochs.
	 * @param vmrkFile Instance of Vmrk class with important marker infos.
	 * @param channels Holds list of channels (electrodes) with info about them.
	 * @param selectedElectrodes String representation of selected electrodes, which are defined by their names, divided by commas, usually.
	 * @param vhdr Holds info from .vhdr file.
	 * @param selectedEpochs Field with indexes of selected electrodes.
	 */
	public Data(ArrayList<double[][]> data, Vmrk vmrkFile,
			ChannelInfoList channels, String selectedElectrodes,
			Vhdr vhdr, int[] selectedEpochs) {
		if (data == null) {
			log.error("Invalid constructor parameter in class Data - ArrayList<double[][]> data is null.");
			System.exit(-1);
		}
		this.data = data;
		if (vmrkFile == null) {
			log.error("Invalid constructor parameter in class Data - Vmrk vmrkFile is null.");
			System.exit(0);
		}
		this.vmrkFile = vmrkFile;
		if (vhdr == null) {
			log.error("Invalid constructor parameter in class Data - Vhdr vhdr is null.");
			System.exit(0);
		}
		this.setVhdrFile(vhdr);
		if (selectedEpochs.length <= 0 || selectedEpochs == null) {
			log.error("Invalid constructor parameter in class Data - int[] selectedEpochs is null or has zero length.");
			System.exit(0);
		}
		this.selectedEpochs = selectedEpochs;
	}

	
	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<double[][]> data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public ArrayList<double[][]> getData() {
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
	 * @return the vmrkFile
	 */
	public Vmrk getVmrkFile() {
		return vmrkFile;
	}


	/**
	 * @param vmrkFile the vmrkFile to set
	 */
	public void setVmrkFile(Vmrk vmrkFile) {
		this.vmrkFile = vmrkFile;
	}


	/**
	 * @return the channels
	 */
	public ChannelInfoList getChannels() {
		return channels;
	}


	/**
	 * @param channels the channels to set
	 */
	public void setChannels(ChannelInfoList channels) {
		this.channels = channels;
	}


	/**
	 * @return the selectedElectrodes
	 */
	public String getSelectedElectrodes() {
		return selectedElectrodes;
	}


	/**
	 * @param selectedElectrodes the selectedElectrodes to set
	 */
	public void setSelectedElectrodes(String selectedElectrodes) {
		this.selectedElectrodes = selectedElectrodes;
	}


	/**
	 * @return the selectedEpochs
	 */
	public int[] getSelectedEpochs() {
		return selectedEpochs;
	}


	/**
	 * @param selectedEpochs the selectedEpochs to set
	 */
	public void setSelectedEpochs(int[] selectedEpochs) {
		this.selectedEpochs = selectedEpochs;
	}

}
