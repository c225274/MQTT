package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.Message;
import message.PubrelMessage;

public class PubrelMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PubrelMessage pm=new PubrelMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PubrelMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PubrelMessageBodyDecode {

	public static void fixbody(PubrelMessage pm,ByteBuffer message)
	{
		try {
			fixvariableheader(pm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubrelMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		pm.setMessageidMSB(message.get());
		pm.setMessageidLSB(message.get());
	}
	private static void fixpayload(PubrelMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}
