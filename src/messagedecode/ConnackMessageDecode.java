package messagedecode;

import java.nio.ByteBuffer;

import message.ConnackMessage;
import message.*;

public class ConnackMessageDecode extends MessageDecode{
	public static Message decode(ByteBuffer message)
	{
		ConnackMessage cm=new ConnackMessage();
		FixedHeaderDecode.fixheader(message);
		cm.setMessagetype(FixedHeaderDecode.getMessagetype());
		cm.setDup(FixedHeaderDecode.getDup());
		cm.setQos(FixedHeaderDecode.getQos());
		cm.setRetain(FixedHeaderDecode.getRetain());
		cm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		message.get();
		cm.setReturncode(message.get());
		return cm;		
	}
}
