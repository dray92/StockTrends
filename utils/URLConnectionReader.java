package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import companies.Configs;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	String urlTemplate = Configs.STOCK_DATA;
    	
    	Map<String, String> map = new HashMap<String, String>();
        map.put("ticker_name", "BRK-A");
        map.put("start_date", getURLEncoded("Aug 9, 2016"));
        map.put("end_date", getURLEncoded("Aug 19, 2016"));
        
        String urlString = MapFormat.format(urlTemplate, map);
               
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                conn.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
    }
    
    private static String getURLEncoded(String st) throws UnsupportedEncodingException {
    	return URLEncoder.encode(st, "UTF-8");
    }
}