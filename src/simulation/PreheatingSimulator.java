package simulation;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.TableParser;

import java.nio.channels.spi.AbstractSelector;


public class PreheatingSimulator {
	private TwoXOneTable t0, t2, t4;
	private OneXOneTable t6;
	private double  w74, w24, w45, w26, w60, w00, w11, w02, w22, w33;
	private double x0; // air inertia = air inside temperature
	private double x2; // ground inertia

	private FuzzyDriver driver_air, driver_ground, driver_th, driver_heat;

	private boolean isSC1;
	double x1 = 0, x3 = 0, y1 = 0, y2 = 0; //variabile auxiliare

	public PreheatingSimulator(){
		driver_air = FuzzyDriver.createDriverFromMinMax(-15, 15);
		driver_th = FuzzyDriver.createDriverFromMinMax(0, 50);
		driver_heat = FuzzyDriver.createDriverFromMinMax(-1, 1);
		driver_ground = FuzzyDriver.createDriverFromMinMax(0, 10);

	}
	public void initializeSimulation(
			String tableT0_2x1, String tableT2_2x1, String tableT4_2x1, String tableT6_1x1,
			double initialx0, double initialx2, double[] w) { // in ordine: w74, w24, w45, w26, w60, w00, w11, w02, w22, w33
		TableParser parser = new TableParser();
		this.t0 = tableT0_2x1 == null ? null : parser.parseTwoXOneTable(tableT0_2x1); // null pt scenariul 1
		this.t2 = tableT2_2x1 == null ? null : parser.parseTwoXOneTable(tableT2_2x1); // null pt scenariul 1
		if (this.t0 != null ^ this.t2 != null) throw new RuntimeException("t0 and t2 must both be null or not null");
		this.isSC1 = this.t0 != null && this.t2 != null;
		this.t4 = parser.parseTwoXOneTable(tableT4_2x1);
		this.t6 = parser.parseOneXOneTable(tableT6_1x1);

		this.x0 = initialx0;
		this.x2 = initialx2;
		//System.out.println("initialized with " + initialGroundInertia + ", " + initialAirInertia);

		this.w74 = w[0];
		this.w24 = w[1];
		this.w45 = w[2];
		this.w26 = w[3];
		this.w60 = w[4];
		this.w00 = w[5];
		this.w11 = w[6];
		this.w02 = w[7];
		this.w22 = w[8];
		this.w33 = w[9];
	}

	public double[] step(double u, double v) {
		if (this.isSC1) {
			//t0
			FuzzyToken[] outT0 = this.t0.execute(new FuzzyToken[]{
					driver_air.fuzzifie(w60 * u),
					driver_air.fuzzifie(w00 * x0)});
			x1 = driver_air.defuzzify(outT0[0]);
			//x0 ramane neschimbat
		}

		//t4
		FuzzyToken[] outT4 = this.t4.execute(new FuzzyToken[] {
				driver_ground.fuzzifie(w74 * v),
				driver_th.fuzzifie(w24 * x2)});
		double x4 = driver_heat.defuzzify(outT4[0]);
		//x2 ramane neschimbat

		if (this.isSC1) {
			//t2
			FuzzyToken[] outT2 = this.t2.execute(new FuzzyToken[]{
					driver_air.fuzzifie(w02 * x0),
					driver_th.fuzzifie(w22 * x2)});
			x3 = driver_heat.defuzzify(outT2[0]);
			//x0 si x2 raman neschimbati

			//t1
			x0 = x0 + w11 * x1;
			y1 = x0;
		}

		//t5
		x2 = x2 + w45*x4;

		//t6
		FuzzyToken[] outT6 = this.t6.execute(new FuzzyToken[] {
				driver_th.fuzzifie(w26 * x2)});
		y2 = driver_ground.defuzzify(outT6[0]);

		if (this.isSC1) {
			//t3
			x0 = x0 + x3;
			x2 = x2 - w33 * x3;
		}

		return new double[] {y1, y2};
	}
}
