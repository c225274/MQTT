package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;

public class PubrecMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PubrecMessage pm=new PubrecMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PubrecMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PubrecMessageBodyDecode {

	public static void fixbody(PubrecMessage pm,ByteBuffer message)
	{
		try {
			fixvariableheader(pm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubrecMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		pm.setMessageidMSB(message.get());
		pm.setMessageidLSB(message.get());
	}
	private static void fixpayload(PubrecMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}
