package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.Message;
import message.PubackMessage;
import util.StringtoUTF;

public class PubackMessageEncode extends MessageEncode {

	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static PubackMessage pm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		pm=(PubackMessage)message;
		PubackMessageBodyEncode.fixbody(pm);
		encodevariableheader=PubackMessageBodyEncode.encodevariableheader;
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
class PubackMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(PubackMessage pm)
	{
		try {
			fixvariableheader(pm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(PubackMessage pm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		bytearray.write(pm.getMessageidMSB());
		bytearray.write(pm.getMessageidLSB());
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(PubackMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}	
}