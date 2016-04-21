package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验
 *
 */
public final class RegUtils {
	
	/**
	 * 匹配全网IP的正则表达式
	 */
	public static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";
	
	/**
	 * 匹配手机号码的正则表达式
	 * <br>支持130——139、150——153、155——159、180、183、185、186、188、189号段
	 */
	public static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|5{1}[012356789]{1}|8{1}[035689]{1})\\d{8}$";
	
	/**
	 * 匹配邮箱的正则表达式
	 * <br>"www."可省略不写
	 */
	public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";
	
	/**
	 * 匹配汉字的正则表达式，个数限制为一个或多个
	 */
	public static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";
	
	/**
	 * 匹配正整数的正则表达式，个数限制为一个或多个
	 */
	public static final String POSITIVE_INTEGER_REGEX = "^\\d+$";
	
	/**
	 * 匹配身份证号的正则表达式
	 */
	public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
	
	/**
	 * 匹配邮编的正则表达式
	 */
	public static final String ZIP_CODE = "^\\d{6}$";
	
	/**
	 * 匹配URL的正则表达式
	 */
	public static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";
	

    /**
     * 不可实例化
     */
    private RegUtils() {
        throw new Error("Do not need instantiate!");
    }
    
	/**
	 * 正则验证
	 * 
	 * @param toCheckStr 待验证的字符串
	 * @param patternStr 验证格式字符串
	 * @return 是否通过验证
	 */
	public static boolean canMatch(String toCheckStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(toCheckStr);
		if(!matcher.matches()) {
			return false;
		}
		return true;
	}

    /**
     * 邮箱检测
     *
     * @param email 可能是Email的字符串
     * @return 是否是Email
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 邮箱验证
     *
     * @param data 可能是Email的字符串
     * @return 是否是Email
     */
    public static boolean isEmail2(String data) {
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return data.matches(expr);
    }

    /**
     * 移动手机号码验证
     *
     * @param data 可能是手机号码字符串
     * @return 是否是手机号码
     */
    public static boolean isMobileNumber(String data) {
        String expr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return data.matches(expr);
    }
    
	/**
	 * 办公电话验证 格式：区号(可选)-主机号-分机号(可选)
	 * 
	 * @param toCheckStr  toCheckStr
	 * @return   办公电话验证 格式：区号(可选)-主机号-分机号(可选)
	 */
	public static boolean isWorkPhone(String toCheckStr) {
		String patternStr = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{7,8}$)";
		return canMatch(toCheckStr, patternStr);
	}

	/**
	 * 常用固定电话验证 格式：区号(可选)-主机号
	 * 
	 * @param toCheckStr  toCheckStr
	 * @return   常用固定电话验证 格式：区号(可选)-主机号
	 */
	public static boolean isPhoneNumber(String toCheckStr) {
		String patternStr = "(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}$)";
		return canMatch(toCheckStr, patternStr);
	}
    
    /**
     * 身份证号码验证
     *
     * @param data 可能是身份证号码的字符串
     * @return 是否是身份证号码
     */
    public static boolean isCard(String data) {
        String expr = "^[0-9]{17}[0-9xX]$";
        return data.matches(expr);
    }
    
	/**
	 * 验证是否为身份证号
	 * 
	 * @param toCheckStr 待验证的字符串
	 * @return 是否通过验证
	 */
	public static boolean isIDCard(String toCheckStr) {
		// String patternStr =
		// "/^((1[1-5])|(2[1-3])|(3[0-7])|(4[1-6])|(5[0-4])|(6[0-9])|(7[12])|(8[0-9])|(9[0-9])|(10[0-9])|(11[0-1])|(12[0-9])|(13[0-3])|(14[0-9]))"
		// + "\\d{4}("
		// + "(19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))"
		// + "|(19\\d{2}(0[13578]|1[02])31)"
		// + "|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))"
		// + "|(19([13579][26]|[2468][048]|0[48])0229)"
		// + ")\\d{3}(\\d|X|x)?$/";
		// String patternStr1 =
		// "/^((1[1-5])|(2[1-3])|(3[0-7])|(4[1-6])|(5[0-4])|(6[0-9])|(7[0-9])|(8[0-9])|(9[0-1])|(10[0-9])|(11[0-3])|(12[0-9]))"
		// + "\\d{4}("
		// + "(16\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))"
		// + "|(16\\d{2}(0[13578]|1[02])31)"
		// + "|(16\\d{2}02(0[1-9]|1\\d|2[0-8]))"
		// + "|(16([13579][26]|[2468][048]|0[48])0229)"
		// + ")\\d{3}(\\d|X|x)?$/";
		String isIDCard1 = "^(([0-9]{14}[x0-9]{1})|([0-9]{17}[x0-9]{1}))$";
		// String
		// isIDCard2="/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$/";
		return canMatch(toCheckStr, isIDCard1);// || canMatch(toCheckStr,
												// isIDCard2);
	}
	
	/**
	 * 将身份证后六位隐藏,不显示
	 * 
	 * @param identityID  identityID
	 * @return  String
	 */
	public static String hideIdentityID(String identityID) {
		if(identityID != null && identityID.length() > 6) {
			identityID = identityID.substring(0, identityID.length() - 6)
					+ "******";
		}
		return identityID;
	}

    /**
     * 邮政编码验证
     *
     * @param data 可能包含邮政编码的字符串
     * @return 是否是邮政编码
     */
    public static boolean isPostCode(String data) {
        String expr = "^[0-9]{6,10}";
        return data.matches(expr);
    }

    /**
     * 长度验证
     *
     * @param data   源字符串
     * @param length 期望长度
     * @return 是否是期望长度
     */
    public static boolean isLength(String data, int length) {

        return data != null && data.length() == length;
    }

    /**
     * 只含字母和数字
     *
     * @param data 可能只包含字母和数字的字符串
     * @return 是否只包含字母和数字
     */
    public static boolean isNumberLetter(String data) {
        String expr = "^[A-Za-z0-9]+$";
        return data.matches(expr);
    }

    /**
     * 只含数字
     *
     * @param data 可能只包含数字的字符串
     * @return 是否只包含数字
     */
    public static boolean isNumber(String data) {
        String expr = "^[0-9]+$";
        return data.matches(expr);
    }

    /**
     * 只含字母
     *
     * @param data 可能只包含字母的字符串
     * @return 是否只包含字母
     */
    public static boolean isLetter(String data) {
        String expr = "^[A-Za-z]+$";
        return data.matches(expr);
    }

    /**
     * 只是中文
     *
     * @param data 可能是中文的字符串
     * @return 是否只是中文
     */
    public static boolean isChinese(String data) {
        String expr = "^[\u0391-\uFFE5]+$";
        return data.matches(expr);
    }

    /**
     * 包含中文
     *
     * @param data 可能包含中文的字符串
     * @return 是否包含中文
     */
    public static boolean isContainChinese(String data) {
        String chinese = "[\u0391-\uFFE5]";
        if (StringUtils.isEmpty(data)) {
            for (int i = 0; i < data.length(); i++) {
                String temp = data.substring(i, i + 1);
                boolean flag = temp.matches(chinese);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 小数点位数
     *
     * @param data   可能包含小数点的字符串
     * @param length 小数点后的长度
     * @return 是否小数点后有length位数字
     */
    public static boolean isDianWeiShu(String data, int length) {
        String expr = "^[1-9][0-9]+\\.[0-9]{" + length + "}$";
        return data.matches(expr);
    } 

	/**
	 * 验证是否为合法的用户名. 用户名只能由汉字、数字、字母、下划线组成，且不能为空.
	 * 
	 * @param toCheckStr 待验证的字符串
	 * @return 是否通过验证
	 */
	public static boolean isUserName(String toCheckStr) {
		String patternStr = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		return canMatch(toCheckStr, patternStr);
	}
	
	/**
	 * 验证是否为正常的文本内容. 内容只能为：汉字、数字、字母、下划线、 中文标点符号
	 * 英文标点符号，且不能为空.
	 * 
	 * @param toCheckStr 待验证的字符串
	 * @return 是否通过验证
	 */
	public static boolean isNormalText(String toCheckStr) {
		String patternStr = "^[a-zA-Z0-9_\u4e00-\u9fa5" // 汉字、数字、字母、下划线
				// 中文标点符号（。 ； ， ： “ ”（ ） 、 ！ ？ 《 》）
				+ "\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff01\uff1f\u300a\u300b"
				// 英文标点符号（. ; , : ' ( ) / ! ? < >）
				+ "\u002e\u003b\u002c\u003a\u0027\u0028\u0029\u002f\u0021\u003f\u003c\u003e\r\n"
				+ "]+$";
		return canMatch(toCheckStr, patternStr);
	}
	
	/**
	 * 匹配给定的字符串是否是一个全网IP
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean isIp(String string){
		return string.matches(IP_REGEX);
	}

	/**
	 * 验证是否为Url的文本内容. 内容只能为：数字、字母、英文标点符号（. : / ），且不能为空.
	 * 
	 * @param toCheckStr 待验证的字符串
	 * @return 是否通过验证
	 */
	public static boolean isUrlText(String toCheckStr) {
		String patternStr = "^[a-zA-Z0-9" // 数字、字母
				// 英文标点符号（. : /）
				+ "\u002e\u003a\u002f"
				+ "]+$";
		return canMatch(toCheckStr, patternStr);
	}

	/**
	 * 判断房间号是否符合规范：例如102,1202... 先判断3位或者4位的数字
	 * 
	 * @param roomNumber  roomNumber
	 * @return   boolean
	 */
	public static boolean checkRoomNumber(String roomNumber) {
		String regex = "^\\d{3,4}$";
		return Pattern.matches(regex, roomNumber);
	}
}
