package message;

import config.MessageType;

public class DisconnectMessage extends FixedHeader implements MessageID{
	
	/////////novariable header//////////
	/////////novariable header//////////
	
	////////////nopayload///////////////
	////////////nopayload///////////////
	
	public DisconnectMessage()
	{
		this.setMessagetype(MessageType.DISCONNECT);
		this.setDup((byte)0);
		this.setQos((byte)0);
		this.setRetain((byte)0);
		this.setRemaininglength(0);
	}
}
