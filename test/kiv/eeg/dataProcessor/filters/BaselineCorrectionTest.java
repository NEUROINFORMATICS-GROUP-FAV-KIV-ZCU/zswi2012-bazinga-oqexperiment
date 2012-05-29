package kiv.eeg.dataProcessor.filters;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

import kiv.eeg.dataProcessor.Data;

import org.junit.Test;

public class BaselineCorrectionTest {

	/**
	 * Tests method process() of the class BaselineCorrection. 
	 */
	@Test
	public void testProcess() {
		BaselineCorrection bc = new BaselineCorrection();
		
		ArrayList<double[][]> arrayList = new ArrayList<double[][]>(); 
		double[][] x1 = {{1.0, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}};
		arrayList.add(x1);
		double[][] y1 = {{101, 102, 103, 104, 105, 106, 107, 108, 109, 110}, {111, 112, 113, 114, 115, 116, 117, 118, 119, 120}};
		arrayList.add(y1);
		
		Data d = new Data(arrayList, null, null, null, null, null);
		
		d = bc.process(d);
		
		/* average from first 3 numbers is 2, then 12; 102 and 112. So fields should be: */
		double[][] x2 = {{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8}, {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8}};
		double[][] y2 = {{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8}, {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8}};

		assertArrayEquals(d.getData().get(0), x2);
		assertArrayEquals(d.getData().get(1), y2);
		
	}

}
