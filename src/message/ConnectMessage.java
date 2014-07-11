package message;

import config.KeepAliveTime;
import config.MessageType;

public class ConnectMessage extends FixedHeader{
	
	/////////variable header//////////
	private byte lengthMSB;//0-7 bit
	private byte lengthLSB;//0-7 bit
	private String protocalname;//6bytes
	private byte versionnum;//0-7 bit
	private byte cleansession;//1 bit
	private byte willflag;//2 bit
	private byte willqos;//3,4 bit
	private byte willretain;//5 bit
	private byte passwordflag;//6 bit
	private byte usernameflag;//7 bit
	private byte keepaliveMSB;//0-7bit
	private byte keepaliveLSB;//0-7bit
	
	public byte getLengthMSB() {
		return lengthMSB;
	}
	public void setLengthMSB(byte lengthMSB) {
		this.lengthMSB = lengthMSB;
	}
	public byte getLengthLSB() {
		return lengthLSB;
	}
	public void setLengthLSB(byte lengthLSB) {
		this.lengthLSB = lengthLSB;
	}
	public String getProtocalname() {
		return protocalname;
	}
	public void setProtocalname(String protocalname) {
		this.protocalname = protocalname;
	}
	public byte getVersionnum() {
		return versionnum;
	}
	public void setVersionnum(byte versionnum) {
		this.versionnum = versionnum;
	}
	public byte getCleansession() {
		return cleansession;
	}
	public void setCleansession(byte cleansession) {
		this.cleansession = cleansession;
	}
	public byte getWillflag() {
		return willflag;
	}
	public void setWillflag(byte willflag) {
		this.willflag = willflag;
	}
	public byte getWillqos() {
		return willqos;
	}
	public void setWillqos(byte willqos) {
		this.willqos = willqos;
	}
	public byte getWillretain() {
		return willretain;
	}
	public void setWillretain(byte willretain) {
		this.willretain = willretain;
	}
	public byte getPasswordflag() {
		return passwordflag;
	}
	public void setPasswordflag(byte passwordflag) {
		this.passwordflag = passwordflag;
	}
	public byte getUsernameflag() {
		return usernameflag;
	}
	public void setUsernameflag(byte usernameflag) {
		this.usernameflag = usernameflag;
	}
	public byte getKeepaliveMSB() {
		return keepaliveMSB;
	}
	public void setKeepaliveMSB(byte keepaliveMSB) {
		this.keepaliveMSB = keepaliveMSB;
	}
	public byte getKeepaliveLSB() {
		return keepaliveLSB;
	}
	public void setKeepaliveLSB(byte keepaliveLSB) {
		this.keepaliveLSB = keepaliveLSB;
	}
	/////////variable header//////////
	////////////payload////////////////
	private String clientidentifier;
	private String willtopic;
	private String willmessage;
	private String username;
	private String password;

	public String getClientidentifier() {
		return clientidentifier;
	}
	public void setClientidentifier(String clientidentifier) {
		this.clientidentifier = clientidentifier;
	}
	public String getWilltopic() {
		return willtopic;
	}
	public void setWilltopic(String willtopic) {
		this.willtopic = willtopic;
	}
	public String getWillmessage() {
		return willmessage;
	}
	public void setWillmessage(String willmessage) {
		this.willmessage = willmessage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	////////////payload////////////////
	
	public ConnectMessage() {
		this.setMessagetype(MessageType.CONNECT);
		this.setProtocalname("MQIsdp");
		this.setVersionnum((byte)3);
		this.setWillretain((byte)0);
		this.setWillqos((byte)1);
		this.setWillflag((byte)1);
		this.setCleansession((byte)0);
		this.setKeepaliveMSB(KeepAliveTime.getKeepAliveTimeMSB());
		this.setKeepaliveLSB(KeepAliveTime.getKeepAliveTimeLSB());
		this.setWilltopic("willtopic");
		this.setWillmessage("willmessage");
	}	
}
