package utils;

import java.time.LocalDate;

public class InRecord {
	public LocalDate time;
	public double t1; //temp pamant 20cm teava, la 4m de intrare
	public double t2; //temp teava, la 4m de intrare
	public double t3; //temp pamant 20cm teava, la 4m de iesire = y2
	public double t4; //temp teava, la 4m de iesire
	public double t5; //temp aer (afara) = u
	public double t6; //temp aer iesire = y1
	public double t7; //temp pamant (constant) = v

	public InRecord(LocalDate time, double t1, double t2, double t3, double t4, double t5, double t6, double t7) {
		this.time = time;
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
		this.t5 = t5;
		this.t6 = t6;
		this.t7 = t7;
	}
}
