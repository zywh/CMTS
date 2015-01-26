import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class Parsepxf {

	public static void main(String[] args) throws IOException {
		// System.out.println(args.length);
		boolean flag;
		if (args.length < 3) {
			System.out.println("Proper Usage is: java ubrlist logfile alarmfile -v");
			System.exit(0);
		}

		if (args.length == 4)
			flag = args[3].equals("-v") ? true : false;
		else
			flag = false;

		String ubrfile = args[0];
		String logfilename = args[1];
		String alarmlogfile = args[2];
		String cmd = "show pxf cpu queue";

		// Create logfile if it doesn't exist
		File logf = new File(logfilename);
		if (!logf.exists())
			logf.createNewFile();
			

		File alarmf = new File(alarmlogfile);
		if (!alarmf.exists())
			alarmf.createNewFile();

		if (flag)
			System.out.println("FileName is " + ubrfile);

		DropCounter pxfcounter = new DropCounter();
		// Read Old Counter
		pxfcounter.readLastCounter(logfilename, flag);

		// Run RSH to get last counter and put it HashMap Class pxfcounter

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(ubrfile));

			String ubr;

			while ((ubr = inputStream.readLine()) != null) {

				ubr = ubr.split("\\s")[0];

				// Run RSH Command against UBR
				RshParser rshcmd = new RshParser(ubr, cmd);
				//String out = rshcmd.run();
				rshcmd.dbug(flag, ubr);
				 String out = "\n" +
				 "    VCCI/ClassID ClassName       QID  Length/Avg   Max    Dequeues  Drops(Tail/Random)\n"
				 +
				 " 1/0         default 131072     0/1      1024 1332934347            216763644823/0\n"
				 +
				 " 1/3         l3 131075     0/0      1024  262291035              6000012/0\n"
				 +
				 " 1/4       keepalive 131076     0/0      1024          0              0/0";

				// System.out.println(out);
				String lines[] = out.split("\\n");
				String words[] = null ;
				
				for (String line : lines) {
					//System.out.println("LINE:" + line);
					if (line.equals("")) continue;
					words = line.split("\\s+");
					if (words[2].equals("default")) {
						String drops[] = words[7].split("/");
						long defaultdrop = Long.parseLong(drops[0]);
						if (flag)
							System.out.println(ubr + ":defaultdrop-"
									+ defaultdrop);
						pxfcounter.updateCounter(ubr + ":default", defaultdrop);
					}
					// Parse l3 Drops and update Hashmap pxfcounter
					if (words[2].equals("l3")) {
						String drops[] = words[7].split("/");
						int l3drop = Integer.parseInt(drops[0]);
						if (flag)
							System.out.println(ubr + ": l3drop-" + l3drop);
						pxfcounter.updateCounter(ubr + ":l3", l3drop);
					}
				}

			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}

		// Compare old and new counter .Write into alarmlog if it's different
		pxfcounter.compareCounter(flag);
		pxfcounter.writeLogFile(logfilename, flag);
		System.out.println("Run Write Alarm File\n");
		pxfcounter.writeAlarmFile(alarmlogfile, flag);
	}
}
