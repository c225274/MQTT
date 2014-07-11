package messageencode;

import java.io.ByteArrayOutputStream;


import message.*;

public class FixedHeaderEncode {
	public static byte[]encodefixhead;
	static int div;
	static int mod;
	static int sel=0x00000080;
	public static void fixheader(FixedHeader head,int fixheadlength)
	{
		ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
		byte fixheaderpartone=(byte) (((head.getMessagetype()&0x0f)<<4)|((head.getDup()&0x01)<<3)|((head.getQos()&0x03)<<1)|((head.getRetain()&0x01)<<0));
		bytearray.write(fixheaderpartone);
		do
		{
			div=fixheadlength/128;
			mod=fixheadlength%128;
			if(div!=0)
			{
				mod=mod|sel;
			}
			bytearray.write(mod);
			fixheadlength=fixheadlength/128;
		}while(div!=0);
		encodefixhead=bytearray.toByteArray();
	}
}
