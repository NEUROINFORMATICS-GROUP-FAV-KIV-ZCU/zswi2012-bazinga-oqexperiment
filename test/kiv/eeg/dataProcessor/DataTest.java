package kiv.eeg.dataProcessor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class DataTest {

	/**
	 * Tests constructor and getter method at once for initializing and returning the same list of arrays.  
	 */
	@Test
	public void testData1() {
		ArrayList<double[][]> arrayList = new ArrayList<double[][]>(); 
		double[][] x = {{1.0, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}};
		arrayList.add(x);
		double[][] y = {{101, 102, 103, 104, 105, 106, 107, 108, 109, 110}, {111, 112, 113, 114, 115, 116, 117, 118, 119, 120}};
		arrayList.add(y);
		
		Data d = new Data(arrayList, null, null, null, null, null);
		
		assertEquals(arrayList, d.getData());
		
	}
	
	/**
	 * Tests setter and getter for setting and returning the same list of arrays.
	 */
	@Test
	public void testData2() {
		ArrayList<double[][]> almostEmptyArrayList = new ArrayList<double[][]>();
		double[][] almostEmptyField = {{1, 2}, {3, 4}};
		almostEmptyArrayList.add(almostEmptyField);
		Data d = new Data(almostEmptyArrayList, null, null, null, null, null);
		
		ArrayList<double[][]> arrayList = new ArrayList<double[][]>(); 
		double[][] x = {{1.0, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}};
		arrayList.add(x);
		double[][] y = {{101, 102, 103, 104, 105, 106, 107, 108, 109, 110}, {111, 112, 113, 114, 115, 116, 117, 118, 119, 120}};
		arrayList.add(y);
		
		d.setData(arrayList);
		
		assertEquals(arrayList, d.getData());
		
	}
	
	/**
	 * Tests constructor for null parameters. Writes error message and quits.
	 */
	@Test
	public void testData3() {
		@SuppressWarnings("unused")
		Data d = new Data(null, null, null, null, null, null);
		//program ended
	}

}
