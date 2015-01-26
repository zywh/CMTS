import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

public class DropCounter {
	
	
	Map<String, Long> oldcounter = new HashMap<String, Long>();
	Map<String, Long> newcounter = new HashMap<String, Long>();
	String alarms = null;
	Date newtime = new Date();
	Date oldtime = new Date();
	DateFormat ft = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	public void updateCounter(String key, long counter) {
		
		newcounter.put(key, counter);
	}

	public void compareCounter(boolean flag) {

		StringBuffer buffer = new StringBuffer();
		long diff = newtime.getTime() - oldtime.getTime();
		long diffseconds = diff / 1000;
		for (Map.Entry<String, Long> entry : newcounter.entrySet()) {
			String key = entry.getKey();
			long newnumber = entry.getValue();
			
			if ( oldcounter.containsKey(key)){
			long oldnumber = oldcounter.get(key);
			
			if (newnumber != oldnumber) {
				long counterdiff = newnumber - oldnumber;
				long rate = counterdiff / diffseconds;
				String logmessage = ft.format(newtime) + ", UBR:QueueName- "
						+ key + ",newcounter:" + newnumber + ",oldcounter:"
						+ oldnumber + ",rate:" + rate;
				buffer.append(logmessage + "\n");
				// if( flag ) System.out.println(logmessage);
			}
			}else continue;
		}
		if (flag)
			System.out.println("Alarm:" + buffer.toString());
		alarms = buffer.toString();
		// return buffer.toString();
	}

	// Open LogFile and Update Key,Integer[0] as old counter

	public void readLastCounter(String logfilename, boolean flag)
			throws IOException {

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(logfilename));
			String line = null;
			while ((line = inputStream.readLine()) != null) {
				if (flag)
					System.out.println(line);
				// Logfile Format time,busy1ubr:default (or busy1ubr:l3),
				

				String[] words = line.split(",");
				// Get time from first variable
				String stringoldtime = words[0];
				try {
					oldtime = ft.parse(stringoldtime);
				} catch (ParseException e1) {

					e1.printStackTrace();
				}

				String key = words[1];
				long oldnumber = Long.parseLong(words[3]);

				oldcounter.put(key, oldnumber);
				if (flag)
					System.out.println("LoadLastCounter:" + "Keyname:" + key
							+ " oldcounter:" + oldnumber);
			}

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}

	}

	// Write HashMap to logfile. ubrname,old_counter,new_counter
	public void writeAlarmFile(String logfilename, boolean flag)
			throws IOException {

		// BufferedReader inputStream = null;
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(new FileWriter(logfilename,true));
			
		
			
			if (! alarms.isEmpty() ) outputStream.println( alarms); 
			

		} finally {

			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public void writeLogFile(String logfilename, boolean flag)
			throws IOException {

		// BufferedReader inputStream = null;
		PrintWriter outputStream = null;

		try {
			outputStream = new PrintWriter(new FileWriter(logfilename));
			for (Map.Entry<String, Long> entry : newcounter.entrySet()) {

				String time = ft.format(newtime);
				String key = entry.getKey();
				Long newnumber = entry.getValue();
				Long oldnumber = oldcounter.get(key);
				String logtxt = time + "," + key + "," + oldnumber + ","
						+ newnumber;
				if (flag)
					System.out.println("writeLogFile:" + logtxt);
				outputStream.println(logtxt);
			}

		} finally {

			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
