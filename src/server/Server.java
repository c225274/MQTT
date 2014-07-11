package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;


public class Server {
	
	private static Server server;
	private String port;
	private ServerSocketChannel serverchannel = null;
	private Selector selector = null;
	
	public static void main(String[] args)
	{
		server=new Server("1883");
	}
	
	public Server(String port)
	{
		this.port = port;
		socketinit();
	}

	public void socketinit() {
		// TODO Auto-generated method stub
		try {
			serverchannel= ServerSocketChannel.open();
			serverchannel.configureBlocking(false);
			serverchannel.socket().bind(new InetSocketAddress(Integer.parseInt(port)));
			selector=Selector.open();
			serverchannel.register(selector,SelectionKey.OP_ACCEPT);
			new Thread(new NioWorker(selector,serverchannel)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
