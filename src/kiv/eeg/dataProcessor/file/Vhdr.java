package kiv.eeg.dataProcessor.file;

import org.apache.log4j.Logger;

import kiv.eeg.dataProcessor.other.ChannelInfoList;

/**
 * Represents information gathered from .vhdr file.
 * @author Bazinga
 *
 */
public class Vhdr {
	/** Codepage of the file. */
	private String codePage;
	/** Filename of .eeg data file. */
	private String dataFilename;
	/** Filename of .vmrk marker file. */
	private String markerFilename;
	/** Number of channels that were recorded. */
	private int numberOfChannels;
	/** Interval of sampling in microseconds. */
	private int samplingInterval;
	/** Format in which data were recorded. */
	private DataFormat dataFormat;
	/** Orientation of recorded data. */
	private DataOrientation dataOrientation;
	/** Binary format in which data were recorded. */
	private BinaryDataFormat binaryDataFormat;
	/** Contains list of ChannelInfo, where information about channels is stored. */
	private ChannelInfoList channelInfoList;
	/** Determines use of big or small endian. */
	private boolean endianOrder;
	/** Filepath to .vhdr file. */
	private String vhdrFilepath;
	/** Integer indexes of selected electrodes. */
	private int[] selectedElectrodes;
	/** Determines if data were averaged. */
	private boolean isAveraged = false;
	/** Value of milliseconds before stimul. */
	private int millisBeforeStimul;
	/** For logging purposes. */
	private Logger log = Logger.getLogger(getClass());



	/**
	 * @return the codePage
	 */
	public String getCodePage() {
		return codePage;
	}

	/**
	 * @param codePage the codePage to set
	 */
	public void setCodePage(String codePage) {
		if(codePage == null) {
			log.error("Invalid parameter in method setCodePage of class Vhdr - String codePage is null.");
			System.exit(0);
		}
		this.codePage = codePage;
	}

	/**
	 * @return the dataFilename
	 */
	public String getDataFilename() {
		return dataFilename;
	}

	/**
	 * @param dataFilename the dataFilename to set
	 */
	public void setDataFilename(String dataFilename) {
		if(dataFilename == null) {
			log.error("Invalid parameter in method setDataFilename of class Vhdr - String dataFilename is null.");
			System.exit(0);
		}
		this.dataFilename = dataFilename;
	}

	/**
	 * @return the markerFilename
	 */
	public String getMarkerFilename() {
		return markerFilename;
	}

	/**
	 * @param markerFilename the markerFilename to set
	 */
	public void setMarkerFilename(String markerFilename) {
		if(markerFilename == null) {
			log.error("Invalid parameter in method setMarkerFilename of class Vhdr - String markerFilename is null.");
			System.exit(0);
		}
		this.markerFilename = markerFilename;
	}

	/**
	 * @return the numberOfChannels
	 */
	public int getNumberOfChannels() {
		return numberOfChannels;
	}

	/**
	 * @param numberOfChannels the numberOfChannels to set
	 */
	public void setNumberOfChannels(int numberOfChannels) {

		this.numberOfChannels = numberOfChannels;
	}

	/**
	 * @return the samplingInterval
	 */
	public int getSamplingInterval() {
		return samplingInterval;
	}

	/**
	 * @param samplingInterval the samplingInterval to set
	 */
	public void setSamplingInterval(int samplingInterval) {

		this.samplingInterval = samplingInterval;
	}

	/**
	 * @return the dataFormat
	 */
	public DataFormat getDataFormat() {
		return dataFormat;
	}

	/**
	 * @param dataFormat the dataFormat to set
	 */
	public void setDataFormat(DataFormat dataFormat) {
		if(dataFormat == null) {
			log.error("Invalid parameter in method setDataFormat of class Vhdr - DataFormat dataFormat is null.");
			System.exit(0);
		}
		this.dataFormat = dataFormat;
	}

	/**
	 * @return the dataOrientation
	 */
	public DataOrientation getDataOrientation() {
		return dataOrientation;
	}

	/**
	 * @param dataOrientation the dataOrientation to set
	 */
	public void setDataOrientation(DataOrientation dataOrientation) {
		if(dataOrientation == null) {
			log.error("Invalid parameter in method setDataOrientation of class Vhdr - DataOrientation dataOrientation is null.");
			System.exit(0);
		}
		this.dataOrientation = dataOrientation;
	}

	/**
	 * @return the binaryDataFormat
	 */
	public BinaryDataFormat getBinaryDataFormat() {
		return binaryDataFormat;
	}

	/**
	 * @param binaryDataFormat the binaryDataFormat to set
	 */
	public void setBinaryDataFormat(BinaryDataFormat binaryDataFormat) {
		if(binaryDataFormat == null) {
			log.error("Invalid parameter in method setBinaryDataFormat of class Vhdr - BinaryDataFormat binaryDataFormat is null.");
			System.exit(0);
		}
		this.binaryDataFormat = binaryDataFormat;
	}

	/**
	 * @return the channelInfoList
	 */
	public ChannelInfoList getChannelInfoList() {
		return channelInfoList;
	}

	/**
	 * @param channelInfoList the channelInfoList to set
	 */
	public void setChannelInfoList(ChannelInfoList channelInfoList) {
		if(channelInfoList == null) {
			log.error("Invalid parameter in method setChannelInfoList of class Vhdr - ChannelInfoList channelInfoList is null.");
			System.exit(0);
		}
		this.channelInfoList = channelInfoList;
	}

	/**
	 * @return the endianOrder
	 */
	public boolean isEndianOrder() {
		return endianOrder;
	}

	/**
	 * @param endianOrder the endianOrder to set
	 */
	public void setEndianOrder(boolean endianOrder) {
		this.endianOrder = endianOrder;
	}

	/**
	 * @param vhdrFilepath the vhdrFilepath to set
	 */
	public void setVhdrFilepath(String vhdrFilepath) {
		if(vhdrFilepath == null) {
			log.error("Invalid parameter in method setVhdrFilepath of class Vhdr - String vhdrFilepath is null.");
			System.exit(0);
		}
		this.vhdrFilepath = vhdrFilepath;
	}

	/**
	 * @return the vhdrFilepath
	 */
	public String getVhdrFilepath() {
		return vhdrFilepath;
	}

	/**
	 * @param selectedElectrodes the selectedElectrodes to set
	 */
	public void setSelectedElectrodes(int[] selectedElectrodes) {
		this.selectedElectrodes = selectedElectrodes;
	}

	/**
	 * @return the selectedElectrodes
	 */
	public int[] getSelectedElectrodes() {
		return selectedElectrodes;
	}

	/**
	 * @param isAveraged the isAveraged to set
	 */
	public void setAveraged(boolean isAveraged) {
		this.isAveraged = isAveraged;
	}

	/**
	 * @return the isAveraged
	 */
	public boolean isAveraged() {
		return isAveraged;
	}

	/**
	 * @param millisBeforeStimul the millisBeforeStimul to set
	 */
	public void setMillisBeforeStimul(int millisBeforeStimul) {
		this.millisBeforeStimul = millisBeforeStimul;
	}

	/**
	 * @return the millisBeforeStimul
	 */
	public int getMillisBeforeStimul() {
		return millisBeforeStimul;
	}


	
	
}
