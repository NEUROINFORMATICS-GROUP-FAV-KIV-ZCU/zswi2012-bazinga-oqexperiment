package kiv.eeg.dataProcessor.other;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains list of channels, which contains details about each channel.
 * @author Bazinga
 *
 */
public class ChannelInfoList {
	/** List of ChannelInfos. */
	private List<ChannelInfo> list;
	
	public ChannelInfoList(){
		list =  new ArrayList<ChannelInfo>();
	}
	
	public void addItem(ChannelInfo chInfo){
		list.add(chInfo);
	}
	
	
	public List<ChannelInfo> getList(){
		return list;
	}

	
	public void vypis() {
		for (ChannelInfo ch : list) {
			System.out.println(ch.toString());
		}
		
	}
}
