package companies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import companies.CompanyNames;
import utils.CSVUtils;
import utils.JsonReader;

public class PopulateCompanyNames {
	
	private static final Logger LOGGER = Logger.getLogger(PopulateCompanyNames.class.getName());
	
	public static void worker(boolean downloadFreshList,
			boolean downloadFreshTickerSymbols) throws URISyntaxException, IOException {
		if(downloadFreshList)
			CompanyNames.downloadList();
		List<List<String>> csvContents = CSVUtils.getData(CompanyNames.getCompanyNamesFilepath());
		
		List<String> companyNames = new ArrayList<String>();
		
		// first element in csvContents has column header data
		// 1st element in sublist has company name
		for(int i = 1 ; i < csvContents.size() ; i++) 
			companyNames.add(csvContents.get(i).get(1));
		
		List<String> failed = new ArrayList<String>();
		
		// set up file
		String filename = CompanyNames.getSymbolsFilepath();
		
		// delete if file existed
		File file = new File(filename);
		if (file.exists())
			file.delete();
		
		FileWriter writer = new FileWriter(filename);
		
		outer:
		for(String companyName: companyNames) {
			String URL = String.format(Configs.SYMBOL_LOOKUP_URL, companyName);
			try {
				JSONObject response = JsonReader.readJsonFromUrl(URL.trim());
				System.out.println(URL.trim());
				JSONArray results = (JSONArray) ((JSONObject)response.get("ResultSet")).get("Result");
				for(int i = 0; i < results.length() ; i++) {
					JSONObject item = (JSONObject) results.get(i);
					if(item.getString("exchDisp").equals("NYSE") || item.getString("exchDisp").equals("NASDAQ"))
						CSVUtils.writeLine(writer, Arrays.asList(item.getString("symbol"), item.getString("name")));
//					if(item.getString("symbol").equals("AAPL")) {
//						writer.flush();
//						break outer;
//					}
				}
				
			} catch(Exception e) {
				LOGGER.warning(e.getMessage());
				failed.add(companyName);
			}
		}
		for(String companyName: failed) {
			
		}
	}
}
