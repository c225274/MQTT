package messagehandle;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

import client.MessageIDGenerator;
import message.*;
import session.Session;

public class SubackMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		SubackMessage sm=(SubackMessage)message;
		System.out.println("subscribe successful");///这里只是简单的处理，没判断topicnames是否相同
		if(client.getMessageresend().getMessageidmap().containsKey(sm.getMessageid()))
		{
			client.getMessageresend().getMessageidmap().get(sm.getMessageid()).cancel(true);
			client.getMessageresend().getMessageidmap().remove(sm.getMessageid());
		}
	}
}
