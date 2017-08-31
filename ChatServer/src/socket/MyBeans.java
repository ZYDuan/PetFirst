package socket;


public class MyBeans {
	private int userId;
	private int toUserId;
	private String chatContent;
	private String time;
	
	public void setUserId(String userId){
		int Id=Integer.parseInt(userId);
		this.userId=Id;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public void setToUserId(String toUserId){
		int Id=Integer.parseInt(toUserId);
		this.toUserId=Id;
	}
	
	public int getToUserId(){
		return toUserId;
	}
	
	public void setChatContent(String chatContent){
		this.chatContent=chatContent;
	}
	
	public String getChatContent(){
		return chatContent;
	}
	
	public void setTime(String time){
		this.time=time;
	}
	
	public String getTime(){
		return time;
	}
}
