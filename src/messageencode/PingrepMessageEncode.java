package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.Message;
import message.PingrepMessage;

public class PingrepMessageEncode extends MessageEncode{
	
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static PingrepMessage pm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		pm=(PingrepMessage)message;
		PingrepMessageBodyEncode.fixbody(pm);
		FixedHeaderEncode.fixheader(pm, 0);
		encodefixheader=FixedHeaderEncode.encodefixhead;
		encodefixheaderlength=encodefixheader.length;
		try {
			bytearray.write(encodefixheader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteBuffer.wrap(bytearray.toByteArray());
	}
}

class PingrepMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(PingrepMessage pm)
	{

	}
	private static void fixvariableheader(PingrepMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}
	private static void fixpayload(PingrepMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}	
}