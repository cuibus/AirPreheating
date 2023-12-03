package utils;

import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.IChromosome;

import java.util.Arrays;
import java.util.regex.Pattern;


public class GeneticUtils {
	public static void replaceChr(Genotype population, String table4, String table6, double[] w, double initialValue, FitnessFunction fff) {
		IChromosome chr = population.getPopulation().getChromosomes().get(0);
		replaceTable(chr, table4, 0);
		replaceTable(chr, table6, 25);

		for (int wi=0;wi<w.length;wi++){
			chr.getGene(50+wi).setAllele(w[wi]);
		}

		chr.getGene(50+w.length).setAllele(initialValue);

		chr.setFitnessValue(fff.getFitnessValue(chr));

		// a little check if everything was replaced correctly:
		IChromosome chradded = population.getPopulation().getChromosomes().get(0);
		double[] ww = MappingUtils.Mapping_double(chradded, 50, w.length);
		double[] initial = MappingUtils.Mapping_double(chradded, 50+w.length, 1);
		System.out.println("Was added: Fitness: " + chradded.getFitnessValue() + " Tables: " +
				"2x1: " + MappingUtils.Mapping_2x1(chradded, 0) +
				"2x1: " + MappingUtils.Mapping_2x1(chradded, 25) +
				" "+w.length+"w: " + Arrays.toString(ww) +
				" initialValue: "+ initial);
	}

	private static int getNumberForFuzzyValue(String fuzzyValueAsString) {
		switch(fuzzyValueAsString) {
			case "NL": return 0;
			case "NM": return 1;
			case "ZR": return 2;
			case "PM": return 3;
			case "PL": return 4;
			default: return -1;
		}
	}

	private static boolean is2x1(String fuzzyTable){
		return Pattern.matches("\\{(\\[(<(NL|NM|ZR|PM|PL)>){5}\\]){5}\\}", fuzzyTable);
	}

	private static boolean is2x2(String fuzzyTable){
		return Pattern.matches("\\{(\\[(<(NL|NM|ZR|PM|PL),(NL|NM|ZR|PM|PL)>){5}\\]){5}\\}", fuzzyTable);
	}

	private static void replaceTable(IChromosome chr, String table, int startIndex) {
		int i=0;
		if (is2x1(table)) {
			for (int geneNr = startIndex; geneNr < startIndex+25; geneNr++) {
				i = table.indexOf("<", i + 1);
				chr.getGene(geneNr).setAllele(getNumberForFuzzyValue(table.substring(i + 1, i + 3)));
			}
		}
		else {
			for (int geneNr = startIndex; geneNr < startIndex+50; geneNr = geneNr + 2) {
				i = table.indexOf("<", i + 1);
				chr.getGene(geneNr).setAllele(getNumberForFuzzyValue(table.substring(i + 1, i + 3)));
				chr.getGene(geneNr + 1).setAllele(getNumberForFuzzyValue(table.substring(i + 4, i + 6)));
			}
		}
	}
}
