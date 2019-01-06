package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ConnectionService {

	private static final Logger logger = Logger.getLogger(ConnectionService.class.getName());
	private static final String START_INDEX = ":";
	private static final String END_INDEX = "}";
	
	public static String fetchRateFromService(String cryptoType) {
		String parsedString = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=" + cryptoType + "&tsyms=EUR");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = null;
			while ((output = br.readLine()) != null) {
				parsedString = parseJsonString(output);
			}

		} catch (MalformedURLException e) {
			logger.info("Error fetching from url " + e.getMessage());
		} catch (IOException e) {
			logger.info("Error while reading response " + e.getMessage());
		} finally {
			try {
				br.close();
				conn.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsedString;

	}

	private static String parseJsonString(String input) {
		String output = input.substring(input.indexOf(START_INDEX) + 1, input.indexOf(END_INDEX));
		return output;

	}

}
