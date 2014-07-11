package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import util.StringtoUTF;
import message.*;

public class SubscribeMessageEncode extends MessageEncode {

	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static SubscribeMessage sm;
	
	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		sm=(SubscribeMessage)message;
		SubscribeMessageBodyEncode.fixbody(sm);
		encodevariableheader=SubscribeMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		encodepayload=SubscribeMessageBodyEncode.encodepayload;
		encodepayloadlength=encodepayload.length;
		FixedHeaderEncode.fixheader(sm, encodevariableheaderlength+encodepayloadlength);
		encodefixheader=FixedHeaderEncode.encodefixhead;
		encodefixheaderlength=encodefixheader.length;
		try {
			bytearray.write(encodefixheader);
			bytearray.write(encodevariableheader);
			bytearray.write(encodepayload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteBuffer.wrap(bytearray.toByteArray());
	}
}

class SubscribeMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(SubscribeMessage sm){
		try {
			fixvariableheader(sm);
			fixpayload(sm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(SubscribeMessage sm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(sm.getMessageidMSB());
		bytearray.write(sm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(SubscribeMessage sm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		Map<String,String> payloadmap=sm.getPayloadmap();
		Set<String>topics=payloadmap.keySet();
		Iterator<String> topiciterator=topics.iterator();
		String topic;
		while(topiciterator.hasNext())
		{
			topic=topiciterator.next();
			byte[] topicbyte=StringtoUTF.convert(topic);
			byte lengthMSB=(byte) (topicbyte.length/256);
			byte lengthLSB=(byte) (topicbyte.length%256);
			bytearray.write(lengthMSB);
			bytearray.write(lengthLSB);
			bytearray.write(topicbyte);
			bytearray.write(Integer.parseInt(payloadmap.get(topic)));
		}
		encodepayload=bytearray.toByteArray();
	}	
}