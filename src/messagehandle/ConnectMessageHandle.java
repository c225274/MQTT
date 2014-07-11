package messagehandle;

import java.rmi.server.ServerCloneException;

import server.ServerApplicationContext;
import session.Session;
import util.MessageQueue;
import config.ConnackMessageReturnCode;
import message.ConnackMessage;
import message.ConnectMessage;
import message.FixedHeader;
import message.Message;
import messagedecode.PublishMessageDecode;
import messageencode.ConnackMessageEncode;
import messageencode.PublishMessageEncode;

public class ConnectMessageHandle extends MessageHandle{
	public static void messagehandle(Message message,Session client)
	{
		ConnectMessage connectmessage=(ConnectMessage)message;
		ConnackMessage connackmessage=new ConnackMessage();
		String clientid=connectmessage.getClientidentifier();

		if(clientid.length()<1||clientid.length()>23)
		{
			System.out.println("clientid.length()>23");
			connackmessage.setReturncode(ConnackMessageReturnCode.CONNECTUNACCEPTEDIDENTIFIER);
			client.getMessagesender().send(connackmessage);
			return;
		}
		String protocal=connectmessage.getProtocalname();
		if(!protocal.equals("MQIsdp"))
		{
			connackmessage.setReturncode(ConnackMessageReturnCode.CONNECTUNACCEPTEDPROTOCAL);
			client.getMessagesender().send(connackmessage);
			return;
		}
		connackmessage.setReturncode(ConnackMessageReturnCode.CONNECTACCEPTED);
		client.getMessagesender().send(connackmessage);	
		//放在反检测中
		if(connectmessage.getCleansession()==0)//持久订阅
		{
			if(ServerApplicationContext.getClientidsession().keySet().contains(connectmessage.getClientidentifier()))
			{
				Session tempclientsession=client;
				client.getKey().attach(ServerApplicationContext.getClientidsession().get(connectmessage.getClientidentifier()));
				client=ServerApplicationContext.getClientidsession().get(connectmessage.getClientidentifier());
				client.setCleansession(connectmessage.getCleansession());
				client.setSocketchannel(tempclientsession.getSocketchannel());
				client.setKey(tempclientsession.getKey());
				client.setIsalive(true);
				tempclientsession=null;	
			}
			else
			{
				client.setCleansession(connectmessage.getCleansession());
				client.setClientid(connectmessage.getClientidentifier());
				client.setIsalive(true);
				ServerApplicationContext.getClientidsession().put(connectmessage.getClientidentifier(),client);
			}
		}
		else//非持久订阅
		{
			if(ServerApplicationContext.getClientidsession().keySet().contains(connectmessage.getClientidentifier()))
			{
				ServerApplicationContext.getClientidsession().remove(connectmessage.getClientidentifier());
				client.setCleansession(connectmessage.getCleansession());
				client.setClientid(connectmessage.getClientidentifier());
				client.setIsalive(true);
				ServerApplicationContext.getClientidsession().put(connectmessage.getClientidentifier(),client);
			}
			else
			{
				client.setCleansession(connectmessage.getCleansession());
				client.setClientid(connectmessage.getClientidentifier());
				client.setIsalive(true);
				ServerApplicationContext.getClientidsession().put(connectmessage.getClientidentifier(),client);
			}
		}
	}
}
