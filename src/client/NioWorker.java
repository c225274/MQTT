package client;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

import org.junit.Test;

import config.MessageType;
import message.ConnectMessage;
import message.Message;
import messagedecode.FixedHeaderDecode;
import messagehandle.MessageHandle;
import session.Session;
import util.MessageQueue;

public class NioWorker implements Runnable {

	private Selector selector;
	private SocketChannel socketchannel;
	
	public NioWorker(Selector selector, SocketChannel socketchannel) {
		this.selector = selector;
		this.socketchannel = socketchannel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if(!socketchannel.isOpen())
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
		Session client=(Session)key.attachment();
		client.setKey(key);
		try
		{
			if(key.isValid())
			{
				if (key.isReadable()) {
					client.readResponse();
				}
			}
			if(key.isValid())
			{
				if (key.isWritable()) {
					client.sendRequest();
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("CLOSED");
		}
	}

}
