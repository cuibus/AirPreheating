package utils;

import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.IChromosome;

import java.util.Arrays;
import java.util.regex.Pattern;


public class GeneticUtils {
	public static void replaceChr(Genotype population, String firstTable, String secondTable, double[] w, double initialValue, FitnessFunction fff) {
		IChromosome chr = population.getPopulation().getChromosomes().get(0);
		replaceTable(chr, firstTable, 0);
		replaceTable(chr, secondTable, 25);
		int genesForTables = (is1x1(firstTable) ? 5 : 25) + (is1x1(secondTable) ? 5 : 25);

		for (int wi=0;wi<w.length;wi++){
			chr.getGene(genesForTables + wi).setAllele((int)(w[wi]*10));
		}

		chr.getGene(genesForTables+w.length).setAllele((int)initialValue);

		chr.setFitnessValue(fff.getFitnessValue(chr));

		// a little check if everything was replaced correctly:
		IChromosome chradded = population.getPopulation().getChromosomes().get(0);
		double[] ww = MappingUtils.Mapping_double10step(chradded, genesForTables, w.length);
		double[] initialV = MappingUtils.Mapping_integer(chradded, genesForTables+w.length, 1);
		System.out.println("Was added: Fitness: " + chradded.getFitnessValue() + " Tables: " +
				"2x1: " + getTableAsString(chr, 0, false) +
				(is1x1(firstTable) ? "1x1: " : "2x1: ") + getTableAsString(chr, 25, is1x1(secondTable))  +
				" "+w.length+"w: " + Arrays.toString(ww) +
				" initialValue: "+ initialV);
	}

	private static String getTableAsString(IChromosome chr, int startingIndex, boolean is1x1not2x1){
		return is1x1not2x1 ? MappingUtils.Mapping_1x1(chr, startingIndex) : MappingUtils.Mapping_2x1(chr, startingIndex);
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

	private static boolean is1x1(String fuzzyTable){
		return Pattern.matches("\\{\\[(<(NL|NM|ZR|PM|PL)>){5}\\]\\}", fuzzyTable);
	}

	private static boolean is2x1(String fuzzyTable){
		return Pattern.matches("\\{(\\[(<(NL|NM|ZR|PM|PL)>){5}\\]){5}\\}", fuzzyTable);
	}

	private static boolean is2x2(String fuzzyTable){
		return Pattern.matches("\\{(\\[(<(NL|NM|ZR|PM|PL),(NL|NM|ZR|PM|PL)>){5}\\]){5}\\}", fuzzyTable);
	}

	private static void replaceTable(IChromosome chr, String table, int startIndex) {
		int i=0;
		if (is1x1(table)) {
			for (int geneNr = startIndex; geneNr < startIndex+5; geneNr++) {
				i = table.indexOf("<", i + 1);
				chr.getGene(geneNr).setAllele(getNumberForFuzzyValue(table.substring(i + 1, i + 3)));
			}
		}
		else if (is2x1(table)) {
			for (int geneNr = startIndex; geneNr < startIndex+25; geneNr++) {
				i = table.indexOf("<", i + 1);
				chr.getGene(geneNr).setAllele(getNumberForFuzzyValue(table.substring(i + 1, i + 3)));
			}
		}
		else if (is2x2(table)){
			for (int geneNr = startIndex; geneNr < startIndex+50; geneNr = geneNr + 2) {
				i = table.indexOf("<", i + 1);
				chr.getGene(geneNr).setAllele(getNumberForFuzzyValue(table.substring(i + 1, i + 3)));
				chr.getGene(geneNr + 1).setAllele(getNumberForFuzzyValue(table.substring(i + 4, i + 6)));
			}
		}
		else throw new RuntimeException("Weird table format received. Please check: " + table);
	}
}
