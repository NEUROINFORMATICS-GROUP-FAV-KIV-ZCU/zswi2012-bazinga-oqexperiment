package kiv.eeg.dataProcessor.reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import kiv.eeg.dataProcessor.file.Vmrk;
import kiv.eeg.dataProcessor.other.Marker;

/**
 * Reads information from .vmrk file. 
 * @author Bazinga
 *
 */
public class VmrkReader {
	
	/** Name of .vmrk file. */
	private String vmrkFilename;
	
	/** Information from .vmrk file. */
	private Vmrk vmrkFile;
	
	/**
	 * Reads from .vmrk file and stores into "vmrkFile".
	 */
	public void read() {
		vmrkFile = new Vmrk();
		
		FileReader fr = null;
		try {
			fr = new FileReader(vmrkFilename);
			Scanner sc = new Scanner(fr);

			String[] temp;

			while (sc.hasNext()) {
				String line = sc.nextLine();

				if (line.contains("Codepage")) {
					temp = line.split("=");
					vmrkFile.setCodepage(temp[1]);
				} else if (line.contains("DataFile")) {
					temp = line.split("=");
					vmrkFile.setDataFilename(temp[1]);
				} else if (line.startsWith("Mk")) {
					
					Marker marker;
					
					int index = 1;
					while(line.startsWith("Mk")) {
													// Mk1=New Segment,,1,1,0,20120306123612362540
						temp = line.split("=");  // example: Mk12=Stimulus,S  1,23502,1,0
						temp = temp[1].split(",");		
						
						marker = new Marker(index, temp[0], temp[1],
								Long.parseLong(temp[2]), Integer.parseInt(temp[3]),
								Integer.parseInt(temp[4]));
						vmrkFile.addMarker(marker);
						
						if (sc.hasNext()){
							line = sc.nextLine();
							index++; 
							continue;
						}
						else {
							break;
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @return the vmrkFilename
	 */
	public String getVmrkFilename() {
		return vmrkFilename;
	}


	/**
	 * @param vmrkFilename the vmrkFilename to set
	 */
	public void setVmrkFilename(String vmrkFilename) {
		this.vmrkFilename = vmrkFilename;
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

}
