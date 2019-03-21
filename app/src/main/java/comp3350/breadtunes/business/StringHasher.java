package comp3350.breadtunes.business;

import org.apache.commons.codec.digest.DigestUtils;

public class StringHasher {
    public static String sha256HexHash(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
