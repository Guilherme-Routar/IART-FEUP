package FileProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileConverter {
	
	public static String build_line(String[] rowValues) {
		
		String line = "";
		
		rowValues[0] = "";
		rowValues[1] = "";
		rowValues[60] = "";
		
		for (int i = 0; i < rowValues.length; i++) {
			
			if (i == 0 || i == 1 || i == 59 || i == 60)
				line += rowValues[i];
			else
				line += rowValues[i] + ",";
		}
		
		return line = line.substring(1);
	}
	
	public static String build_ARFF_header() {
		
		ARFFheader header = new ARFFheader();
		return header.getHeaderStr();
	}

	public static void create_training_testing_sets(String path, String fileName) throws IOException {

		BufferedReader br_original = null;
		BufferedWriter bw_training = null;
		BufferedWriter bw_testing = null;
		
		final String lineSep = System.getProperty("line.separator");

		try {
			
			File originalSet_csv = new File(path, fileName + ".csv");
			File trainingSet_arff = new File(path, "TrainingSet.arff");
			File testingSet_arff = new File(path, "TestingSet.arff");
			br_original = new BufferedReader(new InputStreamReader(new FileInputStream(originalSet_csv)));
			bw_training = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(trainingSet_arff)));
			bw_testing = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testingSet_arff)));
			
			String header = build_ARFF_header();
			bw_training.write(header + lineSep);
			bw_testing.write(header + lineSep);
			
			String addedColumn, line = null;
			int i = 0, shares;
			int low = 0, medium = 0, high = 0;
			
			for (line = br_original.readLine(); line != null; line = br_original.readLine(), i++) {

				String[] rowVal = line.split(",");

				if (i == 0)
					addedColumn = String.valueOf(", popularity");
				else {
					shares = Integer.parseInt(rowVal[60].substring(1));
					if (shares >= 0 & shares <= 1000) {
						addedColumn = String.valueOf(", Low");
						low++;
						line = build_line(rowVal);
						if (low <= 3172)
							bw_testing.write(line + addedColumn + lineSep);
						else
							bw_training.write(line + addedColumn + lineSep);
					}
					else if (shares >= 1001 & shares <= 2000) {
						addedColumn = String.valueOf(", Medium");
						medium++;
						line = build_line(rowVal);
						if (medium <= 3369)
							bw_testing.write(line + addedColumn + lineSep);
						else
							bw_training.write(line + addedColumn + lineSep);
					}
					else {
						addedColumn = String.valueOf(", High");
						high++;
						line = build_line(rowVal);
						if (high <= 3370)
							bw_testing.write(line + addedColumn + lineSep);
						else
							bw_training.write(line + addedColumn + lineSep);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_original != null) br_original.close();
			if (bw_training != null) bw_training.close();
			if (bw_testing != null) bw_testing.close();
		}
	}	

	public static void main(String[] args) {
		
		HashMap<Integer, Integer> sharesFrequency = Histogram.mapSharesFrequency();
		Histogram.createHistogram(sharesFrequency);
		
		try {
			create_training_testing_sets("/home/routar/Desktop/Java Repository/NewsPopularityPredictor", "OnlineNewsPopularity");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
