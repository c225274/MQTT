package server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import config.MessageType;
import message.Message;
import messagedecode.FixedHeaderDecode;
import messagehandle.MessageHandle;
import session.Session;
import util.MessageQueue;

public class NioWorker implements Runnable {

	private Selector selector;
	private ServerSocketChannel serverChannel;

	public NioWorker(Selector selector, ServerSocketChannel socketChannel) {
		this.selector = selector;
		this.serverChannel = socketChannel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if(!serverChannel.isOpen())
			{
				break;
			}
			try {
				if(selector.select(30)>0)
				{	
					doSelector();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}

	public void doSelector() {
		// TODO Auto-generated method stub
		Iterator<SelectionKey> iter=selector.selectedKeys().iterator();
		while(iter.hasNext())
		{
			SelectionKey key=iter.next();
			if(!key.isValid())
			{
				continue;	
			}
			doKeys(key);
			iter.remove();
		}
	}

	public void doKeys(SelectionKey key) {
		// TODO Auto-generated method stub
		if(key.isAcceptable())
		{
			try {
				ServerSocketChannel serverchannel = (ServerSocketChannel) key.channel();
				SocketChannel socketchannel=serverchannel.accept();
				socketchannel.configureBlocking(false);
				Session clientsession=new Session(socketchannel,"server");
				socketchannel.register(selector,SelectionKey.OP_READ| SelectionKey.OP_WRITE).attach(clientsession);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{	
			Session perclient = (Session)key.attachment();
			perclient.setKey(key);
			try {
				if(key.isValid())
				{
					if (key.isReadable()) {
						perclient.readResponse();
					} 
				}
				if(key.isValid())
				{
					if (key.isWritable()) {
						perclient.sendRequest();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	@Test
//	public void test()
//	{
//		ByteBuffer permessage=ByteBuffer.allocate(1024);
//		ByteBuffer allmessage; 		
//		int allmessageposition;
//		int n;
//		int count=0;
//		boolean headerflag=true;
//		int remaininglength;
//		int remaininglengthindex;
//		int permessagelength;
//		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
//		byte[] bytea=new byte[]{1,2};
//		byte[] remianing=null;
//		try {
//			bytearray.write(bytea);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			if(remianing!=null)
//			{
//				bytearray.write(remianing);
//				remianing=null;
//			}
//			allmessage=ByteBuffer.wrap(bytearray.toByteArray());	
//			while(true)
//			{
//				if(allmessage.remaining()>=5)
//				{	
//					allmessageposition=allmessage.position();
//					FixedHeaderDecode.fixheader(allmessage);
//					remaininglength=7;
//					remaininglengthindex=1;
//					permessagelength=remaininglength+remaininglengthindex+1;
//					if(allmessage.remaining()>remaininglength)
//					{
//						allmessage.position(allmessageposition);
//						byte[] messagetodecode=new byte[permessagelength];
//						allmessage.get(messagetodecode);
//						for(int i=0;i<messagetodecode.length;i++)
//						{
//							System.out.println(messagetodecode[i]);
//						}
//						/////////decode the message//////////////
//					}
//					else if(allmessage.remaining()==remaininglength)
//					{
//						allmessage.position(allmessageposition);
//						byte[] messagetodecode=new byte[permessagelength];
//						allmessage.get(messagetodecode);
//						/////////decode the message//////////////
//						for(int i=0;i<messagetodecode.length;i++)
//						{
//							System.out.println(messagetodecode[i]);
//						}
//						break;
//					}
//					else
//					{
//						permessagelength=remaininglengthindex+1+allmessage.remaining();
//						allmessage.position(allmessageposition);
//						remianing=new byte[permessagelength];
//						allmessage.get(remianing);
//						break;
//					}
//				}
//				else
//				{
//					remianing=new byte[allmessage.remaining()];
//					allmessage.get(remianing);
//					break;
//				}
//			}
//			if(remianing!=null)
//			{
//				System.out.println("------------------------");
//				for(int i=0;i<remianing.length;i++)
//				{
//					System.out.println(remianing[i]);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}

}
