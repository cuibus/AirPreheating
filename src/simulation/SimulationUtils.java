package simulation;

import utils.ExperimentData;
import utils.SimulationOutput;


public class SimulationUtils {
	public static PreheatingSimulator simulator = new PreheatingSimulator();
	
	public static SimulationOutput recordSimulation(
			ExperimentData experiment,
			String tableT0_2x1,
			String tableT2_2x1,
			String tableT4_2x1,
			String tableT6_1x1,
			double[] w, // in order: w74, w24, w45, w26, w60, w00, w11, w02, w22, w33
			double initialx0,
			double initialx2
	){
		SimulationOutput result = new SimulationOutput(experiment.inputData.size());
		simulator.initializeSimulation(tableT0_2x1, tableT2_2x1, tableT4_2x1, tableT6_1x1,
				initialx0, initialx2,  w);
		result.sumErrorSquareAir = 0;
		result.sumErrorSquarePipe = 0;
		result.maxError_air = 0;
		result.maxError_pipe = 0;
		for (int t=0;t<experiment.inputData.size();t++) {
			double[] y = simulator.step(experiment.inputData.get(t).t5, experiment.inputData.get(t).t7);
			result.airTemp[t] = y[0];
			result.pipeTemp[t] = y[1];
			result.ref_pipeTemp[t] = experiment.inputData.get(t).t4;
			result.ref_airTemp[t] = experiment.inputData.get(t).t6;

			double error_pipe = Math.abs(result.pipeTemp[t] - result.ref_pipeTemp[t]);
			double error_air = Math.abs(result.airTemp[t] - result.ref_airTemp[t]);
			result.maxError_air = Math.max(result.maxError_air, error_air);
			result.maxError_pipe = Math.max(result.maxError_pipe, error_pipe);
			result.sumErrorSquareAir += error_air;
			result.sumErrorSquarePipe += error_pipe;
		}
		result.sumErrorSquareAir = result.sumErrorSquareAir / experiment.inputData.size();
		result.sumErrorSquarePipe = result.sumErrorSquarePipe / experiment.inputData.size();
		return result;
	}
}
