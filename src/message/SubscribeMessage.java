package message;

import java.util.*;

import org.junit.Test;

import config.MessageType;

public class SubscribeMessage extends FixedHeader implements MessageID{
	
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
	private Map<String,String> payloadmap=new HashMap<String,String>();

	public Map<String, String> getPayloadmap() {
		return payloadmap;
	}
	public void setPayloadmap(Map<String, String> payloadmap) {
		this.payloadmap = payloadmap;
	}
	////////////payload////////////////
	
	public SubscribeMessage()
	{
		this.setMessagetype(MessageType.SUBCRIBE);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
	}
	
	public SubscribeMessage(String messageid,String topicname,int topicqos)
	{
		this.setMessagetype(MessageType.SUBCRIBE);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
		this.setMessageid(messageid);
		this.getPayloadmap().put(topicname, new Integer(topicqos).toString());
	}
}
