package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.PingreqMessage;
import message.Message;

public class PingreqMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PingreqMessage pm=new PingreqMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PingreqMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PingreqMessageBodyDecode {

	public static void fixbody(PingreqMessage pm, ByteBuffer message) {
		// TODO Auto-generated method stub
		
	}
	private static void fixvariableheader(PingreqMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub

	}
	private static void fixpayload(PingreqMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}

