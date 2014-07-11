package message;

import config.MessageType;

public class ConnackMessage extends FixedHeader{
	
	/////variable header/////
	private byte topicnamecompressionresponse;//0-7bit reserved
	private byte returncode;//0-7bit

	public byte getTopicnamecompressionresponse() {
		return topicnamecompressionresponse;
	}
	public void setTopicnamecompressionresponse(byte topicnamecompressionresponse) {
		this.topicnamecompressionresponse = topicnamecompressionresponse;
	}
	public byte getReturncode() {
		return returncode;
	}
	public void setReturncode(byte returncode){
		this.returncode = returncode;
	}
	/////variable header/////
	/////////no payload//////////
	
	public ConnackMessage() {
		this.setMessagetype(MessageType.CONNACK);
		this.setRemaininglength(2);
	}
}
