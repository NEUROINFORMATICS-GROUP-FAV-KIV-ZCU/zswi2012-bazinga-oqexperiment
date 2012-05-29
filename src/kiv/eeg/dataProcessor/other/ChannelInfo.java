package kiv.eeg.dataProcessor.other;

/**
 * Contains info about channel (electrode).
 * @author Bazinga
 *
 */
public class ChannelInfo {

	/** Name of channel. */
	private String name;
	/** Index of channel. */
	private int index;
	/** Name of reference channel. */
	private String refName;
	/** Resolution of channel. */
	private String resUnit;
	/** Unit of resolution of channel. */
	private String unit;
	
	/**
	 * Public constructor with parameters. Creates instance defined by parameters.
	 * @param name Name of channel.
	 * @param index Index of channel.
	 * @param refName Name of reference channel.
	 * @param resUnit Resolution of channel.
	 * @param unit Unit of resolution of channel.
	 */
	public ChannelInfo(String name, int index, String refName, String resUnit, String unit) {
		setName(name);
		setIndex(index);
		setRefName(refName);
		setResUnit(resUnit);
		setUnit(unit);
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param refName the refName to set
	 */
	public void setRefName(String refName) {
		this.refName = refName;
	}
	/**
	 * @return the refName
	 */
	public String getRefName() {
		return refName;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * Overridden method toString(). Returns more specific details of channel.
	 * @return String representation of ChannelInfo.
	 */
	@Override
	public String toString() {
		return "ChannelInfo [index=" + index + ", name=" + name + ", refName="
				+ refName + ", resUnit=" + resUnit + ", unit=" + unit + "]";
	}

	/**
	 * @param resUnit the resUnit to set
	 */
	public void setResUnit(String resUnit) {
		this.resUnit = resUnit;
	}

	/**
	 * @return the resUnit
	 */
	public String getResUnit() {
		return resUnit;
	}
	
	
	
}
