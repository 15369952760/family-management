package com.family.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sf.json.JSONObject;

/**
 * 
 * 项目名称：GMBuilderBaseTools 类名称：StringUtil 类描述： TODO(字符串处理工具类) 开发单位: 北京天耀宏图科技有限公司
 * 创建人：FanWY 创建时间：2014-9-19 上午9:44:31 修改人： 修改时间： 修改备注：
 * 
 * @version v1.0
 * 
 */
public class StringUtil {

	private static final String[] random={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	/**
	 * 雪花算法
	 */
	private static IdWorker idWorker = new IdWorker(0,0);
	
	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: split
	 * @Description: TODO(字符串拆分)
	 * @param @param str
	 * @param @param regex
	 * @param @return    设定文件
	 * @return String[]    返回类型
	 * @throws
	 */
	public static String[] split(String str,String regex){
		String[] tmpArray ={};
		if(str.indexOf(regex)>0){
			tmpArray=str.split(",");
		}else{
			//当前为一张表
			tmpArray[0]=str;
		}
		return tmpArray;
	}
	
	/**
	 * 
	 * @Title: objToString
	 * @Description: TODO(对象转字符串)
	 * @param @param obj
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String objToString(Object obj){
		if(obj!=null){
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		if (str == null) {
			return true;
		} else if (isEmpty(str.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.equalsIgnoreCase("null")) {
			return false;
		} else if (str.length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		return (!isEmpty(str));
	}
	
	/**
	 * 
	 * @Title: join
	 * @Description: TODO(Join)
	 * @param @param list
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static String filterNullJoin(List list, boolean isFilterNull) {
		if (list == null || list.size() < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (Object field : list) {
			if (field != null) {
				if (str.length() > 0) {
					str.append(",");
				}
				str.append("'");
				str.append(field.toString());
				str.append("'");
			} else {
				if (isFilterNull) {
					if (str.length() > 0) {
						str.append(",");
					}
					str.append("'");
					str.append("null");
					str.append("'");
				}
			}
		}
		return str.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String join(List list) {
		return filterNullJoin(list, false);
	}

	public static String join(Object[] list) {
		return filterNullJoin(list, false);
	}

	public static String filterNullJoin(Object[] list, boolean isFilterNull) {
		if (list == null || list.length < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (Object field : list) {
			if (field != null) {
				if (str.length() > 0) {
					str.append(",");
				}
				str.append("'");
				str.append(field.toString());
				str.append("'");
			} else {
				if (isFilterNull) {
					if (str.length() > 0) {
						str.append(",");
					}
					str.append("'");
					str.append("null");
					str.append("'");
				}
			}
		}
		return str.toString();
	}

	/**
	 * 把字符串按分隔符转换为数组
	 * 
	 * @param str
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return
	 */
	public static String[] stringToArray(String str, String expr) {
		return str.split(expr);
	}

	/**
	 * 把字符串按分隔符转换为list
	 * 
	 * @param str
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return
	 */
	public static List<String> stringToList(String str, String expr) {
		if(StringUtil.isEmpty(str)){
			return null;
		}
		List<String> list = new ArrayList<String>();
		String[] array = str.split(expr);
		for(String temStr : array){
			if(StringUtil.isNotEmpty(temStr)){
				list.add(temStr);
			}
		}
		return list;
	}
	
	/**
	 * 将数组按照给定的分隔转化成字符串
	 * 
	 * @param arr
	 * @param expr
	 * @return
	 */
	public static String arrayToString(String[] arr, String expr) {
		String strInfo = "";
		if (arr != null && arr.length > 0) {
			StringBuffer sf = new StringBuffer();
			for (String str : arr) {
				sf.append(str);
				sf.append(expr);
			}
			strInfo = sf.substring(0, sf.length() - 1);
		}
		return strInfo;
	}
	
	public static String[] mergeArrays(String[] a1,String[] a2){
		 	int aLen1=a1.length;//保存第一个数组长度
		    int aLen2=a2.length;//保存第二个数组长度
		    a1= Arrays.copyOf(a1,aLen1+aLen2);//扩容
		    System.arraycopy(a2, 0, a1, aLen1,aLen2);//将第二个数组与第一个数组合并
		return a1;
	}
	
	/**
	 * 字符串数组去重合并
	 * @return
	 */
	public static String[] mergeDistinctArrays(String[] a1,String[] a2){
		Set<String> set = new TreeSet<String>();
		for(String s : a1)
	        set.add(s);
	    for(String s : a2)
	        set.add(s);
	    String[] s = new String[set.toArray().length];
	    for(int i=0;i<s.length;i++){
	    	s[i]=set.toArray()[i].toString();
	    }
		return s;
	}
	
	/**
	 * 获取元素在数组中是否存在
	 * @param array
	 * @param item
	 * @return
	 */
	public static int findItemIndexOfArray(Object[] array , Object item) {
		List<Object> list = Arrays.asList(array);
		return list.indexOf(item);
	}
	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean listIsNotNull(List list){
		boolean flag = false;
		if(list!=null&&list.size()>0){
			flag =  true;
		}
		return flag;
	}

	/**
	 * 判断map是否为空
	 * @return
	 */
	public static boolean mapIsNotNull(Map map){
		boolean flag = false;
		if(map!=null&&map.size()>0){
			flag =  true;
		}
		return flag;
	}
	
	/**
	 * 判断数组是否为空
	 * @return
	 */
	public static boolean arrayIsNotNull(Object[] obj){
		boolean flag = false;
		if(obj!=null&&obj.length>0){
			flag =  true;
		}
		return flag;
	}
	
	/**
	 * Map转化为List
	 * @param map
	 * @return list
	 */
	public static <T> List<T> maptolist(Map<String, T> map){
		List<T> list = null;
		if(StringUtil.mapIsNotNull(map)){
			list = new ArrayList<T>();
			Set<String> keySet = map.keySet();
			for (String str : keySet) {
				list.add(map.get(str));
			}
		}
		return list;
	}
	
	/**
	 * List通过某个字段作为key转化为Map
	 * @return Map
	 */
	public static <T> Map<String,T> listtomap(List<T> list, String fieldname){
		Map<String,T> map = null;
		if(StringUtil.listIsNotNull(list) && StringUtil.isNotEmpty(fieldname)){
			map = new HashMap<String, T>();
			for (T t : list) {
				String mapkey = ReflectionUtil.invokeGetterMethod(t, fieldname).toString();
				map.put(mapkey, t);
			}
		}
		return map;
	}
	
	/**
	 * List通过某个字段去重
	 * @return List
	 */
	public static <T> List<T> listduplication(List<T> list, String fieldname){
		if(StringUtil.listIsNotNull(list) && StringUtil.isNotEmpty(fieldname)){
			Map<String, T> map = StringUtil.listtomap(list, fieldname);
			list = StringUtil.maptolist(map);
		}
		return list;
	}
	
	/**
	 * List变成某个字段单独的字符串list
	 * @return List
	 */
	public static <T> ArrayList<String> listtostrlist(List<T> list, String fieldname){
		ArrayList<String> strlist = null;
		if(StringUtil.listIsNotNull(list) && StringUtil.isNotEmpty(fieldname)){
			strlist = new ArrayList<String>();
			for (T t : list) {
				Object str = ReflectionUtil.invokeGetterMethod(t, fieldname);
				if(StringUtil.isEmpty(str)){
					strlist.add(null);
				}else{
					strlist.add(str.toString());
				}
				
			}
		}
		return strlist;
	}
	 
	/**
	 * 
	 * @Title: listToString
	 * @Description: TODO(将集合按照给定的分隔转化成字符串)
	 * @param list
	 * @param expr
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String listToString(List<String> list, String expr) {
		String strInfo = "";
		if (list != null && list.size() > 0) {
			StringBuffer sf = new StringBuffer();
			for (String str : list) {
				sf.append(str);
				sf.append(expr);
			}
			strInfo = sf.substring(0, sf.length() - 1);
		}
		return strInfo;
	}

	/**
	 * 
	 * @Title: firstUpperCase
	 * @Description: TODO(首字母大写)
	 * @param paramString
	 * @param isFirstUpperCase
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String firstUpperCase(String paramString,
			boolean isFirstUpperCase) {
		if (paramString == null)
			return null;
		StringBuffer localStringBuffer = new StringBuffer("");
		for (int i = 0;paramString!=null &&  i < paramString.length(); i++) {
			String zf = paramString.charAt(i) + "";
			if (i == 0) {
				if (isFirstUpperCase)
					localStringBuffer.append(zf.toUpperCase());
				else
					localStringBuffer.append(zf.toLowerCase());
			} else if (zf.equalsIgnoreCase("_")) {
				i++;
				zf = paramString.charAt(i) + "";
				localStringBuffer.append(zf.toUpperCase());
			} else {
				localStringBuffer.append(zf.toLowerCase());
			}
		}
		return localStringBuffer.toString();
	}
	
	/**
	 * 
	 * @Title: firstUpperCase
	 * @Description: TODO(首字母大写)
	 * @param paramString
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String firstUpperCase(String paramString) {
		if (paramString == null)
			return null;
		StringBuffer localStringBuffer = new StringBuffer("");
		for (int i = 0;paramString!=null &&  i < paramString.length(); i++) {
			String zf = paramString.charAt(i) + "";
			if (i == 0) {
				localStringBuffer.append(zf.toUpperCase());
			} else {
				localStringBuffer.append(zf);
			}
		}
		return localStringBuffer.toString();
	}

	/**
	 * 
	 * @Title: ACC
	 * @Description: TODO(ACC)
	 * @param str
	 * @return 设定文件11 String 返回类型
	 * @throws
	 */
	public static String ACC(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) + "").getBytes().length > 1) {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: getUUID
	 * @Description: TODO(获得一个UUID)
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String getUUID() {
		return idWorker.nextId()+"";
//		StringBuffer s = new StringBuffer(UUID.randomUUID().toString());
//		// 去掉“-”符号
//		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
//				+ s.substring(19, 23) + s.substring(24);
	}
	
	/**
	 * 
	 * @Title: getUUID
	 * @Description: TODO(获得指定数目的UUID)
	 * @param number
	 * @return 设定文件11 String[] 返回类型
	 * @throws
	 * 
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
	/**
	 * 
	 * @Title: genRandomNum
	 * @Description: TODO(生成秘钥)
	 * @param @param pwd_len
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String genRandomNum(int pwd_len) {  
		// 35是因为数组是从0开始的，26个字母+10个数字  
		final int maxNum = 36;  
		int i; // 生成的随机数  
		int count = 0; // 生成的密码的长度  
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',  
		'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  
		StringBuffer pwd = new StringBuffer("");  
		java.util.Random r = new java.util.Random();  
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，  
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1  
			if (i >= 0 && i < str.length) {  
			pwd.append(str[i]);  
			count++;  
		}  
		}  
		return pwd.toString();  
	}  

	/**
	 * 
	 * @Title: isNumeric
	 * @Description: TODO(判断某个字符串是否为数字)
	 * @param str
	 * @return 设定文件11 boolean 返回类型
	 * @throws
	 * 
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @Title: StringFilter
	 * @Description: TODO(字符串过滤器)
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 *             设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		if (str == null)
			return "";
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 简单的字符串格式化，性能较好。支持不多于10个占位符，从%1开始计算，数目可变。参数类型可以是字符串、Integer、Object，
	 * 甚至int等基本类型
	 * 、以及null，但只是简单的调用toString()，较复杂的情况用String.format()。每个参数可以在表达式出现多次。
	 * 
	 * @param msgWithFormat
	 * @param autoQuote
	 * @param args
	 * @return
	 * 
	 */
	public static StringBuilder formatMsg(CharSequence msgWithFormat,
			boolean autoQuote, Object... args) {
		int argsLen = args.length;
		boolean markFound = false;

		StringBuilder sb = new StringBuilder(msgWithFormat);

		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				String flag = "%" + (i + 1);
				int idx = sb.indexOf(flag);
				// 支持多次出现、替换的代码
				while (idx >= 0) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[i], autoQuote));
					idx = sb.indexOf(flag);
				}
			}

			if (args[argsLen - 1] instanceof Throwable) {
				StringWriter sw = new StringWriter();
				((Throwable) args[argsLen - 1])
						.printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if (argsLen == 1 && !markFound) {
				sb.append(args[argsLen - 1].toString());
			}
		}
		return sb;
	}

	/**
	 * 
	 * @Title: formatMsg
	 * @Description: TODO(格式化消息)
	 * @param msgWithFormat
	 * @param args
	 * @return 设定文件11 StringBuilder 返回类型
	 * @throws
	 */
	public static StringBuilder formatMsg(String msgWithFormat, Object... args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	/**
	 * 
	 * @Title: toString
	 * @Description: TODO(转换为字符串)
	 * @param obj
	 * @param autoQuote
	 * @return 设定文件11 String 返回类型
	 * @throws
	 */
	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else {
			if (obj instanceof Object[]) {
				for (int i = 0; i < ((Object[]) obj).length; i++) {
					sb.append(((Object[]) obj)[i]).append(", ");
				}
				if (sb.length() > 0) {
					sb.delete(sb.length() - 2, sb.length());
				}
			} else {
				sb.append(obj.toString());
			}
		}
		if (autoQuote
				&& sb.length() > 0
				&& !((sb.charAt(0) == '[' && sb.charAt(sb.length() - 1) == ']') || (sb
						.charAt(0) == '{' && sb.charAt(sb.length() - 1) == '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: convertQuot
	 * @Description: TODO(把字符串中的带‘与"转成\'与\")
	 * @param orgStr
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String convertQuot(String orgStr) {
		return orgStr.replace("'", "\\'").replace("\"", "\\\"");
	}
	
	/**
	 * 
	 * @Title: toUpperCaseFirstOne
	 * @Description: TODO(首字母大写)
	 * @param @param name
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String toUpperCaseFirstOne(String name) {
		char[] cs=name.toCharArray();
		cs[0]-=32;
		return String.valueOf(cs);
	}

	/**
	 * 
	 * @Title: htmlEntityToString
	 * @Description: TODO(HTML实体编码转成普通的编码)
	 * @param dataStr
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String htmlEntityToString(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			int system = 10;// 进制
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (start != t)
					start = t;
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);
				// 判断进制
				char s = charStr.charAt(0);
				if (s == 'x' || s == 'X') {
					system = 16;
					charStr = charStr.substring(1);
				}
			}
			// 转换
			try {
				char letter = (char) Integer.parseInt(charStr, system);
				buffer.append(new Character(letter).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			// 处理当前unicode字符到下一个unicode字符之间的非unicode字符
			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}

			// 处理最后面的非unicode字符
			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @Title: stringToHtmlEntity
	 * @Description: TODO(把String转成html实体字符)
	 * @param str
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String stringToHtmlEntity(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			switch (c) {
			case 0x0A:
				sb.append(c);
				break;

			case '<':
				sb.append("&lt;");
				break;

			case '>':
				sb.append("&gt;");
				break;

			case '&':
				sb.append("&amp;");
				break;

			case '\'':
				sb.append("&apos;");
				break;

			case '"':
				sb.append("&quot;");
				break;

			default:
				if ((c < ' ') || (c > 0x7E)) {
					sb.append("&#x");
					sb.append(Integer.toString(c, 16));
					sb.append(';');
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串 转unicode
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s) {
		String unicode = "";
		char[] charAry = new char[s.length()];
		for (int i = 0; i < charAry.length; i++) {
			charAry[i] = (char) s.charAt(i);
			unicode += "\\u" + Integer.toString(charAry[i], 16);
		}
		return unicode;
	}

	/**
	 * 
	 * @Title: unicodeToString
	 * @Description: TODO(unicode转字符串)
	 * @param unicodeStr
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String unicodeToString(String unicodeStr) {
		StringBuffer sb = new StringBuffer();
		String str[] = unicodeStr.toUpperCase().split("\\\\U");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(""))
				continue;
			char c = (char) Integer.parseInt(str[i].trim(), 16);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: html2Text
	 * @Description: TODO(转换html)
	 * @param inputString
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script,
					Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style,
					Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html,
					Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	/**
	 * 
	 * @Title: formatStringA
	 * @Description: TODO(将传入的字符串格式化：传入 a,b,c 格式化后 'a','b','c')
	 * @param sSource
	 * @return 设定文件11 String 返回类型
	 * @throws
	 * 
	 */
	public static String formatStringA(String sSource) {
		if (sSource == null)
			sSource = "''";
		else if (sSource.equals(""))
			sSource = "''";
		else {
			sSource = sSource.replaceAll(",", "','");
			sSource = "'" + sSource + "'";
		}

		return sSource;
	}

	/**
	 * 
	 * @Title: stringToDate
	 * @Description: TODO(字符串转日期)
	 * @param date
	 * @return 设定文件11 Date 返回类型
	 * @throws
	 * 
	 */
	public static Date stringToDate(String date) {
		if (date == null || "".equals(date))
			return null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = null;
		try {
			dt = sf.parse(date);
		} catch (ParseException e) {
			dt = null;
		}
		return dt;
	}

	/**
	 * 
	 * @Title: dateToString
	 * @Description: TODO(日期转字符串)
	 * @param date
	 * @return 设定文件11 String 返回类型
	 * @throws
	 */
	public static String dateToString(Date date) {
		String str = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str = sf.format(date);
		} catch (Exception ex) {
			str = "";
		}
		return str;
	}

	/**
	 * 
	 * @Title: getRealPath
	 * @Description: TODO(获取文件真实路径)
	 * @param @param fileName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getRealPath(String fileName) {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(fileName);
		if (url != null) {
			return url.getPath();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getRealPath
	 * @Description: TODO(获取文件真实路径)
	 * @param @param fileName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getRealPath2UTF8(String fileName) {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(fileName);
		if (url != null) {
			return path2UTF8(url.getPath());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 *  @Title md5
	 *  TODO(md5加密算法)
	 *  @param context
	 *  @return    返回类型   
	 * @throws
	 */
	public static String md5(String context) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(context.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return  buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 
	 * @Title: encryptMD5
	 * @Description: TODO(双重加密)
	 * @param @param context
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String encryptMD5(String context){
		try {
			return DesUtil.encrypt(md5(context));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}
	
	/**
	 * 获得项目的WEB-INF目录
	 * @return
	 */
	public  String getRootPath(){
		String modelPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		modelPath=modelPath.substring(0, modelPath.lastIndexOf("WEB-INF")+8);
		return modelPath;
	}
	
	/**
	 * 
	 *  @Title Random
	 *  TODO(生成随机字母)
	 *  @param count 生成的位数
	 *  @return    返回类型   
	 * @throws
	 */
	public static String Random(int count) {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<count;i++){
			sb.append(random[(int)Math.round(Math.random()*(51-0)+0)]);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: TranslateBaiDuString
	 * @Description: TODO(百度中英文翻译)
	 * @param @param strToTranslate
	 * @param @param fromLanguage
	 * @param @param toLanguage
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String TranslateBaiDuString(String strToTranslate, String fromLanguage, String toLanguage)
    {
        String translatedStr = "";
        String googleTransBaseUrl = "http://fanyi.baidu.com/v2transapi?";
        String googleTransUrl = googleTransBaseUrl;
        googleTransUrl += "&form=" + fromLanguage;
        googleTransUrl += "&to=" + toLanguage;
        googleTransUrl += "&query=" + strToTranslate;
        URL realUrl;
        BufferedReader in = null;
		try {
			realUrl = new URL(googleTransUrl);
			// 打开和URL之间的连接
	        URLConnection connection = realUrl.openConnection();
	        // 建立实际的连接
	        connection.connect();
	         // 定义 BufferedReader输入流来读取URL的响应
	         in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         String line;
	         while ((line = in.readLine()) != null) {
	        	 translatedStr += line;
	         }
	         translatedStr = JSONObject.fromObject(translatedStr).getJSONObject("trans_result").getJSONArray("data").getJSONObject(0).get("dst").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 if(in!=null){
				 try {
					in.close();
					in = null;
				} catch (IOException e) {
				}
			 }
		}
        return translatedStr;
    }
	
	/**
	 * 
	 * @Title: replaceOnce
	 * @Description: TODO(替换值)
	 * @param @param template
	 * @param @param placeholder
	 * @param @param replacement
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String replaceOnce(String template, String placeholder, String replacement) {
		if ( template == null ) {
			return template;
		}
        int loc = template.indexOf( placeholder );
		if ( loc < 0 ) {
			return template;
		}
		else {
			return new StringBuilder( template.substring( 0, loc ) )
					.append( replacement )
					.append( template.substring( loc + placeholder.length() ) )
					.toString();
		}
	}
	
	/**
	 * clob转换string
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String ClobToString(Clob clob,String encoder) { 
		String reString = ""; 
		Reader is = null;
		BufferedReader br = null;
		try {
				is = clob.getCharacterStream();
				br = new BufferedReader(is); 
				String s = br.readLine(); 
				StringBuffer sb = new StringBuffer(); 
				while (s != null) {// 执行循环将字符串全部取出付值给 StringBuffer由StringBuffer转成STRING 
					s = new String(s.getBytes(encoder),"utf-8");
					sb.append(s); 
					s = br.readLine(); 
				} 
				reString = sb.toString(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return reString; 
	}
	
	/**
	 * 
	 * @Title: path2UTF8
	 * @Description: TODO(路径转换)
	 * @param @param path
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String path2UTF8(String path){
		try {
			return URLDecoder.decode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return path;
	}
	
	/**
	 * List按照其中的某个Integer属性进行排序
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List listsortbyfield(List list,final String fieldname){
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object arg1, Object arg2) {
				int flag = 0;
				Object obj1 = ReflectionUtil.invokeGetterMethod(arg1, fieldname);
				Object obj2 = ReflectionUtil.invokeGetterMethod(arg2, fieldname);
				if(obj1==null)obj1 = 0;
				if(obj2==null)obj2 = 0;
				Integer para1 = Integer.parseInt(String.valueOf(obj1));
				Integer para2 = Integer.parseInt(String.valueOf(obj2));
				if(para1>para2){
					flag = 1;
				}else if(para1==para2){
					flag = 0;
				}else{
					flag = -1;
				}
				return flag;
			}
		});
		return list;
	}
	
	/**
	 * List按照其中的某个String属性进行排序
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List listsortbystrfield(List list,final String fieldname){
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object arg1, Object arg2) {
				Object obj1 = ReflectionUtil.invokeGetterMethod(arg1, fieldname);
				Object obj2 = ReflectionUtil.invokeGetterMethod(arg2, fieldname);
				if(obj1 == null || obj2 == null){
                    return -1;
                }
				String para1 = obj1.toString();
				String para2 = obj2.toString();
                if(para1.length() > para2.length()){
                    return 1;
                }
                if(para1.length() < para2.length()){
                    return -1;
                }
                if(para1.compareTo(para2) > 0){
                    return 1;
                }
                if(para1.compareTo(para2) < 0){
                    return -1;
                }
                if(para1.compareTo(para2) == 0){
                    return 0;
                }
				return 0;
			}
		});
		return list;
	}
	
	/**
	 * List按照某个已知的父节点进行排序
	 * @param needsortlist 需要排序的集合 sortlist 排序之后的集合 parentid 按照某个父节点排序
	 * @return
	 */
	public static <T> List<T> listsortbyparentid(List<T> needsortlist, List<T> sortlist, String parentid){
		//判断需要排序的集合是否为空并且遍历
		if(StringUtil.listIsNotNull(needsortlist) && StringUtil.isNotEmpty(parentid)){
			for (T t : needsortlist) {
				//通过反射获取其id与其父节点
				String menuId = ReflectionUtil.invokeGetterMethod(t, "id").toString();
				String menuPid = ReflectionUtil.invokeGetterMethod(t, "parentid").toString();
				//判断是否是当前传过来的父节点
				if(parentid.equals(menuPid)){
					sortlist.add(t);
					//递归调用自身
					sortlist = listsortbyparentid(needsortlist, sortlist, menuId);
				}
			}
		}
		return sortlist;
	}
	
	 public static String getRootPath(URL url) {
	 	String fileUrl = url.getFile();
	    int pos = fileUrl.indexOf('!');
	    if (-1 == pos) {
	    	return fileUrl;
	    }
	    return fileUrl.substring(5, pos);
	 }
	 
	 public static String dotToSplash(String name) {
	 	return name.replaceAll("\\.", "/");
	 }
	 
	 public static String trimExtension(String name) {
		 int pos = name.indexOf('.');
	     if (-1 != pos) {
	     	return name.substring(0, pos);
	     }
	     return name;
	  }
	 
	/**
     * 
     * @Title: formateFieldName
     * @Description: TODO(处理字段名)
     * @param @param filename
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
	public static String formatFieldName(String filename) {
		//处理以is字段
		StringBuilder sb = new StringBuilder(filename.toLowerCase());
		String temFieldName = null;
		//如果以is开头，则将第三个字符转换为大写。如 isparent -->> isParent
		if(sb.substring(0, 2).toString().equalsIgnoreCase("is")){
			sb.replace(2, 3, (sb.charAt(2)+"").toUpperCase());
			temFieldName = sb.toString();
		}
		temFieldName = temFieldName == null ? filename.toLowerCase() : temFieldName;
		return temFieldName;
	}
	
	/**
	 * 字段转换方法
	 * @param context
	 * @return
	 */
	public static String cfd(String context) {
		if (StringUtil.isEmpty(context))
			return null;
		StringBuffer localStringBuffer = new StringBuffer("");
		context=context.replace("_", "");
		for (int i = 0; i < context.length(); i++) {
			String zf = context.charAt(i)+"";
			if(i==0){
				localStringBuffer.append(zf.toLowerCase());
			}
//			else if(zf.equalsIgnoreCase("_")){
//				i++;
//				zf = context.charAt(i)+"";
//				localStringBuffer.append(zf.toUpperCase());
//			}
			else{
				localStringBuffer.append(zf.toLowerCase());
			}
		}
//		if(localStringBuffer.substring(0, 1).toString().equalsIgnoreCase("is")){
//			localStringBuffer.setCharAt(2, localStringBuffer.charAt(2));
//		}
		//如果以is开头，则将第三个字符转换为大写。如 isparent -->> isParent
		if(localStringBuffer.substring(0, 2).toString().equalsIgnoreCase("is")){
			localStringBuffer.replace(2, 3, (localStringBuffer.charAt(2)+"").toUpperCase());
		}
		return localStringBuffer.toString();
	}
}
