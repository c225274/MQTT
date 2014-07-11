package client;

public class MessageIDGenerator {

	private static int id=0;
	
	public static int getId()
	{
		id++;
		if(id==0x00010000)
		{
			id=0;
		}
		return id;
	}

	public static void setId(int id) {
		MessageIDGenerator.id = id;
	}
}
