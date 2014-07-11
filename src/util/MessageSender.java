package util;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.*;

import client.MessageIDGenerator;
import config.MessageType;
import config.Qos;
import message.*;

public class MessageSender {
	
	public MessageQueue messagequeue;
	private Messageresend messageresend;
	
	public MessageSender(MessageQueue messagequeue,Messageresend messageresend) {
		// TODO Auto-generated constructor stub
		this.messagequeue=messagequeue;
		this.messageresend=messageresend;
	}

	public  void send(final FixedHeader message)
	{
		synchronized (messagequeue) {
			int qos=message.getQos();
			final int messagetype=message.getMessagetype();
			final String messageid;
			String messageidtemp;
			if(qos==Qos.ATMOSTONCEDELIVERY)
			{
				send0(message,messagetype);
			}
			else if(qos==Qos.ATLESTONCEDELIVERY)
			{
				messageidtemp=((MessageID)message).getMessageid();
				Map<String,ScheduledFuture<?>> messageidmap=messageresend.getMessageidmap();
				while(messageidmap.containsKey(messageidtemp))
				{
					short messageidbyte=(short) (Short.parseShort(messageidtemp)-10);
					if(messageidbyte<0)
					{
						messageidbyte=0x7fff;
					}
					messageidtemp=new Short(messageidbyte).toString();
				}
				messageid=messageidtemp;
				message.setMessageid(messageid);
				try{
					ScheduledFuture<?> messagefuture=messageresend.getPool().schedule(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							message.setDup((byte)1);
							send(message);
						}			
					}, messageresend.SCHEDULEDDELAYTIME,TimeUnit.MILLISECONDS);
					if(messageresend.getMessageidmap().keySet().contains(messageid))
					{
						messageresend.getMessageidmap().get(messageid).cancel(true);
						messageresend.getMessageidmap().remove(messageid);
					}
					messageresend.getMessageidmap().put(messageid, messagefuture);
					send0(message,messagetype);
				}
				catch(Exception e)
				{
					
				}
			}
			else
			{
				messageidtemp=((MessageID)message).getMessageid();
				Map<String,ScheduledFuture<?>> messageidmap=messageresend.getMessageidmap();
				while(messageidmap.containsKey(messageidtemp))
				{
					short messageidbyte=(short) (Short.parseShort(messageidtemp)-10);
					if(messageidbyte<0)
					{
						messageidbyte=0x7fff;
					}
					messageidtemp=new Short(messageidbyte).toString();
				}
				messageid=messageidtemp;
				message.setMessageid(messageid);
				try{
					ScheduledFuture<?> messagefuture=messageresend.getPool().schedule(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							message.setDup((byte)1);
							send(message);
						}
						
					}, messageresend.SCHEDULEDDELAYTIME,TimeUnit.MILLISECONDS);
					if(messageresend.getMessageidmap().keySet().contains(messageid))
					{
						messageresend.getMessageidmap().get(messageid).cancel(true);
						messageresend.getMessageidmap().remove(messageid);
					}
					messageresend.getMessageidmap().put(messageid, messagefuture);
					send0(message,messagetype);
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}

	private  void send0(FixedHeader message, int messagetype) {
		// TODO Auto-generated method stub
		ByteBuffer cmencode;
		Class<?> messageencodeclass=MessageType.getTypetomessageencode().get(Byte.toString((byte)messagetype));
		 try {
			 cmencode=(ByteBuffer) messageencodeclass.getMethod("encode",Message.class).invoke(null,message);
			 messagequeue.add(cmencode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
