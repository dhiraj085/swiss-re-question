package main;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

public class Application {

	private static final String PATTERN = "=";
	private static final String FILE_NAME = "bobs_crypto.txt";
	private static Logger logger = Logger.getLogger(ConnectionService.class.getName());

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(new File(FILE_NAME));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] input = line.split(PATTERN);

			String cryptoType = input[0];
			String rate = ConnectionService.fetchRateFromService(cryptoType);
			logger.info("Value of your current " + cryptoType + " is : " + rate + " euros");

		}
		scanner.close();

	}

}
