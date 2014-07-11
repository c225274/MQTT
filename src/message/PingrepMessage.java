package message;

import config.MessageType;

public class PingrepMessage extends FixedHeader implements MessageID{
	
	/////////novariable header//////////
	/////////novariable header//////////
	
	////////////nopayload///////////////
	////////////nopayload///////////////
	
	public PingrepMessage()
	{
		this.setMessagetype(MessageType.PINGRESP);
		this.setDup((byte)0);
		this.setQos((byte)0);
		this.setRetain((byte)0);
		this.setRemaininglength(0);
	}
}
