package config;

import java.util.*;



import messageencode.*;
import messagehandle.*;
import messagedecode.*;

public class MessageType {
	public static byte CONNECT=1;
	public static byte CONNACK=2;
	public static byte PUBLISH=3;
	public static byte PUBACK=4;
	public static byte PUBREC=5;
	public static byte PUBREL=6;
	public static byte PUBCOMP=7;
	public static byte SUBCRIBE=8;
	public static byte SUBACK=9;
	public static byte UNSUBCRIBE=10;
	public static byte UNSUBACK=11;
	public static byte PINGREQ=12;
	public static byte PINGRESP=13;
	public static byte DISCONNECT=14;
	public static Map<String,Class<?>> typetomessageencode=new HashMap<String,Class<?>>();
	public static Map<String,Class<?>> typetomessagedecode=new HashMap<String,Class<?>>();
	public static Map<String,Class<?>> typetomessagehandle=new HashMap<String,Class<?>>();
	
	static 
	{
		typetomessageencode.put(Byte.toString(CONNECT), ConnectMessageEncode.class);
		typetomessagedecode.put(Byte.toString(CONNECT), ConnectMessageDecode.class);
		typetomessagehandle.put(Byte.toString(CONNECT), ConnectMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(CONNACK), ConnackMessageEncode.class);
		typetomessagedecode.put(Byte.toString(CONNACK), ConnackMessageDecode.class);
		typetomessagehandle.put(Byte.toString(CONNACK), ConnackMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(SUBCRIBE), SubscribeMessageEncode.class);
		typetomessagedecode.put(Byte.toString(SUBCRIBE), SubscribeMessageDecode.class);
		typetomessagehandle.put(Byte.toString(SUBCRIBE), SubscribeMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(SUBACK), SubackMessageEncode.class);
		typetomessagedecode.put(Byte.toString(SUBACK), SubackMessageDecode.class);
		typetomessagehandle.put(Byte.toString(SUBACK), SubackMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PUBLISH), PublishMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PUBLISH), PublishMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PUBLISH), PublishMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PUBACK), PubackMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PUBACK), PubackMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PUBACK), PubackMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PUBREC), PubrecMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PUBREC), PubrecMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PUBREC), PubrecMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PUBREL), PubrelMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PUBREL), PubrelMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PUBREL), PubrelMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PUBCOMP), PubcompMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PUBCOMP), PubcompMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PUBCOMP), PubcompMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(UNSUBCRIBE),UnsubscribeMessageEncode.class);
		typetomessagedecode.put(Byte.toString(UNSUBCRIBE),UnsubscribeMessageDecode.class);
		typetomessagehandle.put(Byte.toString(UNSUBCRIBE),UnsubscribeMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(UNSUBACK), UnsubackMessageEncode.class);
		typetomessagedecode.put(Byte.toString(UNSUBACK), UnsubackMessageDecode.class);
		typetomessagehandle.put(Byte.toString(UNSUBACK), UnsubackMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PINGREQ), PingreqMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PINGREQ), PingreqMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PINGREQ), PingreqMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(PINGRESP),PingrepMessageEncode.class);
		typetomessagedecode.put(Byte.toString(PINGRESP),PingrepMessageDecode.class);
		typetomessagehandle.put(Byte.toString(PINGRESP),PingrepMessageHandle.class);
		
		typetomessageencode.put(Byte.toString(DISCONNECT), DisconnectMessageEncode.class);
		typetomessagedecode.put(Byte.toString(DISCONNECT), DisconnectMessageDecode.class);
		typetomessagehandle.put(Byte.toString(DISCONNECT), DisconnectMessageHandle.class);

	}
	
	public static Map<String, Class<?>> getTypetomessageencode() {
		return typetomessageencode;
	}
	public static Map<String, Class<?>> getTypetomessagedecode() {
		return typetomessagedecode;
	}
	public static Map<String, Class<?>> getTypetomessagehandle() {
		return typetomessagehandle;
	}
}
