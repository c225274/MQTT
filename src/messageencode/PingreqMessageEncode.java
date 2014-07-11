package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.PingreqMessage;
import message.Message;

public class PingreqMessageEncode extends MessageEncode{
	
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static PingreqMessage pm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		pm=(PingreqMessage)message;
		PingreqMessageBodyEncode.fixbody(pm);
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

class PingreqMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(PingreqMessage pm)
	{

	}
	private static void fixvariableheader(PingreqMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}
	private static void fixpayload(PingreqMessage pm) throws IOException {
		// TODO Auto-generated method stub
	}	
}
