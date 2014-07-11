package util;

import java.io.*;



public class StringtoUTF 
{
	public static byte[] convert(String unicode) 
	{
		byte[] utf;
		try {
			utf=unicode.getBytes("UTF-8");
			return utf;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
