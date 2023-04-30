package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

	private File logFile;
	private FileWriter writer;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y-d-M-H-m-s");
	private DateTimeFormatter logDTF = DateTimeFormatter.ofPattern("H'H'-m'M'-s'S'");

	private static Log instance;

	private Log() {
		File logsDirectory = new File("logs");
		if (!logsDirectory.exists()) {
		    logsDirectory.mkdirs();
		}

		LocalDateTime now = LocalDateTime.now();
		String fileName = "Log-" + dtf.format(now);
		logFile = new File(logsDirectory, fileName);
		if (!logFile.exists()) {
		    try {
		        logFile.createNewFile();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		try {
			writer = new FileWriter(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initilize() {
		instance = new Log();

		Log.info("Log file sucessfully initialized");
	}

	public static void info(String message) {
		try {
			String timeAsString = instance.logDTF.format(LocalDateTime.now());
			instance.writer.append("\n[Log]" + message + " [Time] " + timeAsString);
			instance.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			instance.writer.flush();
			instance.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
