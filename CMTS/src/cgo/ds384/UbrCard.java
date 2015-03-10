package cgo.ds384;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YPolyanskyy
 * 
 * This class keeps all detailed information per line card that was parsed from the source file  
 */
public class UbrCard {
	private final int MAX_NUM_US = 5; 							// Current configuration has 5 Upstreams only
	private String UbrCardName = null,							// CableX/X/X
				   FiberNode = null,							// Fiber Node number
				   ModIntf = null,								// Modular Cable Interface
				   WBIntf = null,								// WideBand Cable Interface
				   RFGWport = null,								// Corresponding RFGW10 port
				   LBG = null,									// Downstream Load-Balancing group
				   UBG = null;									// Upstream Load-Balancing group
	private String [] NodeNum = new String [MAX_NUM_US], 		// Array of records for Node number descriptions
					  UpNum = new String [MAX_NUM_US],			// Array of records for upstreams associated with the card
					  UpConn = new String [MAX_NUM_US];			// Array of records for upstream connectors
	private float [] usFreq = new float [MAX_NUM_US];			// Array of records for upstream frequencies 
	private List<RFParam> BlueRF = new ArrayList<RFParam>();	// List of RFParam objects for "Blue" channels
	private List<RFParam> GreenRF = new ArrayList<RFParam>();	// List of RFParam objects for "Green" channels
	
	/**
	 * Setters methods for all instance variables
	 * 
	 */
	public void setUbrCardName(String UbrName) {
		this.UbrCardName = UbrName;
	}
	public void setFiberNode(String FiberNode) {
		this.FiberNode = FiberNode;
	}
	public void setModIntf(String ModIntf) {
		this.ModIntf = ModIntf;
	}
	public void setWBInt(String WBIntf) {
		this.WBIntf = WBIntf;
	}
	public void setRFGWport(String RFGWport) {
		this.RFGWport = RFGWport;
	}
	public void setNodeNum(String NodeNum, int index) {
		this.NodeNum[index] = NodeNum;
	}
	public void setUpNum(String UpNum, int index) {
		this.UpNum[index] = UpNum;
	}
	public void setUpConn(String UpConn, int index) {
		this.UpConn[index] = UpConn;
	}
	public void setUsFreq(float usFreq, int index) {
		this.usFreq[index] = usFreq;
	}
	public void setLBG(String LBG) {
		this.LBG = LBG;
	}
	public void setUBG(String UBG) {
		this.UBG = UBG;
	}
	public void addBlue(RFParam RFparam) {
		BlueRF.add(RFparam);
	}	
	public void addGreen(RFParam RFparam) {
		GreenRF.add(RFparam);
	}
	
	/**
	 * Getter methods for all instance variables
	 * 
	 */
	public String getUbrCardName() {
		return this.UbrCardName;
	}
	public String getFiberNode() {
		return this.FiberNode;
	}
	public String getModIntf() {
		return this.ModIntf;
	}
	public String getWBInt() {
		return this.WBIntf;
	}
	public String getRFGWport() {
		return this.RFGWport;
	}
	public String getNodeNum(int index) {
		return this.NodeNum[index];
	}
	public String getUpNum(int index) {
		return this.UpNum[index];
	}
	public String getUpConn(int index) {
		return this.UpConn[index];
	}
	public float getUsFreq(int index) {
		return this.usFreq[index];
	}
	public String getLBG() {
		return this.LBG;
	}
	public String getUBG() {
		return this.UBG;
	}
	public RFParam getBlue(int index) {
		return BlueRF.get(index);
	}
	public int getBlueSize() {
		return BlueRF.size();
	} 
	public int getGreenSize() {
		return GreenRF.size();
	}
	public RFParam getGreen(int index) {
		return GreenRF.get(index);
	}
	/*
	 * Required for building Fiber-node config
	 */
	public String getLastUpstream() {
		String lastUpstream=null;
		for (int i=0; i<MAX_NUM_US; i++){
			if (!this.getUpNum(i).isEmpty())
				lastUpstream=this.getUpNum(i);
		}
		return lastUpstream;	
	}
}
