package utils;

import java.util.List;

public class ExperimentData {
	public String experimentName;
	public List<InRecord> inputData;

	public void checkHasNaN() {
		for (InRecord inrec : inputData) {
			if (Double.isNaN(inrec.t4) ||
				Double.isNaN(inrec.t7))
					throw new RuntimeException("has NaN at " + inrec.time);
		}
	}
}
