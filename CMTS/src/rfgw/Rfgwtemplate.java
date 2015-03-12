package rfgw;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.io.InputStream;
//import java.util.Date;
import java.util.Properties;

import javax.swing.JFileChooser;

public class Rfgwtemplate {

	static Map<String, Integer> lqamgroup = new HashMap<String, Integer>();

	static Properties prop = new Properties();
	
	Rfgwtemplate() {
		
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Proper Usage is: java configfile");
			System.exit(0);
		}
		String filename = args[0];

		FileInputStream in = new FileInputStream(filename);
		// Read the property file
		prop.load(in);
		in.close();

		lqamload();
		// Check How Many USRM in the file
		int usrm = 0;
		for (int i = 1; i <= 4; i++) {
			String key = "USRM" + i;
			// System.out.println(key);
			if (prop.getProperty(key) != null)
				usrm = usrm + 1;
		}

		// List<String> sdviplist = loopback1(usrm); // Get SDV IPs
		// List <String> vodiplist = null ;
		portchannel(); // Generate Port Channel Configuration
		// if (prop.getProperty("LOOPBACK2_START") != null ) { vodiplist =
		// loopback2(); } //VoD IPs
		//
		// qp(usrm, sdviplist); //Generate QP Configuration
		// videoroute(usrm, sdviplist ,vodiplist); //Generate Video Load Balance
		// Group Configuration
		// qam(); //Generate Basic Video QAM Configuration

	}

	public static void portchannel() throws IOException {
		String s = "#Port Channel and Default Route Section Start\n";
		String pcname = prop.getProperty("PORTCHANNEL").replaceAll("\\s+", "");
		String pcip = prop.getProperty("PORTCHANNELIP").replaceAll("\\s+", "");
		String[] parts = pcip.split("/");
		String ip = parts[0];
		String[] ipaddr = ip.split("\\.");
		int gw = Integer.parseInt(ipaddr[3]) + 1;
		String defaultgw = ipaddr[0] + "." + ipaddr[1] + "." + ipaddr[2] + "."
				+ Integer.toString(gw);
		s = s + "ip route 0.0.0.0 0.0.0.0 " + defaultgw + "\n";
		int prefix;
		if (parts.length < 2) {
			prefix = 0;
		} else {
			prefix = Integer.parseInt(parts[1]);
		}
		int mask = 0xffffffff << (32 - prefix);
		// System.out.println("Prefix=" + prefix);
		// System.out.println("Address=" + ip);

		int value = mask;
		byte[] bytes = new byte[] { (byte) (value >>> 24),
				(byte) (value >> 16 & 0xff), (byte) (value >> 8 & 0xff),
				(byte) (value & 0xff) };

		InetAddress netAddr = InetAddress.getByAddress(bytes);
		String netmask = netAddr.getHostAddress();
		s = s + "interface " + pcname + "\n" + "ip address " + ip + " "
				+ netmask;

		System.out.println(s);

	}

	public static List<String> loopback1(int usrm) throws IOException {

		String s = "#Loopback1 Section Start\n";
		String lb1 = prop.getProperty("LOOPBACK1_START").replaceAll("\\s+", "");

		// Parse loopback IPs
		List<String> ips = listip(lb1, usrm);
		s = s + "interface Loopback1\n";
		// Primary IP
		s = s + "ip address " + ips.get(0) + " 255.255.255.255\n";
		// Secondary IPs
		int cards = Integer.parseInt(prop.getProperty("LINECARD"));
		for (int i = 1; i < usrm * cards; i++) {
			s = s + "ip address " + ips.get(i) + " 255.255.255.255 sec\n";
		}

		System.out.println(s);
		return ips;
	}

	public static List<String> loopback2() throws IOException {
		String lb2 = prop.getProperty("LOOPBACK2_START"); // VOD Section
		String s = "#Loopback2 Section Start\n";
		// buffer.append(s + "\n");
		// buffer.append(lb1 + "\n");

		// Parse loopback IPs
		List<String> ips2 = listip(lb2, 1);
		s = s + "interface Loopback2\n";
		// Primary IP
		s = s + "ip address " + ips2.get(0) + " 255.255.255.255\n";
		// Secondary IPs
		int cards = Integer.parseInt(prop.getProperty("LINECARD"));
		for (int i = 1; i < cards; i++) {
			s = s + "ip address " + ips2.get(i) + " 255.255.255.255 sec\n";
		}

		System.out.println(s);
		return ips2;

	}

	public static void qp(int usrm, List<String> ips) {
		String s = "#QP Section Start\n";

		for (int i = 1; i <= usrm; i++) {

			s = "cable qam-partition " + i + "\n" + "protocol ermi\n"
					+ "mgmt-ip " + ips.get(i - 1) + "\n" + "server "
					+ prop.getProperty("USRM" + i) + "\n"
					+ "errp streaming-zone "
					+ prop.getProperty("USRM" + i + "ZONE") + "\n"
					+ "errp component-name " + prop.getProperty("RFGWNAME")
					+ "-QP" + i + "\n" + "rtsp keepalive 300\n" + "active";
			System.out.println(s);
		}

	}

	public static void videoroute(int usrm, List<String> sdvips,
			List<String> vodips) {
		System.out.println("#Video Route Section Start");
		int cards = Integer.parseInt(prop.getProperty("LINECARD"));

		for (int i = 3; i < 3 + cards; i++) {

			System.out.println("cable route linecard " + i
					+ " load-balance-group 1");

			for (int j = 1; j <= usrm; j++) {
				int ipkey = (i - 3) * usrm - 1 + j;
				String s = "qam-partition " + j + " ip " + sdvips.get(ipkey)
						+ " bitrate 1800000";
				System.out.println(s);
			}

			// Generate route for VOD if exist
			if (vodips != null) {
				String s = "qam-partition default ip " + vodips.get(i - 3)
						+ " bitrate 1800000";
				System.out.println(s);
			}

		}
	}

	public static void qam() {
		System.out.println("#QAM Section Start");
		// buffer.append(s + "\n");
		int cards = Integer.parseInt(prop.getProperty("LINECARD"));
		int qamstart = Integer.parseInt(prop.getProperty("QAMSTART"));
		int qamend = Integer.parseInt(prop.getProperty("QAMEND"));
		String powerlevel = prop.getProperty("POWERLEVEL");
		String s;
		// Loop Through the CARD
		for (int i = 3; i < 3 + cards; i++) { // i is card number
			for (int x = 1; x <= 8; x++) { // x is port number
				s = "interface qam-red " + i + "/" + x + "\n"
						+ "Cable downstream max-carriers " + qamend;
				System.out.println(s);

				for (int j = qamstart; j <= qamend; j++) { // j is QAM interface
															// number
					String qamgroupkey = x + "." + j;
					s = "interface qam-red" + i + "/" + x + "." + j + "\n"
							+ "cable downstream lqam-group  "
							+ lqamgroup.get(qamgroupkey) + "\n"
							+ " cable mode video remote \n"
							+ "cable downstream rf-profile video-rf-profile\n"
							+ "cable downstream rf-power " + powerlevel + "\n"
							+ "cable downstream rf-shutdown\n"
							+ "cable qam-group qamgroup \n";

					System.out.println(s);
				}

			}
		}
		// System.out.println(s);
	}

	public static String getNextIPV4Address(String ip) {
		String[] nums = ip.split("\\.");
		int i = (Integer.parseInt(nums[0]) << 24
				| Integer.parseInt(nums[2]) << 8
				| Integer.parseInt(nums[1]) << 16 | Integer.parseInt(nums[3])) + 1;

		// If you wish to skip over .255 addresses.
		if ((byte) i == -1)
			i++;

		return String.format("%d.%d.%d.%d", i >>> 24 & 0xFF, i >> 16 & 0xFF,
				i >> 8 & 0xFF, i >> 0 & 0xFF);
	}

	public static List<String> listip(String s, int usrm) {

		List<String> ips = new ArrayList<String>();
		int cards = Integer.parseInt(prop.getProperty("LINECARD"));

		String[] parts = s.split("/");
		String ip = parts[0];
		ips.add(ip); // first IP

		for (int j = 1; j < cards * usrm; j++) {

			ip = getNextIPV4Address(ip);
			ips.add(ip);
		}

		return ips;
	}

	public static void lqamload() {

		// Generate first LQAM for first 40 QAMs
		for (int i = 1; i <= 8; i++) {
			
			for (int j = 1; j <= 40; j++) {
				String key = i + "." + j;
				Integer group = i + (j - 1) / 8 * 10;
				lqamgroup.put(key, group);
				// System.out.println("KEY:" + key + "Value:" + group);
			}

		}

		// Generate LQAM for QAM 41 - 48

		for (int i = 1; i <= 8; i++) {
			Integer group = ((i - 1) / 2) * 10 + 10 - i % 2;
			for (int j = 41; j <= 48; j++) {
				String key = i + "." + j;

				lqamgroup.put(key, group);
				// System.out.println("KEY:" + key + "Value:" + group);
			}

		}

	}
}
