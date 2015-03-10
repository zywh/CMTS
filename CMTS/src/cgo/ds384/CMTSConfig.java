package cgo.ds384;

/**
 * @author YPolyanskyy
 *
 */
public class CMTSConfig {
	
	private static String RFPOWER = "51.0",
						  BW_PERCENT = "96";
	
	public void produceCMTSConfig (String CMTSHostName, UbrCard Card){
		produceDownStreamConfig(CMTSHostName, Card);
		produceUpStreamConfig(CMTSHostName, Card);
	}
	
	private void produceDownStreamConfig(String CMTSHostName, UbrCard Card) {
		//String CMTSHostName = CMTSHostNameFull.substring(0,4);
		int countBlue = 0,
			countGreen = 0;
   		String tokens1[] = Card.getModIntf().trim().split("/");
		System.out.println("!========================+++++++++++++++++++++++++=========================\n" +
		           "!=========== Cisco uBR10012 Downstream Config for: " + Card.getUbrCardName() + "  ============\n" +
				   "!====================            CMTS: " + CMTSHostName + "            ===================\n" +
		           "!==========================================================================\n" +
		           "controller Modular-Cable " + Card.getModIntf().substring(0, (Card.getModIntf().length() - 1)));
		// Controller Configuration: Blue Channels
		while (countBlue < Card.getBlueSize()) {
			System.out.println(" rf-channel " + Card.getBlue(countBlue).getRfID()+ " frequency " + Card.getBlue(countBlue).getDsFreq() +
					" annex B modulation 256qam interleave 32\n" +
					" rf-channel " + Card.getBlue(countBlue).getRfID() + " depi-tunnel " + CMTSHostName + "_" + tokens1[0] + tokens1[1] +
					//tokens1[2].substring(0, (tokens1[2].length() - 1)) + " tsid " + Card.getBlue(countBlue).getTsID() + "\n" +
					Integer.parseInt(tokens1[2].substring(0, (tokens1[2].length() - 1)))*2 + " tsid " + Card.getBlue(countBlue).getTsID() + "\n" +
					" rf-channel " + Card.getBlue(countBlue).getRfID() + " rf-power " + RFPOWER + "\n" +
					" no rf-channel " + Card.getBlue(countBlue).getRfID() + " rf-shutdown" 
					);
			countBlue++;
		}
		// Controller Configuration: Green Channels
		while (countGreen < Card.getGreenSize()) {
			System.out.println(" rf-channel " + Card.getGreen(countGreen).getRfID()+ " frequency " + Card.getGreen(countGreen).getDsFreq() +
					" annex B modulation 256qam interleave 32\n" +
					" rf-channel " + Card.getGreen(countGreen).getRfID() + " depi-tunnel " + CMTSHostName + "_" + tokens1[0] + tokens1[1] +
					//tokens1[2].substring(0, (tokens1[2].length() - 1)) + " tsid " + Card.getGreen(countGreen).getTsID() + "\n" +
					Integer.parseInt(tokens1[2].substring(0, (tokens1[2].length() - 1)))*2 + " tsid " + Card.getGreen(countGreen).getTsID() + "\n" +
					" rf-channel " + Card.getGreen(countGreen).getRfID() + " rf-power " + RFPOWER + "\n" +
					" no rf-channel " + Card.getGreen(countGreen).getRfID() + " rf-shutdown" 
					);
			countGreen++;
		}
		// Modular Cable Interface Configuration: Blue Channels
		countBlue = 0;
		while (countBlue < Card.getBlueSize()) {
			System.out.println("!\n" +
		            "interface Modular-Cable" + Card.getModIntf() + Card.getBlue(countBlue).getRfID() + "\n" +
					" description Node " + Card.getNodeNum(0) + "\n" +
					" cable rf-bandwidth-percent " + BW_PERCENT
					);
			countBlue++;
		}
		// Wideband Cable Interface Configuration: Green Channels
		countGreen = 0;
		System.out.println("!\n" +
	            "interface Wideband-Cable" + Card.getWBInt() + "\n" +
				" description Node " + Card.getNodeNum(0) + "\n" +
	            " cable bundle 1");
		while (countGreen < Card.getGreenSize()) {
			System.out.println(" cable rf-channel " + Card.getGreen(countGreen).getRfID());
			countGreen++;
		}
		// Fiber Node Configuration:
		System.out.println("!\n" +
				"no cable fiber-node " + Card.getFiberNode() + "\n" +
				"cable fiber-node " + Card.getFiberNode() + "\n" +
				" downstream Modular-Cable " + Card.getModIntf().substring(0, (Card.getModIntf().length() - 1)) +
				" rf-channel " + Card.getBlue(0).getRfID() + "-" + Card.getGreen(2).getRfID() + "\n" +
				" upstream Cable " + Card.getUbrCardName().substring(5,8) +
				" connector " + Card.getUpConn(0) + "-" + Card.getUpConn(2) + "\n" +
				"!");
		// LBG Configuration:
		System.out.println(
				"cable load-balance group " + Card.getLBG() + " method utilization\n" +
				"cable load-balance group " + Card.getLBG() + " interval 60\n" +
				"cable load-balance group " + Card.getLBG() + " dcc-init-technique 4\n" +
				"cable load-balance group " + Card.getLBG() + " threshold load 20 enforce\n" +
				"cable load-balance group " + Card.getLBG() + " threshold load minimum 20\n" +
				"cable load-balance group " + Card.getLBG() + " threshold ugs 99\n" +
				"cable load-balance group " + Card.getLBG() + " policy us-groups-across-ds\n" +
				"!");
		// Configuration clean up and application of the config
		System.out.println(
				"!!! *** WARNING *** Please issue the following command on the CMTS " + CMTSHostName + ":\n" +
				"!!! \"sh ru | i interface Cable|downstream Modular-Cable\" \n" +
				"!!! and then manually delete the old RF Channel assosiations.\n" +
				"!!! An example of the required commands is provided below:\n" +
				"!!! interface " + Card.getUbrCardName() + "\n" +
				"!!! no downstream Modular-Cable " + Card.getModIntf().substring(0, (Card.getModIntf().length() - 1)) + 
				" rf-channel " + Card.getBlue(0).getRfID() + "-" + Card.getBlue(3).getRfID() + " upstream 0-3\n" +
				"!!!!\n" +
				"interface " + Card.getUbrCardName() + "\n" +
			    " description " + CMTSHostName + " Node " + Card.getNodeNum(0) + " Modular-Cable " +
						Card.getModIntf() + Card.getBlue(0).getRfID() + "-" + Card.getBlue(3).getRfID() + "\n" +
				" downstream Modular-Cable " + Card.getModIntf().substring(0, (Card.getModIntf().length() - 1)) + 
				//" rf-channel " + Card.getBlue(0).getRfID() + "-" + Card.getBlue(3).getRfID() + " upstream 0-3\n" +
				" rf-channel " + Card.getBlue(0).getRfID() + "-" + Card.getBlue(3).getRfID() + " upstream 0-" + Card.getLastUpstream() + "\n" +
				"!"
				);
	}

	private void produceUpStreamConfig(String CMTSHostName, UbrCard Card) {
		// Required upstream config could be here added later
	}
}
