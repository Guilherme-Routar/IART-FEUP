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

public class nominalClassifier {

	public static void createNominalClassifier(String path, String fileName) throws IOException {

		BufferedReader br = null;
		BufferedWriter bw = null;
		final String lineSep = System.getProperty("line.separator");

		try {
			File onlineNewsFile = new File(path, fileName + ".csv");
			File qualitativeOnlineNewsFile = new File(path, fileName + "Qualitative.csv");
			br = new BufferedReader(new InputStreamReader(new FileInputStream(onlineNewsFile)));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(qualitativeOnlineNewsFile)));
			
			String addedColumn, line = null;
			int share;
			int i = 0;
			for (line = br.readLine(); line != null; line = br.readLine(), i++) {

				String[] shares = line.split(",");

				if (i == 0)
					addedColumn = String.valueOf(", popularity");
				else {
					share = Integer.parseInt(shares[60].substring(1));
					if (share >= 1 & share <= 500)
						addedColumn = String.valueOf(", Low");
					else if (share >= 501 & share <= 1000)
						addedColumn = String.valueOf(", Medium");
					else if (share >= 1001 & share <= 10000)
						addedColumn = String.valueOf(", Medium-High");
					else
						addedColumn = String.valueOf(", High");
				}
				bw.write(line + addedColumn + lineSep);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br != null)
				br.close();
			if (bw != null)
				bw.close();
		}
	}	

	public static void main(String[] args) {
		
		HashMap<Integer, Integer> sharesFrequency = Histogram.mapSharesFrequency();
		Histogram.createHistogram(sharesFrequency);
		
		try {
			createNominalClassifier("/home/routar/Desktop/Java Repository/NewsPopularityPredictor", "OnlineNewsPopularity");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
