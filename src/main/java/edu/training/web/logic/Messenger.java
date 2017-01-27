package edu.training.web.logic;

import edu.training.web.entity.Claim;
import edu.training.web.entity.Message;

import java.util.ResourceBundle;

/**
 * Created by Roman on 27.01.2017.
 */
public class Messenger {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages");
    private static final String SENDER_SUBSTITUTE = "<sender>";
    private static final String DATEIN_SUBSTITUTE = "<date_in>";
    private static final String DATEOUT_SUBSTITUTE = "<date_out>";
    private static final String HOSTEL_SUBSTITUTE = "<hostel>";
    private static final String ADMINISTRATION = "Administration";
    private static final String APPROVAL_TEXT = "message.approval";
    private static final String REJECTION_TEXT = "message.rejection";
    private static final String BAN_TEXT = "message.ban";
    private static final String UNBAN_TEXT = "message.unban";
    private static final String DISCOUNT_TEXT = "message.discount";
    private static final String PAYMENT_TEXT = "message.payment";
    public static Message generateApprovalMessage(int userId, String sender, Claim claim){
        String generatedText = resourceBundle.getString(APPROVAL_TEXT);
        generatedText = generatedText.replaceAll(SENDER_SUBSTITUTE, sender);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, sender, generatedText);
    }

    public static Message generateRejectionMessage(int userId, String hostel, Claim claim){
        String generatedText = resourceBundle.getString(REJECTION_TEXT);
        generatedText = generatedText.replaceAll(HOSTEL_SUBSTITUTE, hostel);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    public static Message generateBanMessage(int userId){
        String generatedText = resourceBundle.getString(BAN_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    public static Message generateUnbanMessage(int userId){
        String generatedText = resourceBundle.getString(UNBAN_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    public static Message generateDiscountMessage(int userId){
        String generatedText = resourceBundle.getString(DISCOUNT_TEXT);
        return new Message(userId, ADMINISTRATION, generatedText);
    }

    public static Message generatePaymentMessage(int userId,String sender, Claim claim){
        String generatedText = resourceBundle.getString(PAYMENT_TEXT);
        generatedText = generatedText.replaceAll(SENDER_SUBSTITUTE, sender);
        generatedText = generatedText.replaceAll(DATEIN_SUBSTITUTE, claim.getDateIn().toString());
        generatedText = generatedText.replaceAll(DATEOUT_SUBSTITUTE, claim.getDateOut().toString());
        return new Message(userId, sender, generatedText);
    }
}
