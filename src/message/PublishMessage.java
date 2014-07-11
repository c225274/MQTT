package message;

import config.MessageType;

public class PublishMessage extends FixedHeader implements MessageID {
	
	/////////variable header//////////
	private byte lengthMSB;//0-7 bit
	private byte lengthLSB;//0-7 bit
	private String topicname;
	private byte messageidMSB;//0-7bit
	private byte messageidLSB;//0-7bit
	private String messageid;
	
	public byte getLengthMSB() {
		return lengthMSB;
	}

	public void setLengthMSB(byte lengthMSB) {
		this.lengthMSB = lengthMSB;
	}

	public byte getLengthLSB() {
		return lengthLSB;
	}

	public void setLengthLSB(byte lengthLSB) {
		this.lengthLSB = lengthLSB;
	}

	public String getTopicname() {
		return topicname;
	}

	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}

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
	
	/////////payload//////////////////
	private String payload;
	
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	/////////payload//////////////////
	
	public PublishMessage() {
		this.setMessagetype(MessageType.PUBLISH);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
	}
	public PublishMessage(String topicname,String payload,String messageid) {
		this.setMessagetype(MessageType.PUBLISH);
		this.setDup((byte)0);
		this.setQos((byte)1);
		this.setRetain((byte)0);
		this.setTopicname(topicname);
		this.setPayload(payload);
		this.setMessageid(messageid);
	}	
}
