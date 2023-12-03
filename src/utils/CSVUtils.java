package utils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CSVUtils {
	//reads from input file, in this order: datetime, earthTemp, airTemp, outTemp, pipeTemp) {
	public static ExperimentData loadInputCSV(String filepath) {
		ExperimentData exp = new ExperimentData();
		exp.inputData = new ArrayList<InRecord>();
		try	{  
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			
			reader.readLine(); // descriere experiment
			reader.readLine(); // linie goala
			reader.readLine(); // cap de tabel
			reader.readLine(); // linie goala			
			String line;
			while ((line  = reader.readLine())!= null) {
				String[] tokens = line.split(";");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDate date = LocalDate.parse(tokens[0], formatter);

				double earthTemp = getDouble(tokens[1]);
				double airTemp = getDouble(tokens[2]);
				double outTemp = getDouble(tokens[3]);
				double pipeTemp = getDouble(tokens[4]);

				InRecord r = new InRecord(date, earthTemp, airTemp, outTemp, pipeTemp);
				exp.inputData.add(r);
			}
			reader.close();
		}  
		catch(IOException e)  
		{  
			e.printStackTrace();  
		} 
		return exp;
	}
	
	private static double getDouble(String s) {
		try {
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e) {
			return Double.NaN;
		}
	}
}
