package util;

import java.io.*;
import java.nio.ByteBuffer;

import org.junit.Test;

public class UTFtoString {
	
	public static String convert(ByteBuffer message) 
	{
		byte lengthMSB;
		byte lengthLSB;
		int byteslength;
		byte[] toconvertbyte;
		try {
			lengthMSB=message.get();
			lengthLSB=message.get();
			byteslength=(((((int)lengthMSB)<<24)>>>24)*256)+((((int)lengthLSB)<<24)>>>24);
			toconvertbyte=new byte[byteslength];
			message.get(toconvertbyte, 0, byteslength);
			return new String(toconvertbyte,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
