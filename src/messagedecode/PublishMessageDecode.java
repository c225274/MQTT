package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import message.*;
import util.UTFtoString;

public class PublishMessageDecode extends MessageDecode {
	public static Message decode(ByteBuffer message)
	{
		PublishMessage pm=new PublishMessage();
		FixedHeaderDecode.fixheader(message);
		pm.setMessagetype(FixedHeaderDecode.getMessagetype());
		pm.setDup(FixedHeaderDecode.getDup());
		pm.setQos(FixedHeaderDecode.getQos());
		pm.setRetain(FixedHeaderDecode.getRetain());
		pm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		PublishMessageBodyDecode.fixbody(pm, message);
		return pm;
	}
}

class PublishMessageBodyDecode {

	public static void fixbody(PublishMessage pm,ByteBuffer message)
	{
		try {
			fixvariableheader(pm,message);
			fixpayload(pm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PublishMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		pm.setTopicname(UTFtoString.convert(message));
		pm.setMessageidMSB(message.get());
		pm.setMessageidLSB(message.get());
	}
	private static void fixpayload(PublishMessage pm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		byte[] payloadarray=new byte[message.remaining()];
		message.get(payloadarray,0,message.remaining());
		pm.setPayload(new String(payloadarray));
	}
	
}