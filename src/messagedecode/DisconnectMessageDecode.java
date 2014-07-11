package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;

public class DisconnectMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		DisconnectMessage dm=new DisconnectMessage();
		FixedHeaderDecode.fixheader(message);
		dm.setMessagetype(FixedHeaderDecode.getMessagetype());
		dm.setDup(FixedHeaderDecode.getDup());
		dm.setQos(FixedHeaderDecode.getQos());
		dm.setRetain(FixedHeaderDecode.getRetain());
		dm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		DisconnectMessageBodyDecode.fixbody(dm, message);
		return dm;
	}
}

class DisconnectMessageBodyDecode {

	public static void fixbody(DisconnectMessage dm, ByteBuffer message) {
		// TODO Auto-generated method stub
		
	}
	private static void fixvariableheader(DisconnectMessage dm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub

	}
	private static void fixpayload(DisconnectMessage dm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}
