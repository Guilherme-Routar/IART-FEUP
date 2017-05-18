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

public class nominalClassifier {
	
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
		
		String header = "";
		
		final String lineSep = System.getProperty("line.separator");
		
		// About section
		String about = "% 1. Title: Online News Popularity " + lineSep +
					   "%" + lineSep +  
					   "% 2. About:" + lineSep +
					   "%      (a) Creator: João Guilherme Routar de Sousa & Telmo Luís Costa" + lineSep + 
					   "%      (b) Source: UCI maching Learning Repository - Online News Popularity Data Set" + lineSep + 
					   "%      (c) Date: May, 2017" + lineSep;
		
		header = about + lineSep;
		
		// Relation section
		String relation = "@RELATION onlineNewsPopularity";
		header += relation + lineSep + lineSep;
		
		// Attributes section
		LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put("n_tokens_title", "numeric");
		attributes.put("n_tokens_content", "numeric");
		attributes.put("n_unique_tokens", "numeric");
		attributes.put("n_non_stop_words", "numeric");
		attributes.put("n_non_stop_unique_tokens", "numeric");
		attributes.put("num_hrefs", "numeric");
		attributes.put("num_self_hrefs", "numeric");
		attributes.put("num_imgs", "numeric");
		attributes.put("num_videos", "numeric");
		attributes.put("average_token_length", "numeric");
		attributes.put("num_keywords", "numeric");
		attributes.put("data_channel_is_lifestyle", "{0.0, 1.0}");
		attributes.put("data_channel_is_entertainment", "{0.0, 1.0}");
		attributes.put("data_channel_is_bus", "{0.0, 1.0}");
		attributes.put("data_channel_is_socmed", "{0.0, 1.0}");
		attributes.put("data_channel_is_tech", "{0.0, 1.0}");
		attributes.put("data_channel_is_world", "{0.0, 1.0}");
		attributes.put("kw_min_min", "numeric");
		attributes.put("kw_max_min", "numeric");
		attributes.put("kw_avg_min", "numeric");
		attributes.put("kw_min_max", "numeric");
		attributes.put("kw_max_max", "numeric");
		attributes.put("kw_avg_max", "numeric");
		attributes.put("kw_min_avg", "numeric");
		attributes.put("kw_max_avg", "numeric");
		attributes.put("kw_avg_avg", "numeric");
		attributes.put("self_reference_min_shares", "numeric");
		attributes.put("self_reference_max_shares", "numeric");
		attributes.put("self_reference_avg_shares", "numeric");
		attributes.put("weekday_is_monday", "{0.0, 1.0}");
		attributes.put("weekday_is_tuesday", "{0.0, 1.0}");
		attributes.put("weekday_is_wednesday", "{0.0, 1.0}");
		attributes.put("weekday_is_thursday", "{0.0, 1.0}");
		attributes.put("weekday_is_friday", "{0.0, 1.0}");
		attributes.put("weekday_is_saturday", "{0.0, 1.0}");
		attributes.put("weekday_is_sunday", "{0.0, 1.0}");
		attributes.put("is_weekend", "{0.0, 1.0}");
		attributes.put("LDA_00", "numeric");
		attributes.put("LDA_01", "numeric");
		attributes.put("LDA_02", "numeric");
		attributes.put("LDA_03", "numeric");
		attributes.put("LDA_04", "numeric");
		attributes.put("global_subjectivity", "numeric");
		attributes.put("global_sentiment_polarity", "numeric");
		attributes.put("global_rate_positive_words", "numeric");
		attributes.put("global_rate_negative_words", "numeric");
		attributes.put("rate_positive_words", "numeric");
		attributes.put("rate_negative_words", "numeric");
		attributes.put("avg_positive_polarity", "numeric");
		attributes.put("min_positive_polarity", "numeric");
		attributes.put("max_positive_polarity", "numeric");
		attributes.put("avg_negative_polarity", "numeric");
		attributes.put("min_negative_polarity", "numeric");
		attributes.put("max_negative_polarity", "numeric");
		attributes.put("title_subjectivity", "numeric");
		attributes.put("title_sentiment_polarity", "numeric");
		attributes.put("abs_title_subjectivity", "numeric");
		attributes.put("abs_title_sentiment_polarity", "numeric");
		attributes.put("popularity", "{Low, Medium, High}");
		
		Iterator it = attributes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			header += "@ATTRIBUTE " + pair.getKey() + " " + pair.getValue() + lineSep;
	        it.remove(); // avoids a ConcurrentModificationException
		}
		header += lineSep;
		
		// Data section
		String data = "@DATA";
		header += data;
		
		System.out.println("Header = \n" + header);
		return header;
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
