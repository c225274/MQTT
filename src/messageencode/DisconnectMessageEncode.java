package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.*;
import util.StringtoUTF;

public class DisconnectMessageEncode extends MessageEncode{
	
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static DisconnectMessage dm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		dm=(DisconnectMessage)message;
		DisconnectMessageBodyEncode.fixbody(dm);
		FixedHeaderEncode.fixheader(dm, 0);
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

class DisconnectMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(DisconnectMessage dm)
	{

	}
	private static void fixvariableheader(DisconnectMessage dm) throws IOException {
		// TODO Auto-generated method stub
	}
	private static void fixpayload(DisconnectMessage dm) throws IOException {
		// TODO Auto-generated method stub
	}	
}
