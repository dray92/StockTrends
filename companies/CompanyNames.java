package companies;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;


import companies.Configs;

public class CompanyNames {
	
	private static final Logger LOGGER = Logger.getLogger(CompanyNames.class.getName());
	
	public static void downloadList() throws URISyntaxException, IOException {
		String filepath = getCompanyNamesFilepath();
		
		LOGGER.info("List of Fortune 500 companies: " + filepath);
		
		URL website = new URL(getDownloadURL());
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(filepath);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		LOGGER.info("List of Fortune 500 companies downloaded");
	}
	
	private static String getDownloadURL() {
		return Configs.BASE_FORTUNE_500_LIST + "/" + Configs.EXTENSION;
	}
	
	public static String getCompanyNamesFilepath() {
		return getFilepath(Configs.LIST_FILENAME);
	}
	
	public static String getSymbolsFilepath() {
		return getFilepath(Configs.SYMBOLS_FILENAME);
	}
	
	private static String getFilepath(String suffix) {
		Path path = null;
		try {
			path = Paths.get(CompanyNames.class.getResource(".").toURI());
		} catch (URISyntaxException e) {
			LOGGER.warning("Could not get path to current class");
		}
		Path parent = path.getParent();
		
		String filesDir = parent.toString() + "/" + Configs.FILES_DIR;
		
		// since this used the class to find the parent, src will have
		// the same structure
		// doing a bit of a hack to change /bin/ to /src/
		filesDir = filesDir.replace("/bin/", "/src/");
		
		return (filesDir + "/" + suffix);
	}

}
