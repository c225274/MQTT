package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import message.*;
import util.UTFtoString;

public class PubackMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		PubackMessage pm=new PubackMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PubackMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PubackMessageBodyDecode {

	public static void fixbody(PubackMessage pm,ByteBuffer message)
	{
		try {
			fixvariableheader(pm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubackMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		pm.setMessageidMSB(message.get());
		pm.setMessageidLSB(message.get());
	}
	private static void fixpayload(PubackMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}