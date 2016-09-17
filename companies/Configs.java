package companies;

public class Configs {

	public static String BASE_FORTUNE_500_LIST = 
			"https://docs.google.com/"
			+ "spreadsheets/d/12G3rR5WsOJFQzkb9UHVt_U7rpFqnT3C7thKPJxn17xo";
	
	public static String EXTENSION = "export?format=csv";
	
	public static String FILES_DIR = "files";
	
	public static String LIST_FILENAME = "list.csv";
	
	public static String SYMBOLS_FILENAME = "symbols.csv";
	
	public static String SYMBOL_LOOKUP_URL = "https://s.yimg.com/aq/autoc?query=%s&region=US&lang=en-US";
	
	public static String BACKUP_SYMBOL_LOOKUP_URL = "http://chstocksearch.herokuapp.com/api/%s";
	
	/* start_date -> `Aug 19, 2005` */
	public static String STOCK_DATA = "https://www.google.com/finance/historical?output=csv&q={ticker_name}&startdate={start_date}&enddate={end_date}";
}
