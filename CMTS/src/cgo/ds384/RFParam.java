package cgo.ds384;

/**
 * @author YPolyanskyy
 *
 */
public class RFParam {
	private final int MHZ = 1000000;		// Multipliers to convert to proper frequency value
	private int rfID,						// Assigned RF channel ID
				dsFreq,						// Assigned downstream frequency
				tsID;						// Assigned TSID
	
	/**
	 * Constructor for the RFParam object
	 * @param rfID		Assigned RF channel ID
	 * @param dsFreq	Assigned downstream frequency
	 * @param tsID		Assigned TSID
	 */
	RFParam (int rfID, int dsFreq, int tsID ) {
		this.rfID = rfID;
		this.dsFreq = dsFreq * MHZ;
		this.tsID = tsID;
	}
	
	/**
	 * Getter methods for all instance variables
	 * 
	 */
	public int getRfID () {
		return this.rfID;
	}
	public int getDsFreq () {
		return this.dsFreq;
	}
	public int getTsID () {
		return this.tsID;
	}
}
