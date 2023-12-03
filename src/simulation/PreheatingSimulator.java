package simulation;

import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.TableParser;


public class PreheatingSimulator {
	private TwoXOneTable t0, t2, t4;
	private OneXOneTable t6;
	private double  w74, w24, w45, w26, w60, w00, w11, w01;
	private double x0; // air inertia = air inside temperature
	private double x2; // ground inertia

	public void initializeSimulation(
			String tableT0_2x1, String tableT2_2x1, String tableT4_2x1, String tableT6_1x1,
			double initialGroundInertia, double initialAirInertia, double[] w) { // in ordine: w74, w24, w45, w26, w60, w00, w11, w01
		TableParser parser = new TableParser();
		this.t0 = tableT0_2x1 == null ? null : parser.parseTwoXOneTable(tableT0_2x1); // null pt scenariul 1
		this.t2 = tableT2_2x1 == null ? null : parser.parseTwoXOneTable(tableT2_2x1); // null pt scenariul 1
		this.t4 = parser.parseTwoXOneTable(tableT4_2x1);
		this.t6 = parser.parseOneXOneTable(tableT6_1x1);

		this.x0 = initialAirInertia;
		this.x2 = initialGroundInertia;
		//System.out.println("initialized with " + initialGroundInertia + ", " + initialAirInertia);

		this.w74 = w[0];
		this.w24 = w[1];
		this.w45 = w[2];
		this.w26 = w[3];
		this.w60 = w[4];
		this.w00 = w[5];
		this.w11 = w[6];
		this.w01 = w[7];
	}
		
	public double[] step(double u, double v) {
		// TODO: write simulation algorithm for one step (should work in both scenarios)
		// TODO: use Atilla Ors's Fuzzy library
		// TODO: return a vector with [y1, y2] at this step
		return new double[] {0, 0};
	}
}
