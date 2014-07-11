package messagehandle;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import client.ClientApplicationContext;
import config.Qos;
import message.*;
import messageencode.PublishMessageEncode;
import server.ServerApplicationContext;
import session.Session;
import util.MessageQueue;

public class PublishMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		PublishMessage publishmessage=(PublishMessage)message;
		String topicname=publishmessage.getTopicname();
		byte qos=publishmessage.getQos();
		String payload=publishmessage.getPayload();
		///this message is from server//////////////////////
		if(client.getClientorserver().equals("client"))
		{	
			if(qos==Qos.ATMOSTONCEDELIVERY)
			{
//				if(client.getTotalpayloadmap().containsKey(topicname)&&(qos<=Integer.parseInt(client.getTotalpayloadmap().get(topicname))))
//				{
					System.out.println("I HAVE RECEIVED "+topicname+" FROM SERVER:"+payload);			
//				}
//				else
//				{
//					if((client.getTotalpayloadmap().containsKey(topicname)))
//					{
//						System.out.println("I HAVE RECEIVED "+topicname+" FROM SERVER,BUT THE QOS IS WRONG");
//					}			
//				}
					
			}
			else if(qos==Qos.ATLESTONCEDELIVERY)
			{
//				if(client.getTotalpayloadmap().containsKey(topicname)&&(qos<=Integer.parseInt(client.getTotalpayloadmap().get(topicname))))
//				{
					System.out.println("I HAVE RECEIVED "+topicname+" FROM SERVER:"+payload);	
					PubackMessage  pubackmessage=new PubackMessage();
					pubackmessage.setMessageidMSB(publishmessage.getMessageidMSB());
					pubackmessage.setMessageidLSB(publishmessage.getMessageidLSB());
					client.getMessagesender().send(pubackmessage);
//				}
//				else
//				{
//					if((client.getTotalpayloadmap().containsKey(topicname)))
//					{
//						System.out.println("I HAVE RECEIVED "+topicname+" FROM SERVER,BUT THE QOS IS WRONG");
//					}			
//				}
			}
			else
			{
				client.getIdpublishqos2().put(publishmessage.getMessageid(),publishmessage);
				PubrecMessage pubrecmessage=new PubrecMessage();
				pubrecmessage.setMessageidMSB(publishmessage.getMessageidMSB());
				pubrecmessage.setMessageidLSB(publishmessage.getMessageidLSB());
				client.getMessagesender().send(pubrecmessage);
				System.out.println("I HAVE SENDED PubrecMessage TO CLIENT");	
			}
			
		}
		///this message is from server//////////////////////
		///this message is from client//////////////////////
		else
		{
			Map<String, Session> clientidsession=ServerApplicationContext.getClientidsession();
			Iterator<Session> perclientsessioniterator=clientidsession.values().iterator();
			Session perclientsession;
			while(perclientsessioniterator.hasNext())
			{
				perclientsession=perclientsessioniterator.next();
				if(perclientsession.isIsalive())
				{
					if(perclientsession.getTotalpayloadmap().containsKey(topicname))////为了测试所有订阅了该topic的client都会被publish,但实际上发部者不应该被publish
					{	
						if(qos==Qos.ATMOSTONCEDELIVERY)
						{
							if(qos>Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)))
							{
								publishmessage.setQos((byte) Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)));
							}
							perclientsession.getMessagesender().send(publishmessage);	
						}
						else if(qos==Qos.ATLESTONCEDELIVERY)
						{
							if(qos>Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)))
							{
								publishmessage.setQos((byte) Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)));
							}
							perclientsession.getMessagesender().send(publishmessage);
							System.out.println("I HAVE RECEIVED "+topicname+" FROM server:"+payload+" AND I MUST REDIRECT THE PAYLOAD");	
						}
						else
						{
							
						}
					}
				}
				else
				{
					if(perclientsession.getTotalpayloadmap().containsKey(topicname))////为了测试所有订阅了该topic的client都会被publish,但实际上发部者不应该被publish
					{	
						if(qos==Qos.ATMOSTONCEDELIVERY)
						{
						}
						else if(qos==Qos.ATLESTONCEDELIVERY)
						{
							if(qos>Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)))
							{
								publishmessage.setQos((byte) Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)));
							}
							if(perclientsession.getDisconnectmessagequeue()==null)
							{
								perclientsession.setDisconnectmessagequeue(new MessageQueue());
							}
							perclientsession.getDisconnectmessagequeue().add(PublishMessageEncode.encode(publishmessage));
							System.out.println("I HAVE RECEIVED "+topicname+" FROM server:"+payload+"BUT THE SESSION IS DEAD SO I MUST STORE THE PAYLOAD");	
						}
						else
						{
							if(qos>Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)))
							{
								publishmessage.setQos((byte) Integer.parseInt(perclientsession.getTotalpayloadmap().get(topicname)));
							}
							if(perclientsession.getDisconnectmessagequeue()==null)
							{
								perclientsession.setDisconnectmessagequeue(new MessageQueue());
							}
							perclientsession.getDisconnectmessagequeue().add(PublishMessageEncode.encode(publishmessage));
							System.out.println("I HAVE RECEIVED "+topicname+" FROM server:"+payload+"BUT THE SESSION IS DEAD SO I MUST STORE THE PAYLOAD");	
						}
					}
				}

			}	
			if(qos==Qos.ATLESTONCEDELIVERY)
			{
				PubackMessage  pubackmessage=new PubackMessage();
				pubackmessage.setMessageidMSB(publishmessage.getMessageidMSB());
				pubackmessage.setMessageidLSB(publishmessage.getMessageidLSB());		
				client.getMessagesender().send(pubackmessage);
			}
			else if(qos==Qos.EXACTLYONCEDELIVERY)
			{
				client.getIdpublishqos2().put(publishmessage.getMessageid(),publishmessage);
				PubrecMessage pubrecmessage=new PubrecMessage();
				pubrecmessage.setMessageidMSB(publishmessage.getMessageidMSB());
				pubrecmessage.setMessageidLSB(publishmessage.getMessageidLSB());
				client.getMessagesender().send(pubrecmessage);
				System.out.println("I HAVE SENDED PubrecMessage TO CLIENT");	
			}
			else
			{
				
			}
		}
		///this message is from client//////////////////////
	}
}
