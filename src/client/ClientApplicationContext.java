package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import message.Message;
import session.Session;

public class ClientApplicationContext {

	private static Map<String,Session> clientidsession=new HashMap<String,Session>();
	
	private ClientApplicationContext()
	{
		
	}
	
	public static Map<String, Session> getClientidsession() {
		return clientidsession;
	}

	public static void setClientidsession(Map<String, Session> clientidsession) {
		ClientApplicationContext.clientidsession = clientidsession;
	}
}
