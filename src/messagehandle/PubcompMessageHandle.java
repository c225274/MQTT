package messagehandle;

import message.*;
import session.Session;

public class PubcompMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		PubcompMessage pubcompmessage=(PubcompMessage)message;
		if(client.getMessageresend().getMessageidmap().containsKey(pubcompmessage.getMessageid()))
		{
			client.getMessageresend().getMessageidmap().get(pubcompmessage.getMessageid()).cancel(true);
			client.getMessageresend().getMessageidmap().remove(pubcompmessage.getMessageid());
		}
		System.out.println("I HAVE RECEIVED PubcompMessage");
	}
}
