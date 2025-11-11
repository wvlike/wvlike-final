package com.wvlike.user.archaius;

/**
 * @Date: 2025/6/26
 * @Author: tuxinwen
 * @Description: Base32 编码工具类
 */
public class Base32Encoder {

    /**
     * 基准字符串必须保证是2的指数级，当前取2^5，32个字符（顺序不定，保证各个字符唯一即可）, 去掉容易混淆的数值 0 1 与 字母 I O
     * 自定义 Base32 字符集（32个字符）
     */
    private static final String BASE_CHARS = "GXY7CPM9WL6UVH3285BKAJTRZNEQFDS4";

    private static final int CODE_LENGTH = 12;

    /**
     * 将 long 值转换为 Base32 字符串（默认长度为12）
     */
    public static String base32Encode(long serial) {
        return base32Encode(serial, CODE_LENGTH, BASE_CHARS);
    }

    /**
     * 将 long 值转换为指定长度的 Base32 字符串（不足前面补 'A'）
     */
    public static String base32Encode(long serial, int codeLength, String baseChars) {
        // 基准字符长度减1，用于取余数（即取基准字符串的第几位）
        int charAnd = baseChars.length() - 1;

        // 基准字符所占位数（即基准字符串的第几位代表多少位二进制数）
        int charBitAlign = (int) (Math.log(baseChars.length()) / Math.log(2));

        // 生成 Base32 编码串
        StringBuilder codeSerial = new StringBuilder();
        long tmpValue = serial;
        for (int i = 0; i < codeLength; i++) {
            int code = (int) (tmpValue & charAnd);
            codeSerial.append(baseChars.charAt(code));
            tmpValue = tmpValue >> charBitAlign;
        }

        // 低位反转(如不反转，解码时需倒着解)
        return codeSerial.reverse().toString();
    }

    public static void main(String[] args) {
        int charBitAlign = (int) (Math.log(32) / Math.log(2));
        int charBitAlign1 = Integer.bitCount(31); // 即 log2(base)
        System.out.println(charBitAlign);
        System.out.println(charBitAlign1);
    }

}