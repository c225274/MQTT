package config;

public class KeepAliveTime {
	
	private static int KeepAliveTime=10000;//milis
	private static byte KeepAliveTimeMSB=(byte)(KeepAliveTime/256);
	private static byte KeepAliveTimeLSB=(byte)(KeepAliveTime%256);
	
	public static int getKeepAliveTime() {
		return ((((int)KeepAliveTimeLSB)<<24)>>>24)+((((int)KeepAliveTimeMSB)<<24)>>>24)*256;
	}

	public static void setKeepAliveTime(int keepAliveTime) {
		KeepAliveTime = keepAliveTime;
		KeepAliveTimeMSB=(byte)(KeepAliveTime/256);
		KeepAliveTimeLSB=(byte)(KeepAliveTime%256);
	}

	public static byte getKeepAliveTimeMSB() {
		return KeepAliveTimeMSB;
	}

	public static void setKeepAliveTimeMSB(byte keepAliveTimeMSB) {
		KeepAliveTimeMSB = keepAliveTimeMSB;
	}

	public static byte getKeepAliveTimeLSB() {
		return KeepAliveTimeLSB;
	}

	public static void setKeepAliveTimeLSB(byte keepAliveTimeLSB) {
		KeepAliveTimeLSB = keepAliveTimeLSB;
	}
}
