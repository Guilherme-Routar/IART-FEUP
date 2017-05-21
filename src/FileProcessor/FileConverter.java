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
					} else if (shares >= 1001 & shares <= 2000) {
						addedColumn = String.valueOf(", Medium");
						bw_medium.write(line + addedColumn + lineSep);
					} else {
						addedColumn = String.valueOf(", High");
						bw_high.write(line + addedColumn + lineSep);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br_original != null)
				br_original.close();
			if (bw_low != null)
				bw_low.close();
			if (bw_medium != null)
				bw_medium.close();
			if (bw_high != null)
				bw_high.close();
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

		BufferedWriter bw_low_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_trainInstances)));
		BufferedWriter bw_low_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(low_testInstances)));
		BufferedWriter bw_medium_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_trainInstances)));
		BufferedWriter bw_medium_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(medium_testInstances)));
		BufferedWriter bw_high_trainInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_trainInstances)));
		BufferedWriter bw_high_testInstances = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(high_testInstances)));

		try {

			String line = null;
			int i = 0;

			// #### Creating low train and test files ####
			ArrayList<ArrayList<Integer>> low_RandInstances = getRandomInstances(LOW_TEST_INSTANCES, LOW_TOTAL_INSTANCES);
			ArrayList<Integer> low_RandTestInstances = low_RandInstances.get(0);
			ArrayList<Integer> low_RandTrainInstances = low_RandInstances.get(1);

			for (line = br_lowInstances.readLine(); line != null; line = br_lowInstances.readLine(), i++) {
				if (low_RandTrainInstances.contains(i))
					bw_low_trainInstances.write(line + lineSep);
				if (low_RandTestInstances.contains(i))
					bw_low_testInstances.write(line + lineSep);
			}

			// #### Creating medium train and test files ####
			ArrayList<ArrayList<Integer>> medium_RandInstances = getRandomInstances(MEDIUM_TEST_INSTANCES, MEDIUM_TOTAL_INSTANCES);
			ArrayList<Integer> medium_RandTestInstances = medium_RandInstances.get(0);
			ArrayList<Integer> medium_RandTrainInstances = medium_RandInstances.get(1);

			i = 0;
			for (line = br_mediumInstances.readLine(); line != null; line = br_mediumInstances.readLine(), i++) {
				if (medium_RandTrainInstances.contains(i))
					bw_medium_trainInstances.write(line + lineSep);
				if (medium_RandTestInstances.contains(i))
					bw_medium_testInstances.write(line + lineSep);
			}

			// #### Creating high train and test files ####
			ArrayList<ArrayList<Integer>> high_RandInstances = getRandomInstances(HIGH_TEST_INSTANCES, HIGH_TOTAL_INSTANCES);
			ArrayList<Integer> high_RandTestInstances = high_RandInstances.get(0);
			ArrayList<Integer> high_RandTrainInstances = high_RandInstances.get(1);

			i = 0;
			for (line = br_highInstances.readLine(); line != null; line = br_highInstances.readLine(), i++) {
				if (high_RandTrainInstances.contains(i))
					bw_high_trainInstances.write(line + lineSep);
				if (high_RandTestInstances.contains(i))
					bw_high_testInstances.write(line + lineSep);
			}

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
	}
	
	public static void writeToFile(BufferedReader bufferToRead, BufferedWriter bufferToWrite) throws IOException {
		
		String[] rowVal = null;
		for (String line = bufferToRead.readLine(); line != null; line = bufferToRead.readLine()) {
			rowVal = line.split(",");
			line = build_line(rowVal);
			bufferToWrite.write(line + lineSep);
		}
	}

	public static void concatFiles() throws IOException {
		
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
		File trainingSet = new File(PATH, "newTraining.arff");
		File testingSet = new File(PATH, "newTesting.arff");

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
	}

	public static void main(String[] args) {

		// HashMap<Integer, Integer> sharesFrequency =
		// Histogram.mapSharesFrequency();
		// Histogram.createHistogram(sharesFrequency);

		try {
			sortByClass();
			createTrainTest();
			concatFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
