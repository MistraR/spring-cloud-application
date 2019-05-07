package com.mistra.base.utils;

import java.math.BigInteger;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 09:46
 * @ Description: 二进制位运算工具类
 */
public class BitUtils {

    /**
     * 获取运算数指定位置的值
     * 例如：0000 1011 获取其第0位的值为 1 , 第2位的值为 0
     *
     * @param sourceInteger 需要运算的数
     * @param pos           指定位置 (0<=pos<=7)
     * @return 指定位置的值(0 or 1)
     */
    public static Integer getBitValue(Integer sourceInteger, int pos) {
        return ((sourceInteger >> pos) & 1);
    }

    /**
     * 将运算数指定位置的值置为指定值
     * 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1
     *
     * @param sourceInteger 需要运算的数
     * @param pos           指定位置 (0<=pos<=7)
     * @param value         只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static Integer setBitValue(Integer sourceInteger, int pos, Integer value) {
        byte mask = (byte) (1 << pos);
        if (value > 0) {
            sourceInteger |= mask;
        } else {
            sourceInteger &= (~mask);
        }
        return sourceInteger;
    }

    /**
     * 将运算数指定位置取反值 返回Integer
     * 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111
     *
     * @param sourceInteger
     * @param pos           指定位置 (0<=pos<=7)
     * @return 运算后的结果数
     */
    public static Integer reverseBitValue(Integer sourceInteger, int pos) {
        byte mask = (byte) (1 << pos);
        return (sourceInteger ^ mask);
    }

    /**
     * 检查运算数的指定位置是否为1
     *
     * @param sourceInteger 需要运算的数
     * @param pos           指定位置 (0<=pos<=7)
     * @return true 表示指定位置值为1, false 表示指定位置值为 0
     */
    public static boolean checkBitValue(Integer sourceInteger, int pos) {
        sourceInteger = (sourceInteger >>> pos);
        return (sourceInteger & 1) == 1;
    }

    /**
     * 十进制转换成二进制
     *
     * @param decimalSource 十进制数
     * @return String
     */
    public static String decimalToBinary(Integer decimalSource) {
        BigInteger bigInteger = new BigInteger(String.valueOf(decimalSource));
        //参数2指定的是转化成X进制，默认10进制
        return bigInteger.toString(2);
    }

    /**
     * 二进制转换成十进制
     *
     * @param binarySource 二进制数
     * @return int
     */
    public static int binaryToDecimal(String binarySource) {
        BigInteger bigInteger = new BigInteger(binarySource, 2);
        return Integer.parseInt(bigInteger.toString());
    }

    /**
     * 二进制加法
     *
     * @param a a
     * @param b b
     * @return String
     */
    public static String binaryAdd(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int x = 0;
        int y = 0;
        //进位
        int pre = 0;
        //存储进位和另两个位的和
        int sum = 0;

        while (a.length() != b.length()) {
            //将两个二进制的数位数补齐,在短的前面添0
            if (a.length() > b.length()) {
                b = "0" + b;
            } else {
                a = "0" + a;
            }
        }
        for (int i = a.length() - 1; i >= 0; i--) {
            x = a.charAt(i) - '0';
            y = b.charAt(i) - '0';
            sum = x + y + pre;//从低位做加法
            if (sum >= 2) {
                pre = 1;//进位
                sb.append(sum - 2);
            } else {
                pre = 0;
                sb.append(sum);
            }
        }
        if (pre == 1) {
            sb.append("1");
        }
        //翻转返回
        return sb.reverse().toString();
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 取十进制 11 (二级制 0000 1011) 为例子
        Integer source = 11;
        // 取第2位值并输出, 结果应为 0000 1011
        for (byte i = 7; i >= 0; i--) {
            System.out.printf("%d ", getBitValue(source, i));
        }
        // 将第6位置为1并输出 , 结果为 75 (0100 1011)
        System.out.println("\n" + setBitValue(source, 6, 1));
        // 将第6位取反并输出, 结果应为75(0100 1011)
        System.out.println(reverseBitValue(source, 6));
        // 检查第6位是否为1，结果应为false
        System.out.println(checkBitValue(source, 6));
        // 输出为1的位, 结果应为 0 1 3
        for (byte i = 0; i < 8; i++) {
            if (checkBitValue(source, i)) {
                System.out.printf("%d ", i);
            }
        }

        System.out.println("");
        System.out.println(setBitValue(2, 0, 1));
        System.out.println(reverseBitValue(11, 4));
        System.out.println(checkBitValue(3, 1));

        System.out.println(decimalToBinary(262162));
        System.out.println(binaryToDecimal("1000000000000010010"));
//        1 00000000011000 00000000000110
//        1 00000000011001 00000000000110
//        1 00000000011010 00000000000110
        System.out.println(binaryAdd("00000000000001", "1"));
        String str = "00010000000000000100000000000001";
        System.out.println(str.substring(0,str.length()-28));
        System.out.println(str.substring(str.length()-28,str.length()-14));
        System.out.println(str.substring(str.length()-14));
        System.out.println(binaryAdd(str.substring(str.length()-28,str.length()-14),"1"));
        System.out.println(Integer.MAX_VALUE);
    }
}
