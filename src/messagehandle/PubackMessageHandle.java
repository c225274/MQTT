package messagehandle;

import client.MessageIDGenerator;
import message.*;
import session.Session;

public class PubackMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		PubackMessage pm=(PubackMessage)message;
		System.out.println("publish successful");
		if(client.getMessageresend().getMessageidmap().containsKey(pm.getMessageid()))
		{
			client.getMessageresend().getMessageidmap().get(pm.getMessageid()).cancel(true);
			client.getMessageresend().getMessageidmap().remove(pm.getMessageid());
		}
		else
		{
			System.out.println("wrong");
		}
	}
}
