package GA.scenario0;

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
			CSVUtils.loadInputCSV("./data/scenario0_1.txt"),
			CSVUtils.loadInputCSV("./data/scenario0_2.txt"),
	};
	public static Solution bestSoFar =
//			new Solution("{[<PL><ZR><ZR><PM><NM>][<PL><ZR><PL><NM><ZR>][<ZR><PM><PL><ZR><PM>][<PM><PL><PL><PL><NL>][<PL><NL><ZR><PL><NM>]}",
//					"{[<NL><PM><NM><PM><PL>]}",
//					0.2,1.0,0.3,0.2,
//					37.0
//			); // fitness 1.2981826961288585
			new Solution("{[<NL><ZR><PL><PL><PL>][<ZR><ZR><NL><NM><NM>][<NL><NM><NM><PM><ZR>][<NL><ZR><NL><NL><PL>][<PL><NM><PL><PL><PL>]}",
					"{[<ZR><PL><NL><PM><NM>]}",
					0.2,0.5,0.6,0.1,
					25.0
			); // fitness 4.712237284181471
	//Fitness: 0.0 Tables: T4: {[<PM><NM><PM><ZR><PM>][<PM><ZR><NL><NL><ZR>][<ZR><ZR><ZR><ZR><PM>][<NM><ZR><ZR><ZR><NM>][<NM><NM><PM><PM><PL>]} T6: {[<PM><PL><ZR><PM><PL>]} w74, w24, w45, w26 : 0.4,0.8,1.0,0.3 initial ground inertia: 20.0

	public static void main(String[] args) throws InvalidConfigurationException{
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		//low value for fitness = better
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);

		conf.setFitnessFunction(new PreHeatingFitnessFunction());
		
		// prepare sample genes
		int nrGenes = 25 + 5 + 4 + 1;
		Gene[] sampleGenes = new Gene[nrGenes];
		for (int i=0;i<25+5;i++) //t4 si t6
			sampleGenes[i] = new IntegerGene(conf, 0, 4);
		for (int i=30;i<30+4;i++)
			sampleGenes[i] = new IntegerGene(conf, 1, 10); // 0.1, 0.2, .. 1.0
		sampleGenes[34] = new IntegerGene(conf, 0, 50); // earth thermal inertia

		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(2000);
		Genotype population = Genotype.randomInitialGenotype(conf);
		if (bestSoFar != null)
			GeneticUtils.replaceChr(population,
					bestSoFar.tableT4, bestSoFar.tableT6, bestSoFar.w, bestSoFar.initialGroundInertia,
					new PreHeatingFitnessFunction());
	
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			double[] w = MappingUtils.Mapping_double10step(bestChrSoFar, 30, 4);
			double[] initialGroundInertia = MappingUtils.Mapping_integer(bestChrSoFar, 34, 1);
			System.out.println(i + ". " +
					"Fitness: " + bestChrSoFar.getFitnessValue() +
					" Tables: T4: " + MappingUtils.Mapping_2x1(bestChrSoFar, 0) +
					" T6: " + MappingUtils.Mapping_1x1(bestChrSoFar, 25) +
					" w74, w24, w45, w26 : " + w[0] + "," + w[1] + "," + w[2] + "," + w[3] +
					" initial ground inertia: " + initialGroundInertia[0]);
		}

	}

}
