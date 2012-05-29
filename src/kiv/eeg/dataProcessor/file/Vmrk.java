package kiv.eeg.dataProcessor.file;

import java.util.ArrayList;
import java.util.List;

import kiv.eeg.dataProcessor.other.Marker;

/**
 * Represents information gathered from .vmrk file.
 * @author Bazinga
 *
 */
public class Vmrk {
	/** List of Marker. Each marker contains information about one epoch. */
	private ArrayList<Marker> markers;
	
	/** Codepage of the file. */
	private String codepage;
	/** Filename of .eeg data file. */
	private String dataFilename;
	/** Number of epochs that were read. */
	private int epochsOriginally = 0;
	
	/**
	 * Public default constructor. Initializes list of markers.
	 */
	public Vmrk(){
		markers = new ArrayList<Marker>();
	}
	
	/**
	 * Adds marker to list of markers.
	 * @param m Marker to add.
	 */
	public void addMarker(Marker m){
		markers.add(m);
	}
	
	/**
	 * Getter of only some markers defined in parameter.
	 * @param markerTypes List of strings defining types of markers.
	 * @return Filtered list of markers.
	 */
	public List<Marker> getMarkers(List<String> markerTypes){
		List<Marker> temp = new ArrayList<Marker>();
		
		for(int index = 0; index < markers.size(); index++){
			for(int i = 0; i < markerTypes.size(); i++){
				if(markers.get(index).getType().equals(markerTypes.get(i))){
					temp.add(markers.get(index));
					break;
				}
			}
		}
		
		return temp;
	}
	
	/**
	 * @param codepage the codepage to set
	 */
	public void setCodepage(String codepage) {
		this.codepage = codepage;
	}

	/**
	 * @return the codepage
	 */
	public String getCodepage() {
		return codepage;
	}

	/**
	 * @param dataFilename the dataFilename to set
	 */
	public void setDataFilename(String dataFilename) {
		this.dataFilename = dataFilename;
	}

	/**
	 * @return the dataFilename
	 */
	public String getDataFilename() {
		return dataFilename;
	}

	/**
	 * @return the markers
	 */
	public ArrayList<Marker> getMarkers() {
		return markers;
	}

	/**
	 * @param markers the markers to set
	 */
	public void setMarkers(ArrayList<Marker> markers) {
		this.markers = markers;
	}

	/**
	 * @param epochsOriginally the epochsOriginally to set
	 */
	public void setEpochsOriginally(int epochsOriginally) {
		this.epochsOriginally = epochsOriginally;
	}

	/**
	 * @return the epochsOriginally
	 */
	public int getEpochsOriginally() {
		return epochsOriginally;
	}

}
