package messagedecode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import org.junit.Test;

import util.StringtoUTF;
import util.UTFtoString;
import message.*;

public class SubscribeMessageDecode extends MessageDecode{
	
	public static Message decode(ByteBuffer message)
	{
		SubscribeMessage sm=new SubscribeMessage();
		FixedHeaderDecode.fixheader(message);
		sm.setMessagetype(FixedHeaderDecode.getMessagetype());
		sm.setDup(FixedHeaderDecode.getDup());
		sm.setQos(FixedHeaderDecode.getQos());
		sm.setRetain(FixedHeaderDecode.getRetain());
		sm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		SubscribeMessageBodyDecode.fixbody(sm, message);
		return sm;
	}
}

class SubscribeMessageBodyDecode {

	public static void fixbody(SubscribeMessage sm,ByteBuffer message)
	{
		try {
			fixvariableheader(sm,message);
			fixpayload(sm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(SubscribeMessage sm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		sm.setMessageidMSB(message.get());
		sm.setMessageidLSB(message.get());
	}
	private static void fixpayload(SubscribeMessage sm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		Map<String,String>payloadmap=new HashMap<String,String>();
		String topic;
		while(message.remaining()>0)
		{
			payloadmap.put(UTFtoString.convert(message), new Byte(message.get()).toString());
		}
		sm.setPayloadmap(payloadmap);
	}
	
}