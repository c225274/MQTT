package messagehandle;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


import message.*;
import session.Session;

public class UnsubscribeMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		UnsubscribeMessage unsubscribemessage=(UnsubscribeMessage)message;
		String topicname=unsubscribemessage.getTopicname();
		client.getTotalpayloadmap().remove(topicname);
		System.out.println("I HAVE RECEIVED UNSUBSCRIBE,ZHE TOPICNAME IS:"+topicname);
		UnsubackMessage unsubackmessage=new UnsubackMessage();
		unsubackmessage.setMessageidMSB(unsubscribemessage.getMessageidMSB());
		unsubackmessage.setMessageidLSB(unsubscribemessage.getMessageidLSB());
		client.getMessagesender().send(unsubackmessage);
		System.out.println("I HAVE SENDED UNSUBACK");
	}
}
