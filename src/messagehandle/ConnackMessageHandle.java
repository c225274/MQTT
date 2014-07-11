package messagehandle;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import config.KeepAliveTime;
import client.ClientApplicationContext;
import client.MessageIDGenerator;
import session.Session;
import message.*;


public class ConnackMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		System.out.println("connect successful");
		System.out.println(((ConnackMessage)message).getReturncode());
		client.heartbeat(new PingreqMessage());
	}
}
 