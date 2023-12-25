package utils;

import org.jgap.IChromosome;

public class MappingUtils {
	public static String Mapping_2x2(IChromosome chr, int startingIndex) {
		StringBuilder result = new StringBuilder("{");
		for (int i=0;i<5;i++) {
			result.append("[");
			for (int j=0;j<5;j++) {
				int value1 = ((Integer)chr.getGene(startingIndex+i*10+2*j).getAllele()).intValue();
				int value2 = ((Integer)chr.getGene(startingIndex+i*10+2*j+1).getAllele()).intValue();
				result.append("<");
				result.append(toFuzzyValue(value1));
				result.append(",");
				result.append(toFuzzyValue(value2));
				result.append(">");
			}
			result.append("]");
		}
		result.append("}");
		return result.toString();
	}
	
	public static String Mapping_2x1(IChromosome chr, int startingIndex) {
		StringBuilder result = new StringBuilder("{");
		for (int i=0;i<5;i++) {
			result.append("[");
			for (int j=0;j<5;j++) {
				int value = ((Integer)chr.getGene(startingIndex + i*5+j).getAllele()).intValue();
				result.append("<");
				result.append(toFuzzyValue(value));
				result.append(">");
			}
			result.append("]");
		}
		result.append("}");
		return result.toString();
	}

	public static String Mapping_1x1(IChromosome chr, int startingIndex) {
		StringBuilder result = new StringBuilder("{");
		result.append("[");
		for (int i=0;i<5;i++) {
				int value = ((Integer)chr.getGene(startingIndex + i).getAllele()).intValue();
				result.append("<");
				result.append(toFuzzyValue(value));
				result.append(">");
		}
		result.append("]");
		result.append("}");
		return result.toString();
	}
	
	public static double[] Mapping_double10step(IChromosome chr, int startIndex, int nrDoubles) {
		double[] doubles = new double[nrDoubles];
		for (int i=0;i<nrDoubles;i++)
			doubles[i] = ((Integer)chr.getGene(startIndex+i).getAllele()).intValue() / 10.0;
		return doubles;
	}

	public static double[] Mapping_integer(IChromosome chr, int startIndex, int nrInts) {
		double[] ints = new double[nrInts];
		for (int i=0;i<nrInts;i++)
			ints[i] = ((Integer)chr.getGene(startIndex+i).getAllele()).intValue();
		return ints;
	}
	
	public static String mapping_2x1_follower_input2() {
		return "{[<NL><NM><ZR><PM><PL>][<NL><NM><ZR><PM><PL>][<NL><NM><ZR><PM><PL>][<NL><NM><ZR><PM><PL>][<NL><NM><ZR><PM><PL>]}"; // follower second input
		//return "{[<NL><NL><NL><NL><NL>][<NM><NM><NM><NM><NM>][<ZR><ZR><ZR><ZR><ZR>][<PM><PM><PM><PM><PM>][<PL><PL><PL><PL><PL>]}"; // follower first input
	}
	
	private static String toFuzzyValue(int value)
	{
		switch (value) {
		case 0: return "NL";
		case 1: return "NM";
		case 2: return "ZR";
		case 3: return "PM";
		case 4: return "PL";
		default: return null;
		}
	}
	
/*	public static void main(String[] args) {
		FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-30, 30);
		TableParser parser = new TableParser();
		TwoXOneTable t = parser.parseTwoXOneTable(Mapping_2x1_follower_input2());
		
		FuzzyToken[] x = t.execute(new FuzzyToken[] { driver.fuzzifie(0.4),  driver.fuzzifie(-0.2) });
		System.out.println(driver.defuzzify(x[0]));
	}
*/
}
