import java.io.*;

//Run rsh command and return result
// ubr =UBRNAME for example busy1ubr, cmd = RSH CMD like "show pxf cpu queue", filter = regex filter for output
public class RshParser {
	String ubr, cmd, filter;

	public RshParser(String ubr, String cmd) {
		// TODO Auto-generated constructor stub
		this.ubr = ubr;
		this.cmd = cmd;
		// this.filter = filter;

	}

	public RshParser(String ubr, String cmd, String filter) {
		// TODO Auto-generated constructor stub
		this(ubr, cmd);
		this.filter = filter;

	}

	void dbug ( boolean flag, Object obj )
	{
	    String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
	    String classname = Thread.currentThread().getStackTrace()[2].getClassName();
	    String variableContents = obj.toString();
	    if (flag) System.out.println ( "Class Name:" + classname + "\nThread Name: " + methodname + "\nObject Contents:" + variableContents );
	}
	
	public String run() {
		
		String runcmd = "rsh -l " + ubr + " " + ubr + " " + cmd;
		//Commands from remote server
		//String runcmd = "ssh dliu@lucy.cogeco.net rsh -l " + ubr + " " + ubr + " " + cmd;
		System.out.println(runcmd);
		StringBuffer buffer = new StringBuffer();
		try {
			String s = null;
			Process p = Runtime.getRuntime().exec(runcmd);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));
			String line = "";
			// read the output from the command
			// System.out.println("Here is the standard output of the command:\n");
			// while ((s = stdInput.readLine()) != null
			// && s.matches(".*" + filter + ".*")) {
			while ((s = stdInput.readLine()) != null) {

				// System.out.println(s);
				buffer.append(s + "\n");

				// if (s.matches(".*" + filter + ".*"))
				// buffer.append(s + "\n");

			}

			// read any errors from the attempted command
			System.out
					.println("Here is the standard error of the command (if any):\n");
			while ((line = stdError.readLine()) != null) {
				System.out.println(line);
			}

			// System.exit(0);
		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}
		// System.out.println(buffer.toString());

		return buffer.toString();
	}
}
