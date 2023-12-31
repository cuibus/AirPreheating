package GA.scenario1;

import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import utils.CSVUtils;
import utils.ExperimentData;
import utils.GeneticUtils;
import utils.MappingUtils;

public class MainGA {
	private static final int MAX_ALLOWED_EVOLUTIONS = 500;
	public static ExperimentData[] experiments = new ExperimentData[] {
			// scenario 1: air doesn't flow through pipe
			CSVUtils.loadInputCSV("./data/scenario1_0.txt"),
	};
	public static Solution bestSoFar =
			new Solution("{[<NL><NL><NM><NL><PM>][<ZR><PM><ZR><NM><PM>][<NM><PL><PM><NL><NM>][<ZR><NL><PL><ZR><PL>][<ZR><PM><PL><NM><PM>]}",
					"{[<ZR><ZR><NL><NM><NM>][<NM><PM><ZR><NL><NM>][<ZR><PM><PM><PL><PM>][<NM><PL><ZR><PM><PM>][<PM><ZR><PM><NM><NM>]}",
					0.9,0.6,0.3,0.6,0.4,0.5,
					6.0
			); // fitness 7.679709248841521

	public static void main(String[] args) throws InvalidConfigurationException{
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		//low value for fitness = better
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);

		conf.setFitnessFunction(new PreHeatingFitnessFunction());
		
		// prepare sample genes
		int nrGenes = 25 + 25 + 6 + 1;
		Gene[] sampleGenes = new Gene[nrGenes];
		for (int i=0;i<25+25;i++) //t0
			sampleGenes[i] = new IntegerGene(conf, 0, 4);
		for (int i=25+25;i<25+25+6;i++)
			sampleGenes[i] = new IntegerGene(conf, 1, 10); // 0.1, 0.2, .. 1.0
		sampleGenes[56] = new IntegerGene(conf, -15, 15); // air thermal inertia

		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(2000);
		Genotype population = Genotype.randomInitialGenotype(conf);
		if (bestSoFar != null)
			GeneticUtils.replaceChr(population,
					bestSoFar.tableT0, bestSoFar.tableT2, bestSoFar.w, bestSoFar.initialAirInertia,
					new PreHeatingFitnessFunction());
	
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			double[] w = MappingUtils.Mapping_double10step(bestChrSoFar, 50, 6);
			double[] initialAirInertia = MappingUtils.Mapping_integer(bestChrSoFar, 56, 1);
			System.out.println(i + ". " +
					"Fitness: " + bestChrSoFar.getFitnessValue() +
					" Tables: T0: " + MappingUtils.Mapping_2x1(bestChrSoFar, 0) +
					" T2: " + MappingUtils.Mapping_2x1(bestChrSoFar, 25) +
					" w60, w00, w11, w02, w22, w33 : " + w[0] + "," + w[1] + "," + w[2] + "," + w[3] + "," + w[4] + "," + w[5] +
					" initial air inertia: " + initialAirInertia[0]);
		}

	}

}
