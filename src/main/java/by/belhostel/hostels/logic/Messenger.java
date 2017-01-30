package by.belhostel.hostels.logic;

import by.belhostel.hostels.entity.Claim;
import by.belhostel.hostels.entity.Message;
import java.util.ResourceBundle;

/**
 * Created by Roman on 27.01.2017.
 */
public class Messenger {

    /** The Constant resourceBundle. */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages");

    /** The Constant SENDER_SUBSTITUTE. */
    private static final String SENDER_SUBSTITUTE = "<sender>";

    /** The Constant DATEIN_SUBSTITUTE. */
    private static final String DATEIN_SUBSTITUTE = "<date_in>";

    /** The Constant DATEOUT_SUBSTITUTE. */
    private static final String DATEOUT_SUBSTITUTE = "<date_out>";

    /** The Constant HOSTEL_SUBSTITUTE. */
    private static final String HOSTEL_SUBSTITUTE = "<hostel>";

    /** The Constant ADMINISTRATION. */
    private static final String ADMINISTRATION = "Administration";

    /** The Constant APPROVAL_TEXT. */
    private static final String APPROVAL_TEXT = "message.approval";

    /** The Constant REJECTION_TEXT. */
    private static final String REJECTION_TEXT = "message.rejection";

    /** The Constant BAN_TEXT. */
    private static final String BAN_TEXT = "message.ban";

    /** The Constant UNBAN_TEXT. */
    private static final String UNBAN_TEXT = "message.unban";

    /** The Constant DISCOUNT_TEXT. */
    private static final String DISCOUNT_TEXT = "message.discount";

    /** The Constant PAYMENT_TEXT. */
    private static final String PAYMENT_TEXT = "message.payment";

    /**
     * Generate approval message.
     *
     * @param userId the user id
     * @param sender the sender
     * @param claim the claim
     * @return the message
     */
    public static Message generateApprovalMessage(int userId, String sender, Claim claim){
        String generatedText = resourceBundle.getString(APPROVAL_TEXT);
        generatedText = generatedText.replaceAll(SENDER_SUBSTITUTE, sender);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, sender, generatedText);
    }

    /**
     * Generate rejection message.
     *
     * @param userId the user id
     * @param hostel the hostel
     * @param claim the claim
     * @return the message
     */
    public static Message generateRejectionMessage(int userId, String hostel, Claim claim){
        String generatedText = resourceBundle.getString(REJECTION_TEXT);
        generatedText = generatedText.replaceAll(HOSTEL_SUBSTITUTE, hostel);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    /**
     * Generate ban message.
     *
     * @param userId the user id
     * @return the message
     */
    public static Message generateBanMessage(int userId){
        String generatedText = resourceBundle.getString(BAN_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    /**
     * Generate unban message.
     *
     * @param userId the user id
     * @return the message
     */
    public static Message generateUnbanMessage(int userId){
        String generatedText = resourceBundle.getString(UNBAN_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    /**
     * Generate discount message.
     *
     * @param userId the user id
     * @return the message
     */
    public static Message generateDiscountMessage(int userId){
        String generatedText = resourceBundle.getString(DISCOUNT_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    /**
     * Generate payment message.
     *
     * @param userId the user id
     * @param sender the sender
     * @param claim the claim
     * @return the message
     */
    public static Message generatePaymentMessage(int userId,String sender, Claim claim){
        String generatedText = resourceBundle.getString(PAYMENT_TEXT);
        generatedText = generatedText.replaceAll(SENDER_SUBSTITUTE, sender);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, sender, generatedText);
    }

}
