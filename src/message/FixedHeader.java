package message;

public class FixedHeader extends Message implements MessageID{
	private byte messagetype;
	private byte dup;
	private byte qos;
	private byte retain;
	private int remaininglength;
	
	public byte getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(byte messagetype) {
		this.messagetype = messagetype;
	}
	public byte getDup() {
		return dup;
	}
	public void setDup(byte dup) {
		this.dup = dup;
	}
	public byte getQos() {
		return qos;
	}
	public void setQos(byte qos) {
		this.qos = qos;
	}
	public byte getRetain() {
		return retain;
	}
	public void setRetain(byte retain) {
		this.retain = retain;
	}
	public int getRemaininglength() {
		return remaininglength;
	}
	public void setRemaininglength(int remaininglength) {
		this.remaininglength = remaininglength;
	}
	@Override
	public String getMessageid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMessageid(String messageid) {
		// TODO Auto-generated method stub
		
	}
}
