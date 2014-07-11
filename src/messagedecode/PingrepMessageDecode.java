package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;

public class PingrepMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PingrepMessage pm=new PingrepMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PingrepMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PingrepMessageBodyDecode {

	public static void fixbody(PingrepMessage pm, ByteBuffer message) {
		// TODO Auto-generated method stub
		
	}
	private static void fixvariableheader(PingrepMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub

	}
	private static void fixpayload(PingrepMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}
