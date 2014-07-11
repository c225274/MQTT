package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.LinkedList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import session.Session;
import util.MessageQueue;
import util.MessageSender;
import config.KeepAliveTime;
import config.MessageType;
import message.*;
import messageencode.ConnectMessageEncode;
import messageencode.FixedHeaderEncode;

public class Client {
	
	private String ip;
	private String port;
	private String clientid;
	private String username;
	private String password;	
	private ConnectMessage cm;
	private static Client client;
	private Selector selector;
	private InetSocketAddress socketAddress;
	private SocketChannel socketChannel;
	private Session clientsession;
	
	public static void main(String[] args)
	{
		//client1
		client=new Client("localhost","1883","client1","username","password");
		client.run();			
	}
	
	public Client(String ip, String port, String clientid, String username,String password) 
	{
		this.ip = ip;
		this.port = port;
		this.clientid = clientid;
		this.username = username;
		this.password = password;
	}
	
	public void run()
	{
		connect();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//subscribe("abc",1);
		//subscribe("abcd",1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publish("abcd","helloworldaa",2);
		publish("abc","yangchun",2);
		publish("abc","zhang",2);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconect();
	}
	
	public void connect()
	{
		connectmessageinit();
		socketConnet();
		clientsession.getMessagesender().send(cm);
		new Thread(new NioWorker(selector, socketChannel)).start();
	}
	
	public void subscribe(String topicname, int topicqos) {
		// TODO Auto-generated method stub
		SubscribeMessage sm=new SubscribeMessage(new Integer(MessageIDGenerator.getId()).toString(),topicname,topicqos);
		clientsession.getTotalpayloadmap().put(topicname, new Integer(topicqos).toString());
		clientsession.getMessagesender().send(sm);	
	}
	
	public void unsubscribe(String topicname) {
		// TODO Auto-generated method stub
		UnsubscribeMessage usm=new UnsubscribeMessage(new Integer(MessageIDGenerator.getId()).toString(),topicname);
		clientsession.getTotalpayloadmap().remove(topicname);
		clientsession.getMessagesender().send(usm);
	}
	
	public void publish(String topic, String payload,int qos) {
		// TODO Auto-generated method stub
		PublishMessage pm=new PublishMessage(topic,payload,new Integer(MessageIDGenerator.getId()).toString());
		pm.setQos((byte) qos);
		clientsession.getMessagesender().send(pm);
	}
	
	public void disconect()
	{
		DisconnectMessage dm=new DisconnectMessage();
		clientsession.getMessagesender().send(dm);
		System.out.println("disconnect!!!");
	}
	
	public void socketConnet() {
		try {
			InetSocketAddress socketAddress =new InetSocketAddress(ip,Integer.parseInt(port));
			socketChannel = SocketChannel.open();
			socketChannel.socket().setReuseAddress(true);
			socketChannel.connect(socketAddress);	
			selector = Selector.open();
			socketChannel.configureBlocking(false);
			clientsession=new Session(socketChannel,"client");
			clientsession.setClientid(cm.getClientidentifier());
			clientsession.setCleansession(cm.getCleansession());
			clientsession.setIsalive(true);
			ClientApplicationContext.getClientidsession().put(cm.getClientidentifier(), clientsession);
			socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE).attach(clientsession);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void connectmessageinit()
	{
		cm=new ConnectMessage();
		if(username!=null)
		{
			cm.setUsername(username);
			cm.setUsernameflag((byte)1);
		}
		if(password!=null)
		{
			cm.setPassword(password);
			cm.setPasswordflag((byte)1);
		}
		cm.setClientidentifier(clientid);
	}
}
