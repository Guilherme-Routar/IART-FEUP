package FileProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class FileConverter {
	
	private static int LOW_TOTAL_INSTANCES = 12424;
	private static int LOW_TEST_INSTANCES = 3172;
	private static int MEDIUM_TOTAL_INSTANCES = 13604;
	private static int MEDIUM_TEST_INSTANCES = 3369;
	private static int HIGH_TOTAL_INSTANCES = 13616;
	private static int HIGH_TEST_INSTANCES = 3370;
	
	private static String lineSep = System.getProperty("line.separator");
	
	private static String PATH = "/home/routar/Desktop/Java Repository/NewsPopularityPredictor";
	
	public static String build_line(String[] rowValues) {
		
		String line = "";
		
		rowValues[0] = "";
		rowValues[1] = "";
		rowValues[60] = "";
		
		for (int i = 0; i < rowValues.length; i++) {
			
			if (i == 0 || i == 1 || i == 60 || i == 61)
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
	
	public static void sortByClass() throws IOException {

		BufferedReader br_original = null;
		
		BufferedWriter bw_low = null;
		BufferedWriter bw_medium = null;
		BufferedWriter bw_high = null;

		try {
			
			File originalSet_csv = new File(PATH, "OnlineNewsPopularity.csv");
			File low = new File(PATH, "low.csv");
			File medium = new File(PATH, "medium.csv");
			File high = new File(PATH, "high.csv");
			br_original = new BufferedReader(new InputStreamReader(new FileInputStream(originalSet_csv)));
			bw_low = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low)));
			bw_medium = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium)));
			bw_high = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high)));
			
			String addedColumn, line = null;
			int i = 0, shares;
			
			for (line = br_original.readLine(); line != null; line = br_original.readLine(), i++) {

				String[] rowVal = line.split(",");

				if (i == 0)
					addedColumn = String.valueOf(", popularity");
				else {
					shares = Integer.parseInt(rowVal[60].substring(1));
					if (shares >= 0 & shares <= 1000) {
						addedColumn = String.valueOf(", Low");
						bw_low.write(line + addedColumn + lineSep);
					}
					else if (shares >= 1001 & shares <= 2000) {
						addedColumn = String.valueOf(", Medium");
						bw_medium.write(line + addedColumn + lineSep);
					}
					else {
						addedColumn = String.valueOf(", High");
						bw_high.write(line + addedColumn + lineSep);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_original != null) br_original.close();
			if (bw_low != null) bw_low.close();
			if (bw_medium != null) bw_medium.close();
			if (bw_high != null) bw_high.close();
		}
	}
	
	public static ArrayList<ArrayList<Integer>> getRandomInstances(int testIntances, int totalInstances) {
		
		ArrayList<ArrayList<Integer>> train_test_instances = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> test_instances = new ArrayList<Integer>();
		ArrayList<Integer> train_instances = new ArrayList<Integer>();
		
		ArrayList<Integer> instances = new ArrayList<Integer>();
		
		for (int i = 0; i < totalInstances; i++)
			instances.add(new Integer(i));
		
		Collections.shuffle(instances);
		
		for (int i = 0; i < testIntances; i++)
			test_instances.add(instances.get(i));
		for (int i = testIntances; i < totalInstances; i++) 
			train_instances.add(instances.get(i));
		
		train_test_instances.add(test_instances);
		train_test_instances.add(train_instances);
		return train_test_instances;
	}
	
	public static void createTrainTest() throws IOException {

		// #### FILES TO READ ####
		File lowInstances = new File(PATH, "low.csv");
		File mediumInstances = new File(PATH, "medium.csv");
		File highInstances = new File(PATH, "high.csv");
		
		BufferedReader br_lowInstances = new BufferedReader(new InputStreamReader(new FileInputStream(lowInstances)));
		BufferedReader br_mediumInstances = new BufferedReader(new InputStreamReader(new FileInputStream(mediumInstances)));
		BufferedReader br_highInstances = new BufferedReader(new InputStreamReader(new FileInputStream(highInstances)));
		
		// #### FILES TO CREATE ####
		File low_trainInstances = new File(PATH, "low_train.csv");
		File low_testInstances = new File(PATH, "low_test.csv");
		File medium_trainInstances = new File(PATH, "medium_train.csv");
		File medium_testInstances = new File(PATH, "medium_test.csv");
		File high_trainInstances = new File(PATH, "high_train.csv");
		File high_testInstances = new File(PATH, "high_test.csv");
		
		BufferedWriter bw_low_train = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_trainInstances)));
		BufferedWriter bw_low_test = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_testInstances)));
		BufferedWriter bw_medium_train = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_trainInstances)));
		BufferedWriter bw_medium_test = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_testInstances)));
		BufferedWriter bw_high_train = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_trainInstances)));
		BufferedWriter bw_high_test = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_testInstances)));

		try {
			
			String line = null;
			int i = 0;
			
			ArrayList<ArrayList<Integer>> randomsLow = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> randomsLow_train = new ArrayList<Integer>();
			ArrayList<Integer> randomsLow_test = new ArrayList<Integer>();
			
			randomsLow = getRandomInstances(LOW_TEST_INSTANCES, LOW_TOTAL_INSTANCES);
			randomsLow_test = randomsLow.get(0);
			randomsLow_train = randomsLow.get(1);
			
			// Write Low train and test set
			for (line = br_lowInstances.readLine(); line != null; line = br_lowInstances.readLine(), i++) {

				String[] rowVal = line.split(",");
				
				if (randomsLow_train.contains(i))
					bw_low_train.write(line + lineSep);
				if (randomsLow_test.contains(i))
					bw_low_test.write(line + lineSep);
			}
			
			i = 0;
			
			ArrayList<ArrayList<Integer>> randomsMed = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> randomsMed_train = new ArrayList<Integer>();
			ArrayList<Integer> randomsMed_test = new ArrayList<Integer>();
			
			randomsMed = getRandomInstances(MEDIUM_TEST_INSTANCES, MEDIUM_TOTAL_INSTANCES);
			randomsMed_test = randomsMed.get(0);
			randomsMed_train = randomsMed.get(1);
			// Write Low train and test set
						for (line = br_mediumInstances.readLine(); line != null; line = br_mediumInstances.readLine(), i++) {

							String[] rowVal = line.split(",");
							
							if (randomsMed_train.contains(i))
								bw_medium_train.write(line + lineSep);
							if (randomsMed_test.contains(i))
								bw_medium_test.write(line + lineSep);
						}
			
					i= 0;
					
					ArrayList<ArrayList<Integer>> randomsHigh = new ArrayList<ArrayList<Integer>>();
					ArrayList<Integer> randomsHigh_train = new ArrayList<Integer>();
					ArrayList<Integer> randomsHigh_test = new ArrayList<Integer>();
					randomsHigh = getRandomInstances(HIGH_TEST_INSTANCES, HIGH_TOTAL_INSTANCES);
					randomsHigh_test = randomsHigh.get(0);
					randomsHigh_train = randomsHigh.get(1);
						// Write Low train and test set
						for (line = br_highInstances.readLine(); line != null; line = br_highInstances.readLine(), i++) {

							String[] rowVal = line.split(",");
							
							if (randomsHigh_train.contains(i))
								bw_high_train.write(line + lineSep);
							if (randomsHigh_test.contains(i))
								bw_high_test.write(line + lineSep);
						}
						
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_lowInstances != null) br_lowInstances.close();
			if (br_mediumInstances != null) br_mediumInstances.close();
			if (br_highInstances != null) br_highInstances.close();
			
			if (bw_low_train != null) bw_low_train.close();
			if (bw_low_test != null) bw_low_test.close();
			if (bw_medium_train != null) bw_medium_train.close();
			if (bw_medium_test != null) bw_medium_test.close();
			if (bw_high_train != null) bw_high_train.close();
			if (bw_high_test != null) bw_high_test.close();
		}
	}
	
	public static void concatFiles() throws IOException {
		
		BufferedReader br_low_train = null;
		BufferedReader br_low_test = null;
		BufferedReader br_medium_train = null;
		BufferedReader br_medium_test = null;
		BufferedReader br_high_train = null;
		BufferedReader br_high_test = null;
		
		BufferedWriter bw_train = null;
		BufferedWriter bw_test = null;
		
		final String lineSep = System.getProperty("line.separator");

		try {
			
			File low_train = new File(PATH, "low_train.csv");
			File low_test = new File(PATH, "low_test.csv");
			File medium_train = new File(PATH, "medium_train.csv");
			File medium_test = new File(PATH, "medium_test.csv");
			File high_train = new File(PATH, "high_train.csv");
			File high_test = new File(PATH, "high_test.csv");
			
			File low = new File(PATH, "newTraining.arff");
			File medium = new File(PATH, "newTesting.arff");
			
			br_low_train = new BufferedReader(new InputStreamReader(new FileInputStream(low_train)));
			br_low_test = new BufferedReader(new InputStreamReader(new FileInputStream(low_test)));
			br_medium_train = new BufferedReader(new InputStreamReader(new FileInputStream(medium_train)));
			br_medium_test = new BufferedReader(new InputStreamReader(new FileInputStream(medium_test)));
			br_high_train = new BufferedReader(new InputStreamReader(new FileInputStream(high_train)));
			br_high_test = new BufferedReader(new InputStreamReader(new FileInputStream(high_test)));
			
			bw_train = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low)));
			bw_test = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium)));
			
			String header = build_ARFF_header();
			bw_train.write(header + lineSep);
			bw_test.write(header + lineSep);
			
			String addedColumn, line = null;
			int i = 0, shares;
			
			//train
			for (line = br_low_train.readLine(); line != null; line = br_low_train.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				System.out.println("line = " + line);
				bw_train.write(line + lineSep);
			}
			
			for (line = br_medium_train.readLine(); line != null; line = br_medium_train.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				bw_train.write(line + lineSep);
			}
			
			for (line = br_high_train.readLine(); line != null; line = br_high_train.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				bw_train.write(line + lineSep);
			}
			
			//test
			for (line = br_low_test.readLine(); line != null; line = br_low_test.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				bw_test.write(line + lineSep);
			}
			
			for (line = br_medium_test.readLine(); line != null; line = br_medium_test.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				bw_test.write(line + lineSep);
			}
			
			for (line = br_high_test.readLine(); line != null; line = br_high_test.readLine(), i++) {
				String[] rowVal = line.split(","); 
				line = build_line(rowVal);
				bw_test.write(line + lineSep);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_low_train != null) br_low_train.close();
			if (br_low_test != null) br_low_test.close();
			if (br_medium_train != null) br_medium_train.close();
			if (br_medium_test != null) br_medium_test.close();
			if (br_high_train != null) br_high_train.close();
			if (br_high_test != null) br_high_test.close();
			if (bw_train != null) bw_train.close();
			if (bw_test != null) bw_test.close();
		}
	}

	public static void main(String[] args) {
		
		//HashMap<Integer, Integer> sharesFrequency = Histogram.mapSharesFrequency();
		//Histogram.createHistogram(sharesFrequency);
		
		try {
			sortByClass();
			createTrainTest();
			concatFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
