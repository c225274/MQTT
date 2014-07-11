package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;

public class UnsubackMessageEncode extends MessageEncode {
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static UnsubackMessage usm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		usm=(UnsubackMessage)message;
		UnsubackMessageBodyEncode.fixbody(usm);
		encodevariableheader=UnsubackMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		FixedHeaderEncode.fixheader(usm, encodevariableheaderlength);
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
class UnsubackMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(UnsubackMessage usm)
	{
		try {
			fixvariableheader(usm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(UnsubackMessage usm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(usm.getMessageidMSB());
		bytearray.write(usm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(UnsubackMessage usm) throws IOException {
		// TODO Auto-generated method stub
	}	
}
