import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Chat implements Serializable {
	String chatConversation;
	Date updateTime;
	public Chat(String chatConversation)
	{
		this.chatConversation = chatConversation;
		updateTime=new GregorianCalendar(2000, Calendar.OCTOBER, 27).getTime();
	}
	public String getChatConversation()
	{
		return chatConversation;
	}
	public void setChatConversation(String chat) { this.chatConversation = chat;}
	public void doUpdate() {this.updateTime=new Date(System.currentTimeMillis());}
	public Date getChatUpdateTime() { return updateTime; }

}
