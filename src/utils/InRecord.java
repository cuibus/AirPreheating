package utils;

import java.time.LocalDate;

public class InRecord {
	public LocalDate time;
	public double earthTemp;
	public double airTemp;
	public double outTemp;
	public double pipeTemp;

	public InRecord(LocalDate time, double earthTemp, double airTemp, double outTemp, double pipeTemp) {
		this.time = time;
		this.earthTemp = earthTemp;
		this.airTemp = airTemp;
		this.outTemp = outTemp;
		this.pipeTemp = pipeTemp;
	}
}
