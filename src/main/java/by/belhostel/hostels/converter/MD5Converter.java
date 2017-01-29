package by.belhostel.hostels.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Roman on 25.12.2016.
 */
public class MD5Converter {
    private static final Logger LOG = LogManager.getLogger();
    private static final String ALGORITHM = "MD5";
    private static MessageDigest md;

    public MD5Converter() {
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e);
        }
    }

    public String convert(String text) {
        byte[] textBytes = text.getBytes();
        md.reset();
        byte[] digested = md.digest(textBytes);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    }
}
