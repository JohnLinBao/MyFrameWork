package com.alfred.framework.utils;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音
 */
public class Chinese2Pin {
    /**
     * 取字符串首字母
     * @param chinese
     * @return
     */
    public static String getStringFirstLetter(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            Log.d("NEVER", String.valueOf(arr[i]));
            if (arr[i] > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().toLowerCase().trim();
    }

    /**
     * 取字符的首字母
     * @param c
     * @return
     */
    public static char getCharLetter(char c) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        try {
            //拼音数组 喊声调 如：单 解析后[dan1],[shan4]
            String[] _c = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
            if (_c != null) {
                return _c[0].charAt(0);//取数组第一位的第一个字符  即是首字母 d
            }else{
                return c;//非汉语字符 不处理直接返回
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            return c;
        }
    }
}
