package session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import client.ClientApplicationContext;
import server.ServerApplicationContext;
import util.MessageQueue;
import util.MessageSender;
import util.Messageresend;
import message.FixedHeader;
import message.Message;
import messagedecode.FixedHeaderDecode;
import messagehandle.MessageHandle;
import config.KeepAliveTime;
import config.MessageType;
import config.Qos;
import config.RETRY;

public class Session {
	
	private SocketChannel socketchannel;
	private MessageQueue messagequeue;
	private Messageresend messageresend;
	private MessageSender messagesender;
	private byte[] remianing=null;
	private Map<String, String> totalpayloadmap=new HashMap<String, String>();
	private String clientorserver;
	private byte cleansession;
	private String clientid;
	private Map<String,Message> idpublishqos2=new HashMap<String,Message>();
	private SelectionKey key;
	private MessageQueue disconnectmessagequeue=null;
	private boolean isalive;
	private ScheduledFuture<?> pingshedule;
	private ScheduledFuture<?> serverdetectclientshedule;
	
	public Session(SocketChannel socketchannel,String clientorserver) {
		this.socketchannel = socketchannel;
		this.messagequeue=new MessageQueue();
		this.messageresend=new Messageresend();
		messagesender=new MessageSender(messagequeue,messageresend);
		this.clientorserver=clientorserver;
	}
	
	public SocketChannel getSocketchannel() {
		return socketchannel;
	}

	public void setSocketchannel(SocketChannel socketchannel) {
		this.socketchannel = socketchannel;
	}


	public Messageresend getMessageresend() {
		return messageresend;
	}

	public void setMessageresend(Messageresend messageresend) {
		this.messageresend = messageresend;
	}
	
	public MessageQueue getMessagequeue() {
		return messagequeue;
	}

	public void setMessagequeue(MessageQueue messagequeue) {
		this.messagequeue = messagequeue;
	}

	public MessageSender getMessagesender() {
		return messagesender;
	}

	public void setMessagesender(MessageSender messagesender) {
		this.messagesender = messagesender;
	}

	public Map<String, String> getTotalpayloadmap() {
		return totalpayloadmap;
	}

	public void setTotalpayloadmap(Map<String, String> totalpayloadmap) {
		this.totalpayloadmap = totalpayloadmap;
	}
	
	public String getClientorserver() {
		return clientorserver;
	}

	public void setClientorserver(String clientorserver) {
		this.clientorserver = clientorserver;
	}
	public byte getCleansession() {
		return cleansession;
	}

	public void setCleansession(byte cleansession) {
		this.cleansession = cleansession;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	public Map<String, Message> getIdpublishqos2() {
		return idpublishqos2;
	}

	public void setIdpublishqos2(Map<String, Message> idpublishqos2) {
		this.idpublishqos2 = idpublishqos2;
	}
	
	public SelectionKey getKey() {
		return key;
	}

	public void setKey(SelectionKey key) {
		this.key = key;
	}
	
	public ScheduledFuture<?> getPingshedule() {
		return pingshedule;
	}

	public void setPingshedule(ScheduledFuture<?> pingshedule) {
		this.pingshedule = pingshedule;
	}
	

	public MessageQueue getDisconnectmessagequeue() {
		return disconnectmessagequeue;
	}

	public void setDisconnectmessagequeue(MessageQueue disconnectmessagequeue) {
		this.disconnectmessagequeue = disconnectmessagequeue;
	}
	

	public boolean isIsalive() {
		return isalive;
	}

	public void setIsalive(boolean isalive) {
		this.isalive = isalive;
	}
	
	
	public ScheduledFuture<?> getServerdetectclientshedule() {
		return serverdetectclientshedule;
	}

	public void setServerdetectclientshedule(
			ScheduledFuture<?> serverdetectclientshedule) {
		this.serverdetectclientshedule = serverdetectclientshedule;
	}
	
	public void heartbeat(final Message message)
	{
		if(RETRY.HASRETRYTIMES++>=RETRY.RETRYTIMESTHREOD)
		{
			System.out.println("THE SERVER IS DUMP");
			try {
				///换成客户端的closehandle
				socketchannel.close();
				socketchannel.socket().close();
				this.getMessageresend().getPool().shutdownNow();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if(pingshedule!=null)
		{
			pingshedule.cancel(true);
		}
		pingshedule=this.getMessageresend().getPool().schedule(new Runnable(){	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				((FixedHeader)message).setDup((byte) 1);
				messagesender.send((FixedHeader)message);
				heartbeat(message);	
			}
			
		},KeepAliveTime.getKeepAliveTime(),TimeUnit.MILLISECONDS);
	}
	
	public void serverdetectclient()
	{
		if(serverdetectclientshedule!=null)
		{
			serverdetectclientshedule.cancel(true);
		}
		try{		
			serverdetectclientshedule=this.getMessageresend().getPool().schedule(new Runnable(){	
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Session.this.sessionDisconnectHandle();
				}
				
			},KeepAliveTime.getKeepAliveTime()*3/2,TimeUnit.MILLISECONDS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sessionDisconnectHandle()
	{
		if(this.getCleansession()==0)//持久订阅
		{
			if(this.getMessagequeue().size()!=0)
			{
				MessageQueue disconnectmessagequeue=this.getDisconnectmessagequeue();
				if(disconnectmessagequeue==null)
				{
					disconnectmessagequeue=new MessageQueue();
				}
				ByteBuffer permessage;
				byte messagetype;
				byte qos;
				for(int i=0;i<this.getMessagequeue().size();i++)
				{
					permessage=this.getMessagequeue().get();
					FixedHeaderDecode.fixheader(permessage);
					messagetype=FixedHeaderDecode.getMessagetype();
					qos=FixedHeaderDecode.getQos();
					if(messagetype==MessageType.PUBLISH)
					{
						if(qos==Qos.ATLESTONCEDELIVERY||qos==Qos.EXACTLYONCEDELIVERY)
						{
							disconnectmessagequeue.add(permessage);
						}
					}
				}
			}
			this.setIsalive(false);
		}
		else//非持久订阅
		{
			ServerApplicationContext.getClientidsession().remove(this.getClientid());
		}
		try {
			this.getSocketchannel().close();
			this.getSocketchannel().socket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.getMessageresend().getPool().shutdownNow();
		this.getIdpublishqos2().clear();
		this.setKey(null);
		this.setPingshedule(null);
		this.setSocketchannel(null);
		this.getMessagequeue().clear();
		this.getMessageresend().getMessageidmap().clear();
	}
	
	public void sendRequest() {
		// TODO Auto-generated method stub
		synchronized (messagequeue) {
			ByteBuffer message=messagequeue.get();
			if(message!=null)
			{
				try {
					socketchannel.write(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}
	}

	public void readResponse() {
		// TODO Auto-generated method stub
		synchronized (messagequeue) {
			ByteBuffer permessage=ByteBuffer.allocate(1024);
			ByteBuffer allmessage; 		
			int allmessageposition;
			int n;
			int count=0;
			boolean headerflag=true;
			int remaininglength;
			int remaininglengthindex;
			int permessagelength;
			byte messagetype;
			ByteArrayOutputStream bytearray=new ByteArrayOutputStream();		
			try {
				if(this.getClientorserver().equals("server"))
				{
					this.serverdetectclient();
				}
				if(remianing!=null)
				{
					bytearray.write(remianing);
					remianing=null;
				}
				try{
					while((n=socketchannel.read(permessage))>0)
					{
						permessage.flip();
						bytearray.write(permessage.array());
					}
				}
				catch(Exception e)
				{
					socketchannel.close();
					socketchannel.socket().close();
					this.getMessageresend().getPool().shutdownNow();
					System.out.println("I HAVE CLOSED THE CONNECT");
					return;
				}	
				allmessage=ByteBuffer.wrap(bytearray.toByteArray());
				while(true)
				{
					if(allmessage.remaining()>=2)//有个bug,这里数据如果被断开传送的话，恰好reamininglength>2&&<=4的时候，会发生错误。
					{	
						System.out.println(allmessage.remaining());
						allmessageposition=allmessage.position();
						FixedHeaderDecode.fixheader(allmessage);
						messagetype=FixedHeaderDecode.getMessagetype();
						if(messagetype==0)
						{
							break;
						}
						System.out.println("messagetype:"+messagetype);
						remaininglength=FixedHeaderDecode.getRemaininglength();
						System.out.println("remaininglength:"+remaininglength);
						remaininglengthindex=FixedHeaderDecode.getRemaininglengthindex();
						System.out.println("remaininglengthindex:"+remaininglengthindex);
						permessagelength=remaininglength+remaininglengthindex+1;
						System.out.println("permessagelength:"+permessagelength);
						if(allmessage.remaining()>remaininglength)
						{
							allmessage.position(allmessageposition);
							byte[] messagetodecode=new byte[permessagelength];
							allmessage.get(messagetodecode);
							/////////decode and handle the message//////////////
							Class<?> messagedecodeclass=MessageType.getTypetomessagedecode().get(Byte.toString((byte)messagetype));
							try {
								MessageHandle.messagehandle((Message)messagedecodeclass.getMethod("decode",ByteBuffer.class).invoke(null,ByteBuffer.wrap(messagetodecode)),this);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							/////////////////////////////////////////
						}
						else if(allmessage.remaining()==remaininglength)
						{
							allmessage.position(allmessageposition);
							byte[] messagetodecode=new byte[permessagelength];
							allmessage.get(messagetodecode);
							/////////decode and handle the message//////////////
							Class<?> messagedecodeclass=MessageType.getTypetomessagedecode().get(Byte.toString((byte)messagetype));
							try {
								MessageHandle.messagehandle((Message)messagedecodeclass.getMethod("decode").invoke(null,ByteBuffer.wrap(messagetodecode)),this);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							/////////////////////////////////////////
							break;
						}
						else
						{
							permessagelength=remaininglengthindex+1+allmessage.remaining();
							allmessage.position(allmessageposition);
							remianing=new byte[permessagelength];
							allmessage.get(remianing);
							break;
						}
					}
					else
					{
						permessagelength=allmessage.remaining();
						remianing=new byte[permessagelength];
						allmessage.get(remianing);
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
