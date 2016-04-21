package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

/**
 * MD5加密与解密的工具类
 * 防篡改
 *
 * @author lin
 */
public final class MD5Utils {

    /**
     * MD5加密
     * <br>http://stackoverflow.com/questions/1057041/difference-between-java-and-php5-md5-hash
     * <br>http://code.google.com/p/roboguice/issues/detail?id=89
     *
     * @param string 源字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 根据指定的密钥及算法对指定字符串进行可逆加密。
     *
     * @param data      要进行加密的字符串。
     * @param key       密钥。
     * @param algorithm 算法。
     * @return 加密后的结果将由byte[]数组转换为16进制表示的数组。如果加密过程失败，将返回null。
     */
    public static String encrypt(String data, Key key, String algorithm)
            throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return StringUtils.byteArrayToHexString(cipher.doFinal(data
                .getBytes("utf8")));
    }

    /**
     * 根据指定的密钥及算法，将字符串进行解密。
     *
     * @param data      要进行解密的数据，它是由原来的byte[]数组转化为字符串的结果。
     * @param key       密钥。
     * @param algorithm 算法。
     * @return 解密后的结果。它由解密后的byte[]重新创建为String对象。如果解密失败，将返回null。
     * @throws Exception
     */
    public static String decrypt(String data, Key key, String algorithm)
            throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        String result = new String(cipher.doFinal(StringUtils
                .hexStringToByteArray(data)), "utf8");
        return result;
    }
}
