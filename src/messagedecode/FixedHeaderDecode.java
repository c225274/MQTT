package messagedecode;

import java.nio.*;


public class FixedHeaderDecode {
	
	private static byte messagetype;
	private static byte dup;
	private static byte qos;
	private static byte retain;
	private static int remaininglengthindex;
	private static int remaininglength;

	public static byte getMessagetype() {
		return messagetype;
	}

	public static void setMessagetype(byte messagetype) {
		FixedHeaderDecode.messagetype = messagetype;
	}

	public static byte getDup() {
		return dup;
	}

	public static void setDup(byte dup) {
		FixedHeaderDecode.dup = dup;
	}

	public static byte getQos() {
		return qos;
	}

	public static void setQos(byte qos) {
		FixedHeaderDecode.qos = qos;
	}

	public static byte getRetain() {
		return retain;
	}

	public static void setRetain(byte retain) {
		FixedHeaderDecode.retain = retain;
	}

	public static int getRemaininglengthindex() {
		return remaininglengthindex;
	}

	public static void setRemaininglengthindex(int remaininglengthindex) {
		FixedHeaderDecode.remaininglengthindex = remaininglengthindex;
	}

	public static int getRemaininglength() {
		return remaininglength;
	}

	public static void setRemaininglength(int remaininglength) {
		FixedHeaderDecode.remaininglength = remaininglength;
	}

	public static void  fixheader(ByteBuffer message)
	{
		byte byte1=message.get();
		messagetype=(byte) ((byte1&0xf0)>>>4);
		//System.out.println(messagetype);
		dup=(byte) ((byte1&0x08)>>>3);
		//System.out.println(dup);
		qos=(byte) ((byte1&0x06)>>>1);
		//System.out.println(qos);
		retain=(byte) ((byte1&0x01)>>>0);
		//System.out.println(retain);
		byte[] remaininglengthbytes=new byte[4];
		remaininglengthindex=0;
		remaininglength=0;
		do{
			remaininglengthbytes[remaininglengthindex]=message.get();
			int temp=remaininglengthbytes[remaininglengthindex]&0x7f;
			for(int i=0;i<remaininglengthindex;i++)
			{
				temp=temp*128;
			}
			remaininglength=remaininglength+temp;
			remaininglengthindex++;
		}while(((remaininglengthbytes[remaininglengthindex-1]&0x80)>>>7)==1);	
		//System.out.println(remaininglength);
	}
}
