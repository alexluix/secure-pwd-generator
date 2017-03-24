package landpro.so;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Simple secure password generator.
 *
 * Password requirements:
 *  - must contain both numeric and alphabetic characters
 *  - must contain letters in both lower and upper case
 *  - must contain not more than 2 consecutive repetitive characters
 *
 *  @author landpro
 */
public class PasswordGenerator {

    private static final int MAX_REPETITIVE_CONSECUTIVE = 2;

    private static final String DIGITS = "0123456789";
    private static final String LOW_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL = "!@#$%^";

    private static final String ALL = DIGITS + LOW_ALPHA + UPPER_ALPHA + SPECIAL;

    private final SecureRandom random = new SecureRandom();
    private final char[] buf;

    public PasswordGenerator(int length) {
        if (length < 3) {
            throw new IllegalArgumentException("Length is too small");
        }

        buf = new char[length];
    }

    public String nextPassword() {
        char digit = getRandomCharacter(DIGITS);
        insertNext(random.nextInt(buf.length), digit);

        char lowerAlpha = getRandomCharacter(LOW_ALPHA);
        insertNext(random.nextInt(buf.length), lowerAlpha);

        char upperAlpha = getRandomCharacter(UPPER_ALPHA);
        insertNext(random.nextInt(buf.length), upperAlpha);

        for (int i = 0; i < buf.length - 3; i++) {
            boolean inserted;
            do {
                inserted = insertNext(random.nextInt(buf.length), getRandomCharacter(ALL));
            } while (!inserted);
        }

        String generatedPassword = new String(buf);

        Arrays.fill(buf, '\0');

        return generatedPassword;
    }

    private boolean insertNext(int pos, char ch) {
        for (int i = 0; i <= buf.length; i++) {
            if (buf[pos] == '\0') {
                if (noRepeatedSeqAround(buf, pos, ch)) {
                    buf[pos] = ch;
                    return true;
                }
            }

            pos = (pos + i) % buf.length;
        }

        return false;
    }

    private boolean noRepeatedSeqAround(char[] buf, int pos, char ch) {
        int backwardRepeatedSeq = 0;
        for (int i = pos - 1; i >= 0; i--) {
            if (buf[i] == ch) {
                backwardRepeatedSeq++;
            } else break;
        }

        int forwardRepeatedSeq = 0;
        for (int i = pos + 1; i < buf.length; i++) {
            if (buf[i] == ch) {
                forwardRepeatedSeq++;
            } else break;
        }

        return backwardRepeatedSeq + forwardRepeatedSeq + 1 <= MAX_REPETITIVE_CONSECUTIVE;
    }

    private char getRandomCharacter(String characters) {
        int randomIndex = random.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }

}
