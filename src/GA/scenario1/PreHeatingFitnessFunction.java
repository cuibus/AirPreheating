package GA.scenario1;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import simulation.SimulationUtils;
import utils.ExperimentData;
import utils.MappingUtils;
import utils.SimulationOutput;

import java.util.ArrayList;


public class PreHeatingFitnessFunction extends FitnessFunction{

	ArrayList<SimulationOutput> result = new ArrayList<>(); //gets overwritten everytime we evaluate a chromosome
	GA.scenario0.Solution ssc0 = GA.scenario0.MainGA.bestSoFar;

	public double evaluate(IChromosome chr) {
		result = new ArrayList<>();
		String tableT0 = MappingUtils.Mapping_2x1(chr, 0);
		String tableT2 = MappingUtils.Mapping_2x1(chr, 25);
		double[] w = MappingUtils.Mapping_double10step(chr, 50, 6);
		double[] initialAirInertia = MappingUtils.Mapping_integer(chr, 56, 1);

		double w74 = ssc0.w[0];
		double w24 = ssc0.w[1];
		double w45 = ssc0.w[2];
		double w26 = ssc0.w[3];
		double w60 = w[0];
		double w00 = w[1];
		double w11 = w[2];
		double w02 = w[3];
		double w22 = w[4];
		double w33 = w[5];
		double[] weights = new double[] {w74, w24, w45, w26, w60, w00, w11, w02, w22, w33};
		
		double fitness = evaluate(tableT0, tableT2, ssc0.tableT4, ssc0.tableT6, weights, initialAirInertia[0], ssc0.initialGroundInertia);
		return fitness;
	}
	
	public double evaluate(String tableT0_2x1, String tableT2_2x1, String tableT4_2x1, String tableT6_1x1, double[] w, double initialAirInertia, double initialGroundInertia) {
		double fitness = 0;
		for (ExperimentData exp : MainGA.experiments) {
			SimulationOutput res = SimulationUtils.recordSimulation(exp, tableT0_2x1, tableT2_2x1, tableT4_2x1, tableT6_1x1, w, initialAirInertia, ssc0.initialGroundInertia);
			result.add(res); // add result for each experiment
			fitness += 10 * Math.sqrt(res.sumErrorSquarePipe) + res.maxError_pipe;
			fitness += 10 * Math.sqrt(res.sumErrorSquareAir) + res.maxError_air;
		}
		return fitness / MainGA.experiments.length;
	}

}