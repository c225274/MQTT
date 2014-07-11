package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;

import message.Message;
import message.UnsubackMessage;

public class UnsubackMessageDecode extends MessageDecode {

	public static Message decode(ByteBuffer message)
	{
		UnsubackMessage usm=new UnsubackMessage();
		FixedHeaderDecode.fixheader(message);
		usm.setMessagetype(FixedHeaderDecode.getMessagetype());
		usm.setDup(FixedHeaderDecode.getDup());
		usm.setQos(FixedHeaderDecode.getQos());
		usm.setRetain(FixedHeaderDecode.getRetain());
		usm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		UnsubackMessageBodyDecode.fixbody(usm, message);
		return usm;
	}
}

class UnsubackMessageBodyDecode {

	public static void fixbody(UnsubackMessage usm,ByteBuffer message)
	{
		try {
			fixvariableheader(usm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(UnsubackMessage usm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		usm.setMessageidMSB(message.get());
		usm.setMessageidLSB(message.get());
	}
	private static void fixpayload(UnsubackMessage usm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
	}
	
}

