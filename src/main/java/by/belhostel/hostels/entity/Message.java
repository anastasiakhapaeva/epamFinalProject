package by.belhostel.hostels.entity;

/**
 * Created by Roman on 08.01.2017.
 */
public class Message extends Entity {

    /** The message id. */
    private int messageId;

    /** The user id. */
    private int userId;

    /** The sender. */
    private String sender;

    /** The text. */
    private String text;

    /** The is deleted. */
    private boolean isDeleted = false;

    /**
     * Instantiates a new message.
     */
    public Message() {
    }



    /**
     * Instantiates a new message.
     *
     * @param userId the user id
     * @param sender the sender
     * @param text the text
     */
    public Message(int userId, String sender, String text) {
        this.userId = userId;
        this.sender = sender;
        this.text = text;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted the new deleted
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /**
     * Gets the message id.
     *
     * @return the message id
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Sets the message id.
     *
     * @param messageId the new message id
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the sender.
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender.
     *
     * @param sender the new sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
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
