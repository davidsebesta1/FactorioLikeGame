package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Log class writes a log file into a .txt file for debugging purposes.
 * @author David Šebesta
 *
 */
public class Log {

	private File logFile;
	private FileWriter writer;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y-d-M-H-m-s");
	private DateTimeFormatter logDTF = DateTimeFormatter.ofPattern("H'H'-m'M'-s'S'");

	private static Log instance;

	/**
	 * Private constructor.
	 */
	private Log() {
		File logsDirectory = new File("logs");
		if (!logsDirectory.exists()) {
		    logsDirectory.mkdirs();
		}

		LocalDateTime now = LocalDateTime.now();
		String fileName = "Log-" + dtf.format(now) + ".txt";
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
		
		//Uncaugh exception logging
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread t, Throwable e) {
		    	
		    	StringWriter sw = new StringWriter();
		    	PrintWriter pw = new PrintWriter(sw);
		    	e.printStackTrace(pw);
		    	String trace = sw.toString();
		    	
		    	Log.error("Uncaught exception:" + trace);
		        System.exit(1);
		    }
		});
	}

	/**
	 * Initialize Log.
	 */
	public static void initilize() {
		instance = new Log();

		Log.info("Log file sucessfully initialized");
	}

	/**
	 * Writes a information to a file.
	 * @param message
	 */
	public static void info(String message) {
		try {
			String timeAsString = instance.logDTF.format(LocalDateTime.now());
			instance.writer.append("\n[Info] " + message + " [Time] " + timeAsString);
			instance.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a warning to a file.
	 * @param message
	 */
	public static void warn(String message) {
		try {
			String timeAsString = instance.logDTF.format(LocalDateTime.now());
			instance.writer.append("\n[WARNING] " + message + " [Time] " + timeAsString);
			instance.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a error to a file.
	 * @param message
	 */
	public static void error(String message) {
		try {
			String timeAsString = instance.logDTF.format(LocalDateTime.now());
			instance.writer.append("\n[ERROR] " + message + " [Time] " + timeAsString);
			instance.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the writer.
	 */
	public static void close() {
		try {
			instance.writer.flush();
			instance.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
