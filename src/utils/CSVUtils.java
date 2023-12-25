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

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDate date = LocalDate.parse(tokens[0], formatter);

				double t1 = getDouble(tokens[1]);
				double t2 = getDouble(tokens[2]);
				double t3 = getDouble(tokens[3]);
				double t4 = getDouble(tokens[4]);
				double t5 = getDouble(tokens[5]);
				double t6 = getDouble(tokens[6]);
				double t7 = getDouble(tokens[7]);

				InRecord r = new InRecord(date, t1, t2, t3, t4, t5, t6, t7);
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

	public static void writeToCSV(double[] simulatedData, double[] measuredData,
								  String simulatedName, String measuredName, String filepath) {
		try
		{
			File f = new File(filepath);
			PrintWriter pw = new PrintWriter(f);

			pw.println(simulatedName + ";" + measuredName);
			for (int i=0;i<simulatedData.length;i++)
				pw.println(simulatedData[i] + ";" + measuredData[i]);
			pw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
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
