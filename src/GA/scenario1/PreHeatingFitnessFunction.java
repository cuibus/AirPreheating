package GA.scenario1;

import simulation.SimulationUtils;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import utils.ExperimentData;
import utils.MappingUtils;
import utils.SimulationOutput;

import java.util.ArrayList;


public class PreHeatingFitnessFunction extends FitnessFunction{

	ArrayList<SimulationOutput> result = new ArrayList<>(); //gets overwritten everytime we evaluate a chromosome
	
	public double evaluate(IChromosome chr) {
		result = new ArrayList<>();
		String tableT4 = MappingUtils.Mapping_2x1(chr, 0);
		String tableT6 = MappingUtils.Mapping_1x1(chr, 25);
		double[] w = MappingUtils.Mapping_double(chr, 30, 4);
		double[] initialGroundInertia = MappingUtils.Mapping_double(chr, 34, 1);

		double w74 = w[0];
		double w24 = w[1];
		double w45 = w[2];
		double w26 = w[3];
		double w60 = 0; // not used
		double w00 = 0; // not used
		double w11 = 0; // not used
		double w01 = 0; // not used
		double[] weights = new double[] {w74, w24, w45, w26, w60, w00, w11, w01};
		
		double fitness = evaluate(null, null, tableT4, tableT6, weights, initialGroundInertia[0]);
		return fitness;
	}
	
	public double evaluate(String tableT0_2x1, String tableT2_2x1, String tableT4_2x1, String tableT6_1x1, double[] w, double initialGroundInertia) {
		double fitness = 0;
		for (ExperimentData exp : MainGA.experiments) {
			SimulationOutput res = SimulationUtils.recordSimulation(exp, tableT0_2x1, tableT2_2x1, tableT4_2x1, tableT6_1x1, w, initialGroundInertia);
			result.add(res); // add result for each experiment
			fitness += 10 * Math.sqrt(res.sumErrorSquare) + res.maxError_air + res.maxError_pipe;
		}
		return fitness / MainGA.experiments.length;
	}

}