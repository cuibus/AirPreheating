package GA.scenario1;

import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;
import utils.CSVUtils;
import utils.ExperimentData;
import utils.GeneticUtils;
import utils.MappingUtils;

public class MainGA {
	private static final int MAX_ALLOWED_EVOLUTIONS = 500;
	public static ExperimentData[] experiments = new ExperimentData[] {
			// scenario 1: air doesn't flow through pipe
			CSVUtils.loadInputCSV("./data/scenario1.txt"),
		//	CSVUtils.loadInputCSV("./data/scenario1_another_one.txt"),
	};
	public static Solution bestSoFar = null;
//			new Solution("{[<PM><PL><PM><PM><ZR>][<NM><NM><NM><ZR><NL>][<PM><PL><NM><PM><ZR>][<PM><PM><NL><ZR><PM>][<PM><PM><ZR><NM><NL>]}",
//					"{[<PM><PL><PM><PM><ZR>][<NM><NM><NM><ZR><NL>][<PM><PL><NM><PM><ZR>][<PM><PM><NL><ZR><PM>][<PM><PM><ZR><NM><NL>]}",
//					0.5049886399251344,0.9505959347937725,0.5259607136457674,0.4111984332059812
//					0.5049886399251344
//			); // fitness 5.177575760652849

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
		for (int i=0;i<25+5;i++)
			sampleGenes[i] = new IntegerGene(conf, 0, 4);
		for (int i=30;i<30+4;i++)
			sampleGenes[i] = new DoubleGene(conf, 0.3, 1);
		sampleGenes[34] = new DoubleGene(conf, 0, 10); // earth thermal inertia

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
			double[] w = MappingUtils.Mapping_double(bestChrSoFar, 30, 4);
			double[] initialGroundInertia = MappingUtils.Mapping_double(bestChrSoFar, 34, 1);
			System.out.println(i + ". " +
					"Fitness: " + bestChrSoFar.getFitnessValue() +
					" Tables: T4: " + MappingUtils.Mapping_2x1(bestChrSoFar, 0) +
					" T6: " + MappingUtils.Mapping_1x1(bestChrSoFar, 25) +
					" w74, w24, w45, w26: " + w[0] + "," + w[1] + "," + w[2] + "," + w[3] +
					" initial ground inertia: " + initialGroundInertia[0]);
		}

	}

}
