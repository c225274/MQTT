package messagedecode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import util.UTFtoString;
import message.ConnectMessage;
import message.Message;
import message.SubscribeMessage;

public class ConnectMessageDecode extends MessageDecode {
	public static Message decode(ByteBuffer message)
	{	
		ConnectMessage cm=new ConnectMessage();
		FixedHeaderDecode.fixheader(message);
		cm.setMessagetype(FixedHeaderDecode.getMessagetype());
		cm.setDup(FixedHeaderDecode.getDup());
		cm.setQos(FixedHeaderDecode.getQos());
		cm.setRetain(FixedHeaderDecode.getRetain());
		cm.setRemaininglength(FixedHeaderDecode.getRemaininglength());
		ConnectMessageBodyDecode.fixbody(cm, message);
		return cm;		
	}
}

class ConnectMessageBodyDecode {

	public static void fixbody(ConnectMessage cm,ByteBuffer message)
	{
		try {
			fixvariableheader(cm,message);
			fixpayload(cm,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(ConnectMessage cm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		String protocal;
		byte connectflag;
		protocal=UTFtoString.convert(message);
		cm.setProtocalname(protocal);
		cm.setVersionnum(message.get());
		connectflag=message.get();
		cm.setUsernameflag((byte)((connectflag&0x80)>>>7));
		cm.setPasswordflag((byte)((connectflag&0x40)>>>6));
		cm.setWillretain((byte)((connectflag&0x20)>>>5));
		cm.setWillqos((byte)((connectflag&0x18)>>>3));
		cm.setWillflag((byte)((connectflag&0x04)>>>2));
		cm.setCleansession((byte)((connectflag&0x02)>>>1));
		cm.setKeepaliveMSB(message.get());
		cm.setKeepaliveLSB(message.get());
	}
	private static void fixpayload(ConnectMessage cm,ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		String clientid;
		clientid=UTFtoString.convert(message);
		cm.setClientidentifier(clientid);
		if(cm.getWillflag()==(byte)1)
		{
			String willtopic=UTFtoString.convert(message);
			cm.setWilltopic(willtopic);
			String willmessage=UTFtoString.convert(message);
			cm.setWillmessage(willmessage);
		}
		if(cm.getUsernameflag()==(byte)1)
		{
			String username=UTFtoString.convert(message);
			cm.setWilltopic(username);
		}
		if(cm.getPasswordflag()==(byte)1)
		{
			String password=UTFtoString.convert(message);
			cm.setWilltopic(password);
		}
	}
}