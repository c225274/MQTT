package messagehandle;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import message.*;
import session.Session;
import util.StringtoUTF;
/////��һ��bug:��SubscribeMessage�в�Ӧ����map����Ӧ����˳��Ĵ��////
public class SubscribeMessageHandle extends MessageHandle{
	public static void messagehandle(Message message,Session client)
	{
		SubscribeMessage subscribemessage=(SubscribeMessage)message;
		SubackMessage subackmessage=new SubackMessage();
		Map<String, String>payloadmap=subscribemessage.getPayloadmap();
		Set<String>topics=payloadmap.keySet();
		Iterator<String>topiciterator=topics.iterator();
		String topic;
		Map<String, String>totalpayloadmap=client.getTotalpayloadmap();
		Vector<String> topicsqosvector=new Vector<String>();
		while(topiciterator.hasNext())
		{
			topic=topiciterator.next();
			totalpayloadmap.put(topic,payloadmap.get(topic));
			topicsqosvector.add(payloadmap.get(topic));
		}
		subackmessage.setMessageidMSB(subscribemessage.getMessageidMSB());
		subackmessage.setMessageidLSB(subscribemessage.getMessageidLSB());
		subackmessage.setTopicsqosvector(topicsqosvector);
		client.getMessagesender().send(subackmessage);
	}
}
