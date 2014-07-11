package messagehandle;

import java.io.ObjectInputStream.GetField;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import client.ClientApplicationContext;
import config.Qos;
import message.*;
import server.ServerApplicationContext;
import session.Session;

public class PubrelMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		PubrelMessage  pubrelmessage=(PubrelMessage)message;
		PubcompMessage pubcompmessage=new PubcompMessage();
		pubcompmessage.setMessageidMSB(pubrelmessage.getMessageidMSB());
		pubcompmessage.setMessageidLSB(pubrelmessage.getMessageidLSB());
		System.out.println("I HAVE RECEIVED PUBREL");
		client.getMessagesender().send(pubcompmessage);
		///this message is from client//////////////////////
		if(client.getClientorserver().equals("server"))
		{
			//System.out.println(pubrelmessage.getMessageid());
			PublishMessage publishmessage=(PublishMessage) client.getIdpublishqos2().get(pubrelmessage.getMessageid());
			String topicname=publishmessage.getTopicname();
			byte qos=publishmessage.getQos();
			String payload=publishmessage.getPayload();
			Map<String, Session> clientidsession=ServerApplicationContext.getClientidsession();
			Iterator<Session> perclientsessioniterator=clientidsession.values().iterator();
			Session perclientsession;
			while(perclientsessioniterator.hasNext())
			{
				perclientsession=perclientsessioniterator.next();
				if(perclientsession.getTotalpayloadmap().containsKey(topicname))////为了测试所有订阅了该topic的client都会被publish,但实际上发部者不应该被publish
				{
					System.out.println("I MUST REDIRECT THE PAYLOAD NOW");	
					if(qos>Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)))
					{
						publishmessage.setQos((byte) Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)));
					}
					perclientsession.getMessagesender().send(publishmessage);
					System.out.println("I HAVE SENDED "+topicname+" TO CLIENT:"+payload);
				}
			}
			client.getIdpublishqos2().remove(publishmessage.getMessageid());
		}
		///this message is from client//////////////////////
		///this message is from server//////////////////////
		else
		{
			PublishMessage publishmessage=(PublishMessage) client.getIdpublishqos2().get(pubrelmessage.getMessageid());
			String topicname=publishmessage.getTopicname();
			byte qos=publishmessage.getQos();
			String payload=publishmessage.getPayload();
			System.out.println("I HAVE SEEN "+topicname+":"+payload);
			client.getIdpublishqos2().remove(publishmessage.getMessageid());

		}
		///this message is from server//////////////////////
	}
}
