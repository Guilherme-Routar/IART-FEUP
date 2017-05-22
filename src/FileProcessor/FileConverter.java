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

	private static String PATH = System.getProperty("user.dir");
	
	public static void generateRandomSets() throws IOException {
		
		sortByClassType();
		createTrainTestSetsByClass();
		createTrainTestSets();
	}
	
	// This function creates 3 =/= files similar to the original with an extra attribute (popularity)
	// This attribute can be instanced as Low, Medium or High depending on the number of shares
	// Each file contains the instances of the 3 different classes
	// Low.csv - contains all the instances where class="Low", Medium.csv - (...), High.csv - (...)
	public static void sortByClassType() throws IOException {
		
		// #### FILES TO READ ####
		File originalDataset = new File(PATH, "OnlineNewsPopularity.csv");
		BufferedReader br_originalDataset = new BufferedReader(new InputStreamReader(new FileInputStream(originalDataset)));
		
		// #### FILES TO WRITE ####
		File lowInstances = new File(PATH, "low.csv");
		File mediumInstances = new File(PATH, "medium.csv");
		File highInstances = new File(PATH, "high.csv");
		
		BufferedWriter bw_lowInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lowInstances)));
		BufferedWriter bw_mediumInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mediumInstances)));
		BufferedWriter bw_highInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(highInstances)));

		try {

			String addedColumn, line = null;
			int i = 0, shares;

			for (line = br_originalDataset.readLine(); line != null; line = br_originalDataset.readLine(), i++) {

				String[] rowVal = line.split(",");

				if (i != 0) {
					// Getting number of shares to calculate the class (Low, Medium or High) 
					shares = Integer.parseInt(rowVal[60].substring(1));
					if (shares >= 0 & shares <= 1000) {
						addedColumn = String.valueOf(", Low"); //Adding extra attribute 
						bw_lowInstances.write(line + addedColumn + lineSep);
					} else if (shares >= 1001 & shares <= 2000) {
						addedColumn = String.valueOf(", Medium");
						bw_mediumInstances.write(line + addedColumn + lineSep);
					} else {
						addedColumn = String.valueOf(", High");
						bw_highInstances.write(line + addedColumn + lineSep);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_originalDataset != null) br_originalDataset.close();
			if (bw_lowInstances != null) bw_lowInstances.close();
			if (bw_mediumInstances != null) bw_mediumInstances.close();
			if (bw_highInstances != null) bw_highInstances.close();
		}
	}
	
	public static void createTrainTestSetsByClass() throws IOException {

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

		BufferedWriter bw_low_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_trainInstances)));
		BufferedWriter bw_low_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_testInstances)));
		BufferedWriter bw_medium_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_trainInstances)));
		BufferedWriter bw_medium_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_testInstances)));
		BufferedWriter bw_high_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_trainInstances)));
		BufferedWriter bw_high_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_testInstances)));

		try {

			// #### Creating low train and test files ####
			writeToFile("low", br_lowInstances, bw_low_testInstances, bw_low_trainInstances);
			
			// #### Creating medium train and test files ####
			writeToFile("medium", br_mediumInstances, bw_medium_testInstances, bw_medium_trainInstances);
			
			// #### Creating high train and test files ####
			writeToFile("high", br_highInstances, bw_high_testInstances, bw_high_trainInstances);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_lowInstances != null) br_lowInstances.close();
			if (br_mediumInstances != null) br_mediumInstances.close();
			if (br_highInstances != null) br_highInstances.close();
			if (bw_low_trainInstances != null) bw_low_trainInstances.close();
			if (bw_low_testInstances != null) bw_low_testInstances.close();
			if (bw_medium_trainInstances != null) bw_medium_trainInstances.close();
			if (bw_medium_testInstances != null) bw_medium_testInstances.close();
			if (bw_high_trainInstances != null) bw_high_trainInstances.close();
			if (bw_high_testInstances != null) bw_high_testInstances.close();
		}
		
		// Deleting unnecessary files
		lowInstances.delete();
		mediumInstances.delete();
		highInstances.delete();
		
	}
	
	public static void createTrainTestSets() throws IOException {
		
		// #### FILES TO READ ####
		File low_trainInstances = new File(PATH, "low_train.csv");
		File low_testInstances = new File(PATH, "low_test.csv");
		File medium_trainInstances = new File(PATH, "medium_train.csv");
		File medium_testInstances = new File(PATH, "medium_test.csv");
		File high_trainInstances = new File(PATH, "high_train.csv");
		File high_testInstances = new File(PATH, "high_test.csv");
		
		BufferedReader br_low_trainInstances = new BufferedReader(new InputStreamReader(new FileInputStream(low_trainInstances)));
		BufferedReader br_low_testInstances = new BufferedReader(new InputStreamReader(new FileInputStream(low_testInstances)));
		BufferedReader br_medium_trainInstances = new BufferedReader(new InputStreamReader(new FileInputStream(medium_trainInstances)));
		BufferedReader br_medium_testInstances = new BufferedReader(new InputStreamReader(new FileInputStream(medium_testInstances)));
		BufferedReader br_high_trainInstances = new BufferedReader(new InputStreamReader(new FileInputStream(high_trainInstances)));
		BufferedReader br_high_testInstances = new BufferedReader(new InputStreamReader(new FileInputStream(high_testInstances)));
		
		// #### FILES TO CREATE ####
		File trainingSet = new File(PATH, "randomTrainingSet.arff");
		File testingSet = new File(PATH, "randomTestingSet.arff"); 

		BufferedWriter bw_trainingSet = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(trainingSet)));
		BufferedWriter bw_testingSet = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testingSet)));

		try {

			// Writing arff header
			String header = build_ARFF_header();
			bw_trainingSet.write(header + lineSep);
			bw_testingSet.write(header + lineSep);
			
			// Writing training set
			writeToFile(br_low_trainInstances, bw_trainingSet);
			writeToFile(br_medium_trainInstances, bw_trainingSet);
			writeToFile(br_high_trainInstances, bw_trainingSet);
			
			// Writing testing set
			writeToFile(br_low_testInstances, bw_testingSet);
			writeToFile(br_medium_testInstances, bw_testingSet);
			writeToFile(br_high_testInstances, bw_testingSet);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_low_trainInstances != null) br_low_trainInstances.close();
			if (br_low_testInstances != null) br_low_testInstances.close();
			if (br_medium_trainInstances != null) br_medium_trainInstances.close();
			if (br_medium_testInstances != null) br_medium_testInstances.close();
			if (br_high_trainInstances != null) br_high_trainInstances.close();
			if (br_high_testInstances != null) br_high_testInstances.close();
			if (bw_trainingSet != null) bw_trainingSet.close();
			if (bw_testingSet != null) bw_testingSet.close();
		}
		
		// Deleting unnecessary files
		low_trainInstances.delete();
		low_testInstances.delete();
		medium_trainInstances.delete();
		medium_testInstances.delete();
		high_trainInstances.delete();
		high_testInstances.delete();
		
	}

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
	
	public static void writeToFile(String classType, BufferedReader bufferToRead, BufferedWriter test, BufferedWriter train) throws IOException {
		
		int testInstances = 0, totalInstances = 0;
		
		switch (classType) {
		case "low":
			testInstances = LOW_TEST_INSTANCES;
			totalInstances = LOW_TOTAL_INSTANCES;
			break;
		case "medium":
			testInstances = MEDIUM_TEST_INSTANCES;
			totalInstances = MEDIUM_TOTAL_INSTANCES;
			break;
		case "high":
			testInstances = HIGH_TEST_INSTANCES;
			totalInstances = HIGH_TOTAL_INSTANCES;
			break;
		default:
			break;
		}
		
		ArrayList<ArrayList<Integer>> randInstances = getRandomInstances(testInstances, totalInstances);
		ArrayList<Integer> randTestInstances = randInstances.get(0);
		ArrayList<Integer> randTrainInstances = randInstances.get(1);

		int i = 0;
		String line = null;
		for (line = bufferToRead.readLine(); line != null; line = bufferToRead.readLine(), i++) {
			if (randTrainInstances.contains(i))
				train.write(line + lineSep);
			if (randTestInstances.contains(i))
				test.write(line + lineSep);
		}
	}

	public static void writeToFile(BufferedReader bufferToRead, BufferedWriter bufferToWrite) throws IOException {
		
		String[] rowVal = null;
		for (String line = bufferToRead.readLine(); line != null; line = bufferToRead.readLine()) {
			rowVal = line.split(",");
			line = build_line(rowVal);
			bufferToWrite.write(line + lineSep);
		}
	}

	public static void main(String[] args) {

		// HashMap<Integer, Integer> sharesFrequency =
		// Histogram.mapSharesFrequency();
		// Histogram.createHistogram(sharesFrequency);

		try {
			sortByClassType();
			createTrainTestSetsByClass();
			createTrainTestSets();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
