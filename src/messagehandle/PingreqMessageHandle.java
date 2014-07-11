package messagehandle;

import message.*;
import messagedecode.PublishMessageDecode;
import session.Session;
import util.MessageQueue;

public class PingreqMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		MessageQueue disconnectmessagequeue;
		if((disconnectmessagequeue=client.getDisconnectmessagequeue())!=null)
		{
			for(int i=0;i<disconnectmessagequeue.size();i++)
			{
				client.getMessagesender().send((FixedHeader)PublishMessageDecode.decode(disconnectmessagequeue.get()));
			}
		}
		System.out.println("I HAVE RECEIVED THE HEART BEAT");
		PingrepMessage pm=new PingrepMessage();
		client.getMessagesender().send(pm);
	}
}
