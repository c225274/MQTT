package server;

import java.util.*;

import client.ClientApplicationContext;
import message.*;
import session.Session;

public class ServerApplicationContext {
	
	private static Map<String,Session> clientidsession=new HashMap<String,Session>();
	
	private ServerApplicationContext()
	{
		
	}

	public static Map<String, Session> getClientidsession() {
		return clientidsession;
	}

	public static void setClientidsession(Map<String, Session> clientidsession) {
		ServerApplicationContext.clientidsession = clientidsession;
	}

}
