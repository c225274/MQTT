package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import message.*;
import util.UTFtoString;

public class UnsubscribeMessageDecode extends MessageDecode {
	public static Message decode(ByteBuffer message)
	{
		UnsubscribeMessage usm=new UnsubscribeMessage();
		FixedHeaderDecode.fixheader(message);
		usm.setMessagetype(FixedHeaderDecode.getMessagetype());
		usm.setDup(FixedHeaderDecode.getDup());
		usm.setQos(FixedHeaderDecode.getQos());
		usm.setRetain(FixedHeaderDecode.getRetain());
		usm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		UnsubscribeMessageBodyDecode.fixbody(usm, message);
		return usm;
	}
}

class UnsubscribeMessageBodyDecode {

	public static void fixbody(UnsubscribeMessage usm,ByteBuffer message)
	{
		try {
			fixvariableheader(usm,message);
			fixpayload(usm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(UnsubscribeMessage usm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		usm.setMessageidMSB(message.get());
		usm.setMessageidLSB(message.get());
	}
	private static void fixpayload(UnsubscribeMessage usm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		String topicname;
		topicname=UTFtoString.convert(message);
		usm.setTopicname(topicname);
	}
}
