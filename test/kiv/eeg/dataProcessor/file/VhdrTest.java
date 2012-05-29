package kiv.eeg.dataProcessor.file;

import static org.junit.Assert.*;
import kiv.eeg.dataProcessor.other.ChannelInfoList;

import org.junit.Test;

public class VhdrTest {
//zmena
	Vhdr vhdr = new Vhdr();
	@Test
	public void testSetCodePage() {
		vhdr.setCodePage("nazev");
		assertEquals(vhdr.getCodePage(),"nazev");
	}

	@Test
	public void testSetDataFilename() {
		vhdr.setDataFilename("nazev");
		assertEquals(vhdr.getDataFilename(),"nazev");
	}

	@Test
	public void testSetMarkerFilename() {
		vhdr.setMarkerFilename("nazev");
		assertEquals(vhdr.getMarkerFilename(),"nazev");
	}

	@Test
	public void testSetDataFormat() {
		vhdr.setDataFormat(DataFormat.BINARY);
		assertEquals(vhdr.getDataFormat(),DataFormat.BINARY);
	}

	@Test
	public void testSetDataOrientation() {
		vhdr.setDataOrientation(DataOrientation.MULTIPLEXED);
		assertEquals(vhdr.getDataOrientation(),DataOrientation.MULTIPLEXED);
	}

	@Test
	public void testSetBinaryDataFormat() {
		vhdr.setBinaryDataFormat(BinaryDataFormat.INT_16);
		assertEquals(vhdr.getBinaryDataFormat(),BinaryDataFormat.INT_16);
	}

	@Test
	public void testSetChannelInfoList() {
		ChannelInfoList ch = new ChannelInfoList(); 
		vhdr.setChannelInfoList(ch);
		assertEquals(vhdr.getChannelInfoList(),ch);
	}

	@Test
	public void testSetVhdrFilepath() {
		vhdr.setVhdrFilepath(null);
	}

}
