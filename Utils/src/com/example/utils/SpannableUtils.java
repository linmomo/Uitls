package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * Spannable工具类，用于设置文字的前景色、背景色、Typeface、粗体、斜体、字号、超链接、删除线、下划线、上下标等
 * <p/>
 * 	setTextSize("",24,0,2) = null;
 *	setTextSize(null,24,0,2) = null;
 *	setTextSize("abc",-2,0,2) = null;
 *	setTextSize("abc",24,0,4) = null;
 *	setTextSize("abc",24,-2,2) = null;
 *	setTextSize("abc",24,0,2) = normal string
 * <p/>
 * */
public class SpannableUtils {
	
	private SpannableUtils( ){
		throw new Error("Do not need instantiate!");
	}
	
	/**
	 * 改变字符串中的某一段文字 -字号
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @param fontSize  改变的字号
	 * @return
	 */
	public static SpannableString setTextSize( String content, int startIndex, int endIndex, int fontSize ){
		if( TextUtils.isEmpty( content ) || fontSize <= 0 || startIndex >= endIndex || startIndex < 0 || endIndex >= content.length( ) ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new AbsoluteSizeSpan( fontSize ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -上标
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextSub( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new SubscriptSpan( ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -下标
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextSuper( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new SuperscriptSpan( ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -中划线
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextStrikethrough( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -下划线
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextUnderline( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -加粗
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextBold( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -斜体
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextItalic( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -斜体加粗
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @return
	 */
	public static SpannableString setTextBoldItalic( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -前景色高亮
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @param foregroundColor  色值
	 * @return
	 */
	public static SpannableString setTextForeground( String content, int startIndex, int endIndex, int foregroundColor ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new ForegroundColorSpan( foregroundColor ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的多个文字 -前景色高亮
	 * @param content  文本内容
	 * @param color  颜色
	 * @param keyword  改变文本数组
	 * @return
	 */
    public static SpannableString setMoreTextForeground(String content,int color,String[] keyword) {
    	if( TextUtils.isEmpty( content )){
			return null;
		}
        SpannableString s = new SpannableString(content);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }
	
	/**
	 * 改变字符串中的某一段文字 -背景色
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @param backgroundColor  色值
	 * @return
	 */
	public static SpannableString setTextBackground( String content, int startIndex, int endIndex, int backgroundColor ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new BackgroundColorSpan( backgroundColor ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 设置文本的超链接
	 * @param content 需要处理的文本
	 * @param startIndex
	 * @param endIndex 被处理文本中需要处理字串的开始和结束索引
	 * @param url 文本对应的链接地址，需要注意格式：
	 * （1）电话以"tel:"打头，比如"tel:02355692427"
	 * （2）邮件以"mailto:"打头，比如"mailto:zmywly8866@gmail.com"
	 * （3）短信以"sms:"打头，比如"sms:02355692427"
	 * （4）彩信以"mms:"打头，比如"mms:02355692427"
	 * （5）地图以"geo:"打头，比如"geo:68.426537,68.123456"
	 * （6）网络以"http://"打头，比如"http://www.google.com"
	 * 要让超链可以点击需要设置如下方法：
	 *  TextView.setMovementMethod(new LinkMovementMethod());
	 * */
	public static SpannableString setTextURL( String content, int startIndex, int endIndex, String url ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new URLSpan( url ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * 改变字符串中的某一段文字 -加图片
	 * @param content 文本内容
	 * @param startIndex 起始位置
	 * @param endIndex  结束位置
	 * @param drawable  图片
	 * @return
	 */
	public static SpannableString setTextImg( String content, int startIndex, int endIndex, Drawable drawable ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new ImageSpan(drawable), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
}

