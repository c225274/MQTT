package messagehandle;

import message.*;
import session.Session;

public class PubrecMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		System.out.println("I HAVE RECEIVED PubrecMessage");
		PubrecMessage pubrecmessage=(PubrecMessage)message;
		if(client.getMessageresend().getMessageidmap().containsKey(pubrecmessage.getMessageid()))
		{
			client.getMessageresend().getMessageidmap().get(pubrecmessage.getMessageid()).cancel(true);
			client.getMessageresend().getMessageidmap().remove(pubrecmessage.getMessageid());
		}
		PubrelMessage pubrelmessage=new PubrelMessage();
		pubrelmessage.setMessageidMSB(pubrecmessage.getMessageidMSB());
		pubrelmessage.setMessageidLSB(pubrecmessage.getMessageidLSB());
		client.getMessagesender().send(pubrelmessage);
		System.out.println("I HAVE SENDED PubrelMessage");
	}
}
