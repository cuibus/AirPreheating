package GA.scenario1;

import utils.CSVUtils;

public class MainPrintOutput {
	public static void main(String[] args) {
		GA.scenario0.Solution ssc0 = GA.scenario0.MainGA.bestSoFar;
		Solution ssc1 = MainGA.bestSoFar;

		double w74 = ssc0.w[0]; // from sc0
		double w24 = ssc0.w[1]; // from sc0
		double w45 = ssc0.w[2]; // from sc0
		double w26 = ssc0.w[3]; // from sc0
		double w60 = ssc1.w[0];
		double w00 = ssc1.w[1];
		double w11 = ssc1.w[2];
		double w02 = ssc1.w[3];
		double w22 = ssc1.w[4];
		double w33 = ssc1.w[5];
		double initialAirInertia = ssc1.initialAirInertia;
		double[] w = new double[]{w74, w24, w45, w26, w60, w00, w11, w02, w22, w33};

		PreHeatingFitnessFunction ff = new PreHeatingFitnessFunction();
		double fitness = ff.evaluate(ssc1.tableT0, ssc1.tableT2, ssc0.tableT4, ssc0.tableT6, w, initialAirInertia, ssc0.initialGroundInertia);

		System.out.println("SCENARIO 1. Total fitness: " + fitness);
		for (int i = 0; i < MainGA.experiments.length; i++) {
			CSVUtils.writeToCSV(
					ff.result.get(i).pipeTemp, ff.result.get(i).ref_pipeTemp,
					"simulated y1", "measured y1",
					"data/SC1_results_" + i + ".txt");
			System.out.println("Experiment " + i + ". RMS_air: " + ff.result.get(i).sumErrorSquareAir + ", maxError_air: " + ff.result.get(i).maxError_air);
			System.out.println("Experiment " + i + ". RMS_pipe: " + ff.result.get(i).sumErrorSquarePipe + ", maxError_pipe: " + ff.result.get(i).maxError_pipe);
		}

		System.out.println("table t0: " + ssc1.tableT0 + "\ntable t2: " + ssc1.tableT2);
		System.out.println("w60, w00, w11, w02, w22, w33: "+ssc1.w[0]+", "+ssc1.w[1]+", "+ssc1.w[2]+", "+ssc1.w[3]+", "+ssc1.w[4]+", "+ssc1.w[5]);
		System.out.println("initial air inertia: "+ssc1.initialAirInertia);
	}
}