package messagehandle;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import session.Session;
import config.MessageType;
import message.*;
import messagedecode.FixedHeaderDecode;

public class MessageHandle {
	
	public static void messagehandle(Message message,Session client)
	{
		byte messagetype=FixedHeaderDecode.getMessagetype();
		Class<?> messagehandleclass=MessageType.getTypetomessagehandle().get(Byte.toString((byte)messagetype));
		try {
			messagehandleclass.getMethod("messagehandle",Message.class,Session.class).invoke(null,message,client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
