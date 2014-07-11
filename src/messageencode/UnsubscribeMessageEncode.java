package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import message.*;
import util.StringtoUTF;

public class UnsubscribeMessageEncode extends MessageEncode {
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static UnsubscribeMessage usm;
	
	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		usm=(UnsubscribeMessage)message;
		UnsubscribeMessageBodyEncode.fixbody(usm);
		encodevariableheader= UnsubscribeMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		encodepayload= UnsubscribeMessageBodyEncode.encodepayload;
		encodepayloadlength=encodepayload.length;
		FixedHeaderEncode.fixheader(usm, encodevariableheaderlength+encodepayloadlength);
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

class UnsubscribeMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody( UnsubscribeMessage usm){
		try {
			fixvariableheader(usm);
			fixpayload(usm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader( UnsubscribeMessage usm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(usm.getMessageidMSB());
		bytearray.write(usm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload( UnsubscribeMessage usm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		String topicname=usm.getTopicname();
		byte[] topicbyte=StringtoUTF.convert(topicname);
		byte lengthMSB=(byte) (topicbyte.length/256);
		byte lengthLSB=(byte) (topicbyte.length%256);
		bytearray.write(lengthMSB);
		bytearray.write(lengthLSB);
		bytearray.write(topicbyte);
		encodepayload=bytearray.toByteArray();
	}	
}
