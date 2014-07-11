package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;
import util.StringtoUTF;

public class PublishMessageEncode extends MessageEncode {
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static PublishMessage pm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		pm=(PublishMessage)message;
		PublishMessageBodyEncode.fixbody(pm);
		encodevariableheader=PublishMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		encodepayload=PublishMessageBodyEncode.encodepayload;
		encodepayloadlength=encodepayload.length;
		FixedHeaderEncode.fixheader(pm, encodevariableheaderlength+encodepayloadlength);
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
class PublishMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(PublishMessage pm)
	{
		try {
			fixvariableheader(pm);
			fixpayload(pm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PublishMessage pm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		byte[] topicbyte=StringtoUTF.convert(pm.getTopicname());
		byte lengthMSB=(byte) (topicbyte.length/256);
		byte lengthLSB=(byte) (topicbyte.length%256);
		bytearray.write(lengthMSB);
		bytearray.write(lengthLSB);
		bytearray.write(topicbyte);
		bytearray.write(pm.getMessageidMSB());
		bytearray.write(pm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(PublishMessage pm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		byte[] payloadbyte=pm.getPayload().getBytes();
		bytearray.write(payloadbyte);
		encodepayload=bytearray.toByteArray();
	}	
}