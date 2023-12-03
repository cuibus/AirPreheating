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
			double[] w, // in order: w74, w24, w45, w26, w60, w00, w11, w01
			double initialGroundInertia
	){
		SimulationOutput result = new SimulationOutput(experiment.inputData.size());
		simulator.initializeSimulation(tableT0_2x1, tableT2_2x1, tableT4_2x1, tableT6_1x1,
				initialGroundInertia, experiment.inputData.get(0).airTemp,  w);
		result.sumErrorSquare = 0;
		result.maxError_air = 0;
		result.maxError_pipe = 0;
		for (int t=0;t<experiment.inputData.size();t++) {
			double[] y = simulator.step(experiment.inputData.get(t).outTemp, experiment.inputData.get(t).earthTemp);
			result.pipeTemp[t] = y[0];
			result.airTemp[t] = y[1];
			result.ref_pipeTemp[t] = experiment.inputData.get(t).pipeTemp;
			result.ref_airTemp[t] = experiment.inputData.get(t).airTemp;

			double error_pipe = Math.abs(result.pipeTemp[t] - experiment.inputData.get(t).pipeTemp);
			double error_air = Math.abs(result.airTemp[t] - experiment.inputData.get(t).airTemp);
			result.maxError_air = Math.max(result.maxError_air, error_air);
			result.maxError_pipe = Math.max(result.maxError_pipe, error_pipe);
			result.sumErrorSquare += error_pipe + error_air;
		}
		result.sumErrorSquare = result.sumErrorSquare / experiment.inputData.size();
		return result;
	}
}
