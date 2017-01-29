package by.belhostel.hostels.entity;

/**
 * Created by Roman on 08.01.2017.
 */
public class Message extends Entity {
    private int messageId;
    private int userId;
    private String sender;
    private String text;
    private boolean isDeleted = false;

    public Message() {
    }



    public Message(int userId, String sender, String text) {
        this.userId = userId;
        this.sender = sender;
        this.text = text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return messageId == message.messageId;

    }

    @Override
    public int hashCode() {
        return messageId;
    }
}
