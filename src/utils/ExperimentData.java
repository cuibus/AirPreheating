package utils;

import java.util.List;

public class ExperimentData {
	public String experimentName;
	public List<InRecord> inputData;

	public void checkHasNaN() {
		for (InRecord inrec : inputData) {
			if (Double.isNaN(inrec.airTemp) ||
				Double.isNaN(inrec.earthTemp))
					throw new RuntimeException("has NaN at " + inrec.time);
		}
	}
}
