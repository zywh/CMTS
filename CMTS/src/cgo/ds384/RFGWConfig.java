package cgo.ds384;

/**
 * @author YPolyanskyy
 *
 */
public class RFGWConfig {

	private static String MAX_CARRIERS = "8",
						  START_FREQ="143000000";

	public void produceRFGWConfig (String CMTSHostName, UbrCard Card){
		//String CMTSHostName = CMTSHostNameFull.substring(0,4);
		int countBlue = 0,
		    countGreen = 0,
			SubIntfId = 1;
		
		System.out.println("!=====================++++++++++++======================\n" +
		           "!======== RFGW10 Configuration for: " + Card.getUbrCardName() + " =========\n" +
				   "!=============        CMTS: " + CMTSHostName + "          =============\n" +
		           "!=======================================================\n" +
		"interface Qam-red" + Card.getRFGWport() + "\n" +
		" cable downstream max-carriers " + MAX_CARRIERS + "\n" +
		" cable mode depi remote learn\n" +
		" cable downstream start-freq " + START_FREQ + "\n" +
		"!");
		
		String tokens[] = Card.getRFGWport().trim().split("/"); 
		String tokens1[] = Card.getModIntf().trim().split("/");
		// Blue 
		while (countBlue < Card.getBlueSize()) {
			//System.out.println(tokens1[3].substring(0, (tokens1[3].length() - 1)));
			
			//System.out.println("interface Qam-red" + Card.getRFGWport() + "." + (Card.getBlue(countBlue).getRfID()+ OFFSET) + "\n" +
			System.out.println("interface Qam-red" + Card.getRFGWport() + "." + SubIntfId++ + "\n" +
					//" cable mode depi remote learn\n" +
					" cable downstream lqam-group " + tokens[1] + "\n" +
					" cable downstream tsid " + Card.getBlue(countBlue).getTsID() + "\n" +
					" depi depi-tunnel " + CMTSHostName + "_" + tokens1[0] + tokens1[1] +
					//tokens1[2].substring(0, (tokens1[2].length() - 1)) + "\n" +
					Integer.parseInt(tokens1[2].substring(0, (tokens1[2].length() - 1)))*2 + "\n" +
					" !	");
			countBlue++;
		}
		// Green
			while ( countGreen < Card.getGreenSize()) {
			System.out.println("interface Qam-red" + Card.getRFGWport() + "." + SubIntfId++ + "\n" +
					//" cable mode depi remote learn\n" +
					" cable downstream lqam-group " + tokens[1] + "\n" +
					" cable downstream tsid " + Card.getGreen(countGreen).getTsID() + "\n" +
					" depi depi-tunnel " + CMTSHostName + "_" + tokens1[0] + tokens1[1] +
					//tokens1[2].substring(0, (tokens1[2].length() - 1)) + "\n" +
					Integer.parseInt(tokens1[2].substring(0, (tokens1[2].length() - 1)))*2 + "\n" +
					" !	");
			countGreen++;
		}
	}
}
