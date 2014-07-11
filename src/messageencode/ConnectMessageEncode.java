package messageencode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import util.StringtoUTF;
import message.ConnectMessage;
import message.FixedHeader;
import message.Message;

public class ConnectMessageEncode extends MessageEncode{
	
	private static byte[] encodefixheader;
	private static int encodefixheaderlength;
	private static byte[] encodepayload;
	private static int encodepayloadlength;	
	private static byte[] encodevariableheader;
	private static int encodevariableheaderlength;	
	private static ConnectMessage cm;

	public static ByteBuffer encode(Message message)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		cm=(ConnectMessage)message;
		ConnectMessageBodyEncode.fixbody(cm);
		encodevariableheader=ConnectMessageBodyEncode.encodevariableheader;
		encodevariableheaderlength=encodevariableheader.length;
		encodepayload=ConnectMessageBodyEncode.encodepayload;
		encodepayloadlength=encodepayload.length;
		FixedHeaderEncode.fixheader(cm, encodevariableheaderlength+encodepayloadlength);
		encodefixheader=FixedHeaderEncode.encodefixhead;
		encodefixheaderlength=encodefixheader.length;
		try {
			bytearray.write(encodefixheader);
			bytearray.write(encodevariableheader);
			bytearray.write(encodepayload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteBuffer.wrap(bytearray.toByteArray());
	}
}

class ConnectMessageBodyEncode {
	public static byte[]encodevariableheader;
	public static byte[]encodepayload;
	public static void fixbody(ConnectMessage cm)
	{
		try {
			fixvariableheader(cm);
			fixpayload(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fixvariableheader(ConnectMessage cm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		byte[] protocal=StringtoUTF.convert(cm.getProtocalname());
		byte lengthMSB=(byte) (protocal.length/256);
		byte lengthLSB=(byte) (protocal.length%256);
		bytearray.write(lengthMSB);
		bytearray.write(lengthLSB);
		bytearray.write(protocal);
		byte protocalnum=cm.getVersionnum();
		bytearray.write(protocalnum);
		byte connectflag=(byte) (((cm.getCleansession()&0x01)<<1)|((cm.getWillflag()&0x01)<<2)|((cm.getWillqos()&0x03)<<3)
				|((cm.getWillretain()&0x01)<<5)|((cm.getPasswordflag()&0x01)<<6)|((cm.getUsernameflag()&0x01)<<7));
		bytearray.write(connectflag);
		byte keepaliveMSB=cm.getKeepaliveMSB();
		byte keepaliveLSB=cm.getKeepaliveLSB();
		bytearray.write(keepaliveMSB);
		bytearray.write(keepaliveLSB);
		encodevariableheader=bytearray.toByteArray();
	}
	private static void fixpayload(ConnectMessage cm) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		byte[] clientidentifier=StringtoUTF.convert(cm.getClientidentifier());
		byte clientidentifierMSB=(byte) (clientidentifier.length/256);
		byte clientidentifierLSB=(byte) (clientidentifier.length%256);
		bytearray.write(clientidentifierMSB);
		bytearray.write(clientidentifierLSB);
		bytearray.write(clientidentifier);
		if(cm.getWillflag()==1)
		{
			byte[] willtopic=StringtoUTF.convert(cm.getWilltopic());
			byte willtopicMSB=(byte) (willtopic.length/256);
			byte willtopicLSB=(byte) (willtopic.length%256);
			bytearray.write(willtopicMSB);
			bytearray.write(willtopicLSB);
			bytearray.write(willtopic);
			byte[] willmessage=StringtoUTF.convert(cm.getWillmessage());
			byte willmessageMSB=(byte) (willmessage.length/256);
			byte willmessageLSB=(byte) (willmessage.length%256);
			bytearray.write(willmessageMSB);
			bytearray.write(willmessageLSB);
			bytearray.write(willmessage);
		}
		if(cm.getUsernameflag()==1)
		{
			byte[] username=StringtoUTF.convert(cm.getUsername());
			byte usernameMSB=(byte) (username.length/256);
			byte usernameLSB=(byte) (username.length%256);
			bytearray.write(usernameMSB);
			bytearray.write(usernameLSB);
			bytearray.write(username);
		}
		if(cm.getPasswordflag()==1)
		{
			byte[] password=StringtoUTF.convert(cm.getPassword());
			byte passwordMSB=(byte) (password.length/256);
			byte passwordLSB=(byte) (password.length%256);
			bytearray.write(passwordMSB);
			bytearray.write(passwordLSB);
			bytearray.write(password);
		}
		encodepayload=bytearray.toByteArray();
	}	
}
