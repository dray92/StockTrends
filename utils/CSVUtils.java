package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CSVUtils {

	private static final Logger LOGGER = Logger.getLogger(CSVUtils.class
			.getName());

	public static List<List<String>> getData(String sourceFilepath)
			throws FileNotFoundException {
		File f = new File(sourceFilepath);
		List<List<String>> list = new ArrayList<List<String>>();
		if (f.exists() && !f.isDirectory()) {
			BufferedReader br = null;
			String line = "";
			String comma = ",";
			try {
				br = new BufferedReader(new FileReader(sourceFilepath));
				while ((line = br.readLine()) != null) {
					String[] contents = line.split(comma);
					list.add(Arrays.asList(contents));
				}

			} catch (IOException e) {
				LOGGER.warning("IOException while reading " + sourceFilepath);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			LOGGER.info("Contents of " + sourceFilepath
					+ " read in successfully");
		} else
			throw new FileNotFoundException(sourceFilepath + " is not a file");
		return list;
	}

	private static final char DEFAULT_SEPARATOR = ',';

	public static void writeLine(Writer w, List<String> values)
			throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}

	public static void writeLine(Writer w, List<String> values, char separators)
			throws IOException {
		writeLine(w, values, separators, ' ');
	}

	// https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	public static void writeLine(Writer w, List<String> values,
			char separators, char customQuote) throws IOException {

		boolean first = true;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value))
						.append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
}
