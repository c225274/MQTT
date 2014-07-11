package messagehandle;


import java.io.IOException;

import message.*;
import server.ServerApplicationContext;
import session.Session;

public class DisconnectMessageHandle extends MessageHandle {
	public static void messagehandle(Message message,Session client)
	{
		System.out.println("I HAVE RECEIVED THE DISCONNECT MESSAGE,AND I AM AREADY CLOSE THE CONNECT");
		client.sessionDisconnectHandle();
	}
}
