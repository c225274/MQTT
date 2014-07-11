package message;

import java.util.HashMap;
import java.util.Map;

import config.MessageType;

public class UnsubscribeMessage extends FixedHeader implements MessageID{
	
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
	private String topicname;
	
	public String getTopicname() {
		return topicname;
	}
	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}
	////////////payload////////////////

	public UnsubscribeMessage()
	{
		this.setMessagetype(MessageType.UNSUBCRIBE);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
	}
	
	public UnsubscribeMessage(String messageid,String topicname)
	{
		this.setMessagetype(MessageType.UNSUBCRIBE);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
		this.setMessageid(messageid);
		this.setTopicname(topicname);
	}
}
