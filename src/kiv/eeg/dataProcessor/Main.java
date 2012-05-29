package kiv.eeg.dataProcessor;

import org.apache.log4j.Logger;

/**
 * Main class. Executes JavaBean as instance of Runner.
 * 
 * @author Bazinga
 *
 */
public class Main {
	
	/** For logging purposes. */
	private static Logger log = Logger.getLogger(Logger.class);
	
	/** Main method, executes JavaBean as instance of Runner.
	 * @param args No arguments used.
	 */
	public static void main(String[] args) {
		log.info("Program started.");
		
		String xmlFilePath = "configs/config.xml";

		Runner runner = BeanUtils.readBean(Runner.class, xmlFilePath, "main");
		runner.run();
		
		log.info("Done.");

	}

}
