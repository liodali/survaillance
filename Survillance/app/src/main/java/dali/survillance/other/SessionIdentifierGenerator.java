package dali.survillance.other;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Mohamed ali on 14/01/2017.
 */

public class SessionIdentifierGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
    public static String CodeTracker() {
        return new BigInteger(200, random).toString(48);
    }
    public static String Name() {
        return new BigInteger(130, random).toString(10);
    }
}
