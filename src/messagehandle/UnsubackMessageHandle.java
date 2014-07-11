package messagehandle;

import message.*;
import session.Session;

public class UnsubackMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		System.out.println("I HAVE RECEIVED UnsubackMessage");
		UnsubackMessage UnsubackMessage=(UnsubackMessage)message;
		if(client.getMessageresend().getMessageidmap().containsKey(UnsubackMessage.getMessageid()))
		{
			client.getMessageresend().getMessageidmap().get(UnsubackMessage.getMessageid()).cancel(true);
			client.getMessageresend().getMessageidmap().remove(UnsubackMessage.getMessageid());
		}
		System.out.println("Unsuback successful");
	}
}
