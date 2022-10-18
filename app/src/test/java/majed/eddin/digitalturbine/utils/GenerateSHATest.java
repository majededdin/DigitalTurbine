package majed.eddin.digitalturbine.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateSHATest {

    @Test
    public void SHA1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String input = "12345678910";
        String output;

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = input.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        output = convertToHex(sha1hash);

        assertNotNull(output);
    }

    private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}