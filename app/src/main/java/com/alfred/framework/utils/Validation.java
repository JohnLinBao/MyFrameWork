package com.alfred.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.net.ParseException;

/**
 * 验证工具类
 * 
 * @author chenh
 * 
 */
@SuppressLint("DefaultLocale")
public class Validation {
	/**
	 * 汉字 字母
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isText(String text) {
		boolean isVaild = false;
		String expression = "(^([A-Za-z]|[\u4E00-\u9FA5])+$)";
		CharSequence inputNick = text;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputNick);
		if (matcher.matches()) {
			isVaild = true;
		}
		return isVaild;
	}
	/**
	 * 汉字 字母 数字
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isText_two(String text) {
		boolean isVaild = false;
		String expression = "(^([a-zA-Z0-9]|[\u4E00-\u9FA5])+$)";
		CharSequence inputNick = text;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputNick);
		if (matcher.matches()) {
			isVaild = true;
		}
		return isVaild;
	}
	/**
	 * 非表情判断
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isTextpasswrod(String text) {
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char codePoint = text.charAt(i);
			if (isEmojiCharacter(codePoint)) { // 如果匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		boolean temp = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

		return !temp;
	}

	/**
	 * 根据电话号码判断运营商
	 * 
	 * @param numbers
	 * @return
	 */
	public static String Operator(String numbers) {
		String Operator_name = "暂无";
		String number=numbers.substring(0, 3);
		if (isChina_Mobile(number)) {
			Operator_name = "中国移动";
		} else if (isChina_Unicom(number)) {
			Operator_name = "中国联通";
		} else if (isChina_Telecommunications(number)) {
			Operator_name = "中国电信";
		}

		return Operator_name;

	}

	/**
	 * 中国联通为true 不是为false
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isChina_Telecommunications(String number) {
		Boolean isChina_Telecommunications = false;
		String[] China_Telecommunications = { "133", "153", "180", "181", "189" };
		for (int i = 0; i < China_Telecommunications.length; i++) {
			if (number.equals(China_Telecommunications[i])) {
				isChina_Telecommunications = true;
			}
		}
		return isChina_Telecommunications;

	}

	/**
	 * 中国联通为true 不是为false
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isChina_Unicom(String number) {
		Boolean isChina_Unicom = false;
		String[] China_Unicom = { "130", "131", "132", "152", "155", "156", "185", "185" };
		for (int i = 0; i < China_Unicom.length; i++) {
			if (number.equals(China_Unicom[i])) {
				isChina_Unicom = true;
			}
		}
		return isChina_Unicom;

	}

	/**
	 * 中国移动为true 不是为false
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isChina_Mobile(String number) {
		Boolean isChina_Mobile = false;
		String[] China_Mobile = { "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159",
				"182", "183", "187", "188", "185" };
		for (int i = 0; i < China_Mobile.length; i++) {
			if (number.equals(China_Mobile[i])) {
				isChina_Mobile = true;
			}
		}
		return isChina_Mobile;

	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (StringUtils.isEmpty(mobiles)) {
			return false;
		} else {
			return mobiles.matches(telRegex);
		}
	}

	/**
	 * 邮箱验证 true:格式正确 false:格式不正确
	 */
	public static boolean isEmail(String strEmail) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 身份证验证
	 */
	@SuppressLint("DefaultLocale")
	public static String IDCardValidate(String IDStr) throws ParseException {
		String errorInfo = "";// 记录错误信息

		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		String Ai1 = "";
		String Ai2 = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return errorInfo;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai1 = Ai + strVerifyCode.toUpperCase();
		Ai2 = Ai + strVerifyCode.toLowerCase();
		if (IDStr.length() == 18) {
			if (Ai1.equals(IDStr) == false && Ai2.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPwd(String str){
		Pattern pattern = Pattern.compile("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}");
		Matcher matcher = pattern.matcher(str);
		if(matcher.matches())
			return true;
		else
			return false;
	}
}
