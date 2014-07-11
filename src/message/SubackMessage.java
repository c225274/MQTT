package message;

import java.util.*;

import config.MessageType;

public class SubackMessage extends FixedHeader implements MessageID{
	
	/////////variable header//////////
	private byte messageidMSB;
	private byte messageidLSB;
	private String messageid;
	
	public byte getMessageidMSB() {
		return messageidMSB;
	}
	public void setMessageidMSB(byte messageidMSB) {
		this.messageidMSB = messageidMSB;
	}
	public byte getMessageidLSB() {
		return messageidLSB;
	}
	public void setMessageidLSB(byte messageidLSB) {
		this.messageidLSB = messageidLSB;
	}
	public String getMessageid() {
		return new Integer(((((int)messageidLSB)<<24)>>>24)+((((int)messageidMSB)<<24)>>>24)*256).toString();
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
		messageidMSB=(byte)(Integer.parseInt(messageid)/256);
		messageidLSB=(byte)(Integer.parseInt(messageid)%256);
	}
	/////////variable header//////////
	
	////////////payload////////////////
	Vector<String> topicsqosvector=new Vector<String>();

	public Vector<String> getTopicsqosvector() {
		return topicsqosvector;
	}
	public void setTopicsqosvector(Vector<String> topicsqosvector) {
		this.topicsqosvector = topicsqosvector;
	}
	////////////payload////////////////
	
	public SubackMessage()
	{
		this.setMessagetype(MessageType.SUBACK);
		this.setDup((byte)0);
		this.setQos((byte)0);
		this.setRetain((byte)0);
	}
}
