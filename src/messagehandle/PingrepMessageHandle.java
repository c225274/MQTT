package messagehandle;

import config.RETRY;
import message.*;
import session.Session;

public class PingrepMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		System.out.println("THE SERVER IS ALIVE!");
		RETRY.HASRETRYTIMES=0;
	}
}
