package FileProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 * @author Guilherme Routar & Luís Costa
 * 
 * The class Histogram implements 2 static functions which enable the creation of an 
 * histogram based on the dataset provided
 */
public class Histogram {
	
	public static HashMap<Integer, Integer> mapSharesFrequency() {
		
		HashMap<Integer, Integer> sharesFreq = new HashMap<Integer, Integer>();
		String csvFile = "OnlineNewsPopularity.csv";
		BufferedReader br = null;
		String line = "";
		int share;
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			// Ignoring CSV header
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] shares = line.split(",");
				share = Integer.parseInt(shares[60].substring(1));

				if (sharesFreq.containsKey(share))
					sharesFreq.put(share, sharesFreq.get(share) + 1);
				else
					sharesFreq.put(share, 1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sharesFreq;
	}
	
	public static void createHistogram(HashMap<Integer, Integer> hashMap) {

		String star = "*";
		SortedSet<Integer> keys = new TreeSet<Integer>(hashMap.keySet());
		for (Integer key : keys) {
			Integer value = hashMap.get(key);
			String nStar = String.format(String.format("%%%ds", value), " ").replace(" ", star);
			System.out.println(key + " - " + nStar);
		}
	}
}
