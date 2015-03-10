package cgo.ds384;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author YPolyanskyy
 *
 */
public class DS384ConfigBuilder {
	
	private static String CurrentLine = null,
						  CMTS = null;
	private static final int FIRSTLINE = 1,
						  	 SECONDLINE = 2,
						     THIRDLINE = 3,
						     FOURTHLINE = 4,
						     FIFTHLINE = 5,
						     OFFSET = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
        	CMTS = args[1];
			FileReader input = new FileReader(args[0]);
			BufferedReader bufRead = new BufferedReader(input);
			
			CurrentLine=bufRead.readLine();
			
            while (CurrentLine != null){
        		// Create a new UbrCard object
        		UbrCard currentUbrCard = new UbrCard();
        		
        		// Process input file in bunch of 5 lines (per individual Cable interface) 
            	for (int lineNum = FIRSTLINE; lineNum <= FIFTHLINE; lineNum++) {
            		// Split each line into separate columns
               		String[] tokens = CurrentLine.split("	");
               		
               		// Parse line by line
               		if (lineNum == FIRSTLINE) {			
                   		String[] column0 = tokens[0].split(",");
                		String[] column0_f2 = column0[1].split("-");
               			String[] column1 = tokens[1].split("modc");
               			String[] column2 = tokens[2].split("widc");
 			
               			currentUbrCard.setUbrCardName(column0[0].trim());
               			currentUbrCard.setFiberNode(column0_f2[1].trim());
               			currentUbrCard.setModIntf(column1[1].trim());
               			currentUbrCard.setWBInt(column2[1].trim());
               			currentUbrCard.setNodeNum(tokens[3].trim(), lineNum - OFFSET);
               			currentUbrCard.setUpNum(tokens[4].trim().substring(1), lineNum - OFFSET);
               			if (!tokens[5].equalsIgnoreCase("")) {
               				String[] column5 = tokens[5].split("-");
                   			//currentUbrCard.setUpConn(column5[1].trim().substring(1), lineNum - OFFSET);
               				currentUbrCard.setUpConn(Integer.toString(Integer.parseInt(column5[1].trim())), lineNum - OFFSET);
               			}
               			currentUbrCard.setUsFreq(Float.parseFloat(tokens[6].trim()),lineNum - OFFSET);
               			
                 		} else if (lineNum == SECONDLINE) {
	               			String[] column0 = tokens[0].split("-");
	               			currentUbrCard.setRFGWport(column0[1].trim().substring(1));
	               			currentUbrCard.setNodeNum(tokens[3].trim(), lineNum - OFFSET);
	               			currentUbrCard.setUpNum(tokens[4].trim().substring(1), lineNum - OFFSET);
	               			if (!tokens[5].equalsIgnoreCase("")) {
	               				String[] column5 = tokens[5].split("-");
	                   			//currentUbrCard.setUpConn(column5[1].trim().substring(1), lineNum - OFFSET);
	                   			currentUbrCard.setUpConn(Integer.toString(Integer.parseInt(column5[1].trim())), lineNum - OFFSET);
                 			} else currentUbrCard.setUpConn(currentUbrCard.getUpConn(lineNum - OFFSET - OFFSET), lineNum - OFFSET);
	               			currentUbrCard.setUsFreq(Float.parseFloat(tokens[6].trim()),lineNum - OFFSET);
	               			// Blue
	               			if (!tokens[1].equalsIgnoreCase("")) {
	               				String[] column1_2 = tokens[1].split("/");
	               				currentUbrCard.addBlue(new RFParam
	               						(Integer.parseInt(column1_2[0]),Integer.parseInt(column1_2[1]),
	               								Integer.parseInt(column1_2[2])));
	               			}
	               			// Green
	               			if (!tokens[2].equalsIgnoreCase("")) {
	               				String[] column2_2 = tokens[2].split("/");
	               				currentUbrCard.addGreen(new RFParam
	               						(Integer.parseInt(column2_2[0]),Integer.parseInt(column2_2[1]),
	               								Integer.parseInt(column2_2[2])));
	               			}
	               			
                 		} else if (lineNum == THIRDLINE) {
                   			currentUbrCard.setNodeNum(tokens[3].trim(), lineNum - OFFSET);
	               			currentUbrCard.setUpNum(tokens[4].trim().substring(1), lineNum - OFFSET);
	               			if (!tokens[5].equalsIgnoreCase("")) {
	               				String[] column5 = tokens[5].split("-");
	                   			//currentUbrCard.setUpConn(column5[1].trim().substring(1), lineNum - OFFSET);
	                   			currentUbrCard.setUpConn(Integer.toString(Integer.parseInt(column5[1].trim())), lineNum - OFFSET);
	               			} else currentUbrCard.setUpConn(currentUbrCard.getUpConn(lineNum - OFFSET - OFFSET), lineNum - OFFSET);
	               			currentUbrCard.setUsFreq(Float.parseFloat(tokens[6].trim()),lineNum - OFFSET);
	               			// Blue
	               			if (!tokens[1].equalsIgnoreCase("")) {
	               				String[] column1_2 = tokens[1].split("/");
	               				currentUbrCard.addBlue(new RFParam
	               						(Integer.parseInt(column1_2[0]),Integer.parseInt(column1_2[1]),
	               								Integer.parseInt(column1_2[2])));
	               			}
	               			// Green
	               			if (!tokens[2].equalsIgnoreCase("")) {
	               				String[] column2_2 = tokens[2].split("/");
	               				currentUbrCard.addGreen(new RFParam
	               						(Integer.parseInt(column2_2[0]),Integer.parseInt(column2_2[1]),
	               								Integer.parseInt(column2_2[2])));
	               			}
	               			
                 		} else if (lineNum == FOURTHLINE) {
                   			currentUbrCard.setNodeNum(tokens[3].trim(), lineNum - OFFSET);
	               			currentUbrCard.setUpNum(tokens[4].trim().substring(1), lineNum - OFFSET);
	               			if (!tokens[5].equalsIgnoreCase("")) {
	               				String[] column5 = tokens[5].split("-");
	                   			//currentUbrCard.setUpConn(column5[1].trim().substring(1), lineNum - OFFSET);
	                   			currentUbrCard.setUpConn(Integer.toString(Integer.parseInt(column5[1].trim())), lineNum - OFFSET);
	               			} else currentUbrCard.setUpConn(currentUbrCard.getUpConn(lineNum - OFFSET - OFFSET), lineNum - OFFSET);
	               			currentUbrCard.setUsFreq(Float.parseFloat(tokens[6].trim()),lineNum - OFFSET);
	               			// Blue
	               			if (!tokens[1].equalsIgnoreCase("")) {
	               				String[] column1_2 = tokens[1].split("/");
	               				currentUbrCard.addBlue(new RFParam
	               						(Integer.parseInt(column1_2[0]),Integer.parseInt(column1_2[1]),
	               								Integer.parseInt(column1_2[2])));
	               			}
	               			// Green
	               			if (!tokens[2].equalsIgnoreCase("")) {
	               				String[] column2_2 = tokens[2].split("/");
	               				currentUbrCard.addGreen(new RFParam
	               						(Integer.parseInt(column2_2[0]),Integer.parseInt(column2_2[1]),
	               								Integer.parseInt(column2_2[2])));
	               			}
	                   		String[] column0 = tokens[0].split(",");
	                		String[] column0_f1 = column0[0].split("-");
	                		String[] column0_f2 = column0[1].split("-");
	                		currentUbrCard.setLBG(column0_f1[1]);
	               			currentUbrCard.setUBG(column0_f2[1]);
	               			
                 		} else if (lineNum == FIFTHLINE) {
                    			currentUbrCard.setNodeNum(tokens[3].trim(), lineNum - OFFSET);
	               			currentUbrCard.setUpNum(tokens[4].trim().substring(1), lineNum - OFFSET);
	               			if (!tokens[5].equalsIgnoreCase("")) {
	               				String[] column5 = tokens[5].split("-");
	                   			//currentUbrCard.setUpConn(column5[1].trim().substring(1), lineNum - OFFSET);
	                   			currentUbrCard.setUpConn(Integer.toString(Integer.parseInt(column5[1].trim())), lineNum - OFFSET);
	               			} else currentUbrCard.setUpConn(currentUbrCard.getUpConn(lineNum - OFFSET - OFFSET), lineNum - OFFSET);
	               			currentUbrCard.setUsFreq(Float.parseFloat(tokens[6].trim()),lineNum - OFFSET);
	               			// Blue channels
	               			if (!tokens[1].equalsIgnoreCase("")) {
	               				String[] column1_2 = tokens[1].split("/");
	               				currentUbrCard.addBlue(new RFParam
	               						(Integer.parseInt(column1_2[0]),Integer.parseInt(column1_2[1]),
	               								Integer.parseInt(column1_2[2])));
	               			}
	               			// Green channels
	               			if (!tokens[2].equalsIgnoreCase("")) {
	               				String[] column2_2 = tokens[2].split("/");
	               				currentUbrCard.addGreen(new RFParam
	               						(Integer.parseInt(column2_2[0]),Integer.parseInt(column2_2[1]),
	               								Integer.parseInt(column2_2[2])));
	               			}
                 		}        		
               		
             		CurrentLine = bufRead.readLine();
            	}        	
/*         		produceConfig (CMTS);
       			System.out.println("New Object: " + currentUbrCard.getUbrCardName() + 
       					" Fiber node: " + currentUbrCard.getFiberNode() +
       					" Modular Cable: " + currentUbrCard.getModIntf() +
       					" WideBand: " + currentUbrCard.getWBInt() +
       					"\nNode info: " + currentUbrCard.getNodeNum(0) + " " + currentUbrCard.getNodeNum(1) + " " + currentUbrCard.getNodeNum(2)
       					+ " " + currentUbrCard.getNodeNum(3)+ " " + currentUbrCard.getNodeNum(4) +
               			"\nUS#: " + currentUbrCard.getUpNum(0) + " " + currentUbrCard.getUpNum(1) + " " + currentUbrCard.getUpNum(2) +
               			" " + currentUbrCard.getUpNum(3) + " " + currentUbrCard.getUpNum(4) +
               			"\nUS Freq: " + currentUbrCard.getUsFreq(0) + " " + currentUbrCard.getUsFreq(1) + " " + currentUbrCard.getUsFreq(2) +
               			" " + currentUbrCard.getUsFreq(3) + " " + currentUbrCard.getUsFreq(4) +
               			"\nUS Connectors: " + currentUbrCard.getUpConn(0) + " " + currentUbrCard.getUpConn(1) + " " + currentUbrCard.getUpConn(2) +
               			" " + currentUbrCard.getUpConn(3) + " " + currentUbrCard.getUpConn(4) +
               			"\nBlueRF RF ID:" + currentUbrCard.getBlue(0).getRfID() + " DS Freq: " + currentUbrCard.getBlue(0).getDsFreq() +
               			" TSID: " + currentUbrCard.getBlue(0).getTsID() +
               			"\nBlueRF RF ID:" + currentUbrCard.getBlue(1).getRfID() + " DS Freq: " + currentUbrCard.getBlue(1).getDsFreq() +
               			" TSID: " + currentUbrCard.getBlue(1).getTsID() +
               			"\nBlueRF RF ID:" + currentUbrCard.getBlue(2).getRfID() + " DS Freq: " + currentUbrCard.getBlue(2).getDsFreq() +
               			" TSID: " + currentUbrCard.getBlue(2).getTsID() +
               			"\nBlueRF RF ID:" + currentUbrCard.getBlue(3).getRfID() + " DS Freq: " + currentUbrCard.getBlue(3).getDsFreq() +
               			" TSID: " + currentUbrCard.getBlue(3).getTsID() +
               			
               			"\nGreenRF RF ID:" + currentUbrCard.getGreen(0).getRfID() + " DS Freq: " + currentUbrCard.getGreen(0).getDsFreq() +
               			" TSID: " + currentUbrCard.getGreen(0).getTsID() +               			
              			"\nGreenRF RF ID:" + currentUbrCard.getGreen(1).getRfID() + " DS Freq: " + currentUbrCard.getGreen(1).getDsFreq() +
               			" TSID: " + currentUbrCard.getGreen(1).getTsID() + 
               			"\nGreenRF RF ID:" + currentUbrCard.getGreen(2).getRfID() + " DS Freq: " + currentUbrCard.getGreen(2).getDsFreq() +
               			" TSID: " + currentUbrCard.getGreen(2).getTsID() + 
               			"\nLBG: " + currentUbrCard.getLBG() + " UBG: " + currentUbrCard.getUBG() +
               			"\nRFGW: " + currentUbrCard.getRFGWport()
       					+ "\n");*/    
       			
            	if (args[2].trim().toUpperCase().equals("RFGW")) {
       			// RFGW10 Config
	       			RFGWConfig myRFGWConfig = new RFGWConfig(); 
	       			myRFGWConfig.produceRFGWConfig(CMTS, currentUbrCard);
            	}
            	else if (args[2].trim().toUpperCase().equals("CMTS")) {
            		// CMTS Config
            		CMTSConfig myCMTSConfig = new CMTSConfig();
           			myCMTSConfig.produceCMTSConfig(CMTS, currentUbrCard);
            	} else System.out.println("RFGW or CMTS parameter was not provided.");

            }
            bufRead.close();
			
        } catch (ArrayIndexOutOfBoundsException e){
            /* If no file pr other parameters were passed on the command line, this exception is
			generated. A message indicating how the class should be called is displayed */
        	e.printStackTrace();
			//System.out.println("Usage: DS384ConfigBuilder NodePlanFile CMTSHostName RFGW|CMTS.\n");			
        } catch (FileNotFoundException e) {
        	System.out.println("NodePlanFile file not found.");
        } catch (IOException e){
			// If another exception is generated, print a stack trace
            e.printStackTrace();
        }  
    }

/*	static void produceConfig (String CMTS) {
		System.out.println("==========================================\n" +
				"Processing section config for " + CMTS + " CMTS with the following parameters:");
	}*/
}
