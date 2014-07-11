package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;

public class PubcompMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PubcompMessage pm=new PubcompMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PubcompMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PubcompMessageBodyDecode {

	public static void fixbody(PubcompMessage pm,ByteBuffer message)
	{
		try {
			fixvariableheader(pm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubcompMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		pm.setMessageidMSB(message.get());
		pm.setMessageidLSB(message.get());
	}
	private static void fixpayload(PubcompMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}
