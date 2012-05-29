package kiv.eeg.dataProcessor.other;

/** 
 * Contains info about marker. Data are gathered from .vmrk file.
 * @author Bazinga
 *
 */
public class Marker {

	/** Index of marker. */
	private int index;
	/** Type of marker. */
	private String type;
	/** Description of marker. */
	private String description;
	/** Position of marker in .eeg data file. */
	private long position;
	/** Size in data points. */
	private int size;
	/** Number of related channel number. */
	private int channelNumber;
	
	
	/**
	 * Constructor creating instance of object specified by parameters.
	 * @param index Index of marker.
	 * @param type Type of marker.
	 * @param description Description of marker.
	 * @param position Position of marker in .eeg data file.
	 * @param size Size in data points.
	 * @param channelNumber Number of related channel number.
	 */
	public Marker(int index, String type, String description, long position,
			int size, int channelNumber) {
		this.index = index;
		this.type = type;
		this.description = description;
		this.position = position;
		this.size = size;
		this.channelNumber = channelNumber;
	}
	
	
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	/**
	 * @return the position
	 */
	public long getPosition() {
		return position;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param channelNumber the channelNumber to set
	 */
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	/**
	 * @return the channelNumber
	 */
	public int getChannelNumber() {
		return channelNumber;
	}
}
