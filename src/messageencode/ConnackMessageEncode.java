package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import message.ConnackMessage;
import message.ConnectMessage;
import message.Message;

public class ConnackMessageEncode extends MessageEncode{
	
	private static ConnackMessage cm;
	
	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		cm=(ConnackMessage)message;
		FixedHeaderEncode.fixheader(cm,2);
		try {
			bytearray.write(FixedHeaderEncode.encodefixhead);
			bytearray.write(0x00);
			bytearray.write(cm.getReturncode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteBuffer.wrap(bytearray.toByteArray());
	}
}
