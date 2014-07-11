package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.Message;
import message.PubrelMessage;

public class PubrelMessageEncode extends MessageEncode{

	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static PubrelMessage pm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		pm=(PubrelMessage)message;
		PubrelMessageBodyEncode.fixbody(pm);
		encodevariableheader=PubrelMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		FixedHeaderEncode.fixheader(pm, encodevariableheaderlength);
		encodefixheader=FixedHeaderEncode.encodefixhead;
		encodefixheaderlength=encodefixheader.length;
		try {
			bytearray.write(encodefixheader);
			bytearray.write(encodevariableheader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteBuffer.wrap(bytearray.toByteArray());
	}
}
class PubrelMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(PubrelMessage pm)
	{
		try {
			fixvariableheader(pm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubrelMessage pm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(pm.getMessageidMSB());
		bytearray.write(pm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(PubrelMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}	
}

