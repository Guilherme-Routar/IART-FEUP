package FileProcessor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ARFFheader {

	private String header = "";
	private final String lineSep = System.getProperty("line.separator");
	
	public ARFFheader() {
		buildHeader();
	}
	
	public void buildHeader() {
		header = about() + lineSep +
			  	 relation() + lineSep + lineSep +
			  	 attributes() + lineSep +
				 data();
	}
	
	public String getHeaderStr() {
		return header;
	}
	
	public String about() {
		return 
				"% 1. Title: Online News Popularity " + lineSep +
				"%" + lineSep +  
				"% 2. About:" + lineSep +
				"%      (a) Creator: João Guilherme Routar de Sousa & Telmo Luís Costa" + lineSep + 
				"%      (b) Source: UCI maching Learning Repository - Online News Popularity Data Set" + lineSep + 
				"%      (c) Date: May, 2017" + lineSep;
	}
	
	public String relation() {
		return "@RELATION onlineNewsPopularity";
	}
	
	@SuppressWarnings("rawtypes")
	public String attributes() {
		
		String attributesStr = "";
		
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
			attributesStr += "@ATTRIBUTE " + pair.getKey() + " " + pair.getValue() + lineSep;
	        it.remove(); // avoids a ConcurrentModificationException
		}
		
		return attributesStr;
	}
	
	public String data() {
		return "@DATA";
	}
}
