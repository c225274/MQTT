package message;

import config.MessageType;

public class PingreqMessage extends FixedHeader implements MessageID{
	
	/////////novariable header//////////
	/////////novariable header//////////
	
	////////////nopayload///////////////
	////////////nopayload///////////////
	
	public PingreqMessage()
	{
		this.setMessagetype(MessageType.PINGREQ);
		this.setDup((byte)0);
		this.setQos((byte)0);
		this.setRetain((byte)0);
		this.setRemaininglength(0);
	}
}