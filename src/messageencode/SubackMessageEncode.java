package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import message.*;
import util.StringtoUTF;

public class SubackMessageEncode extends MessageEncode {

	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static SubackMessage sm;
	
	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		sm=(SubackMessage)message;
		SubackMessageBodyEncode.fixbody(sm);
		encodevariableheader=SubackMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		encodepayload=SubackMessageBodyEncode.encodepayload;
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

class SubackMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(SubackMessage sm){
		try {
			fixvariableheader(sm);
			fixpayload(sm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(SubackMessage sm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(sm.getMessageidMSB());
		bytearray.write(sm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(SubackMessage sm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		Vector<String> topicsqosvector=sm.getTopicsqosvector();
		for(int i=0;i<topicsqosvector.size();i++)
		{
			bytearray.write(Byte.parseByte(topicsqosvector.elementAt(i)));
		}
		encodepayload=bytearray.toByteArray();
	}	
}