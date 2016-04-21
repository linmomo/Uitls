package com.example.utils;

import java.util.Random;

import android.text.TextUtils;


/**
 * 随机工具类
 * 
 */
public final class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 不可实例化
     */
    private RandomUtils() {
        throw new Error("Do not need instantiate!");
    }
    
    /**
     * 得到固定长度的随机字符串, 可以是任意字符
     *
     * @param sourceChar 字符串数组 s.toCharArray()
     * @param length     长度
     * @return <ul>
     * <li>if sourceChar is null or empty, return null</li>
     * <li>if length less than 0, return null</li>
     * </ul>
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
        	//返回下一个伪随机数 random.nextInt
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 得到固定长度的随机字符串,它是大写字母，小写字母和数字混合
     *
     * @param length 长度
     * @return 随机字符串
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 得到固定长度的随机数字字符串
     *
     * @param length 长度
     * @return 随机数字符串
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 得到固定长度的随机字符串, 它是大写字母和小写字母的混合
     *
     * @param length 长度
     * @return 随机字母字符串
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 得到固定长度的随机字符串, 它是大写字母的混合
     *
     * @param length 长度
     * @return 随机字符串 包括大写字母
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 得到固定长度的随机字符串, 它是小写字母的混合
     *
     * @param length 长度
     * @return 随机字符串 包括小写字母
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     *  得到固定长度的随机字符串, 它是字符资源的混合
     *
     * @param source 源字符串
     * @param length 长度
     * @return <ul>
     * <li>if source is null or empty, return null</li>
     * <li>else see {@link RandomUtils#getRandom(char[] sourceChar, int length)}</li>
     * </ul>
     */
    public static String getRandom(String source, int length) {
        return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }
    
    //------------随机数和洗牌算法---------------------------
    
    /**
     * 获得min到max之间的随机数
     *
     * @param min 最小随机数
     * @param max 最大随机数
     * @return <ul>
     * <li>if min > max, return 0</li>
     * <li>if min == max, return min</li>
     * <li>else return random int between min and max</li>
     * </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }
    

    /**
     * 获得0到max之间的随机数
     *
     * @param max 最大随机数
     * @return <ul>
     * <li>if max <= 0, return 0</li>
     * <li>else return random int between 0 and max</li>
     * </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }
    
    
    /**
     * 洗牌算法, 随机排列指定的数组
     *
     * @param objArray
     * @param shuffleCount洗牌次数
     * @return
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }


    /**
     * 洗牌算法, 随机排列指定的数组，洗牌次数为数组长度获得的随机数
     *
     * @param objArray
     * @return
     */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }
    
    /**
     * 洗牌算法, 随机排列指定int数组
     *
     * @param intArray
     * @param shuffleCount
     * @return
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }

    /**
     * 洗牌算法, 随机排列指定的int数组，使用数组长度的随机数
     *
     * @param intArray
     * @return
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }
}
