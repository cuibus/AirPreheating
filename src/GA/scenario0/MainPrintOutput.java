package GA.scenario0;

import utils.CSVUtils;

public class MainPrintOutput {
	public static void main(String[] args) {
		Solution ssc0 = MainGA.bestSoFar;

		double w74 = ssc0.w[0];
		double w24 = ssc0.w[1];
		double w45 = ssc0.w[2];
		double w26 = ssc0.w[3];
		double w60 = 0; // not used
		double w00 = 0; // not used
		double w11 = 0; // not used
		double w02 = 0; // not used
		double w22 = 0; // not used
		double w33 = 0; // not used
		double initialGroundInertia = ssc0.initialGroundInertia;
		double[] w = new double[]{w74, w24, w45, w26, w60, w00, w11, w02, w22, w33};

		PreHeatingFitnessFunction ff = new PreHeatingFitnessFunction();
		double fitness = ff.evaluate(null, null, ssc0.tableT4, ssc0.tableT6, w, initialGroundInertia);

		System.out.println("SCENARIO 0. Total fitness: " + fitness);
		for (int i = 0; i < MainGA.experiments.length; i++) {
			CSVUtils.writeToCSV(
					ff.result.get(i).pipeTemp, ff.result.get(i).ref_pipeTemp,
					"simulated y2", "measured y2",
					"data/SC0_results_" + i + ".txt");
			System.out.println("Experiment " + i + ". RMS_pipe: " + ff.result.get(i).sumErrorSquarePipe + ", maxError_pipe: " + ff.result.get(i).maxError_pipe);
		}

		System.out.println("table t4: " + ssc0.tableT4 + "\ntable t6: " + ssc0.tableT6);
		System.out.println("w74,w24,w45,w26: "+ssc0.w[0]+", "+ssc0.w[1]+", "+ssc0.w[2]+", "+ssc0.w[3]);
		System.out.println("initial ground inertia: "+ssc0.initialGroundInertia);
	}
}