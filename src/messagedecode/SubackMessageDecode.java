package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import message.*;
import util.UTFtoString;

public class SubackMessageDecode extends MessageDecode{
	
	public static Message decode(ByteBuffer message)
	{
		SubackMessage sm=new SubackMessage();
		FixedHeaderDecode.fixheader(message);
		sm.setMessagetype(FixedHeaderDecode.getMessagetype());
		sm.setDup(FixedHeaderDecode.getDup());
		sm.setQos(FixedHeaderDecode.getQos());
		sm.setRetain(FixedHeaderDecode.getRetain());
		sm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		SubackMessageBodyDecode.fixbody(sm, message);
		return sm;
	}
}

class SubackMessageBodyDecode {

	public static void fixbody(SubackMessage sm,ByteBuffer message)
	{
		try {
			fixvariableheader(sm,message);
			fixpayload(sm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(SubackMessage sm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		sm.setMessageidMSB(message.get());
		sm.setMessageidLSB(message.get());
	}
	private static void fixpayload(SubackMessage sm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		Vector<String> topicsqosvector=sm.getTopicsqosvector();
		while(message.remaining()>0)
		{
			topicsqosvector.add(new Byte(message.get()).toString());
		}
	}
	
}
