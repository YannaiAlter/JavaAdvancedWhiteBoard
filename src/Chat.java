import java.io.Serializable;

public class Chat implements Serializable {
	String chatConversation;
	public Chat(String chatConversation)
	{
		this.chatConversation = chatConversation;
	}
	public String getChatConversation()
	{
		return chatConversation;
	}
	public void setChatConversation(String chat) { this.chatConversation = chat;}
}
