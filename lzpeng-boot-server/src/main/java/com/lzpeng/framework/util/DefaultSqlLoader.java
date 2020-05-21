package com.lzpeng.framework.util;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * SQL 解析器
 * @date: 2020/5/22
 * @time: 00:47
 * @author:   李志鹏
 */
public class DefaultSqlLoader {
	
	

	private static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
	private static String MASK = "mask";
	private static String SUBJECT = "subject";
	private static String LEFT = "left";
	private static String RIGHT = "right";
	private static String AND = " and ";
	private static String LINK = "link";
	private static String BETWEEN = "between";
	private static String NOT_BETWEEN = "not between";
	private static String NULL = "null";
	private static String IN = "in";
	private static String NOT_IN = "not in";
	private static String SPACE = " ";
	private static String EMPTY = "";
	private static String QUOT = "'";
	private static String COMMA = ",";
	private static String LEFT_BRACKET = "(";
	private static String RIGHT_BRACKET = ")";
	private static Pattern PATTERN = Pattern.compile("(and|or)", 2);

	/**
	 * 解析filter
	 * @param filter
	 * @return
	 */
	public static String parseFilter(String filter) {
		return parseFilter(JSONUtil.parseObj(filter));
	}
	/**
	 * 解析filter
	 * @param map
	 * @return
	 */
	public static String parseFilter(Map<?, ?> map) {
		@SuppressWarnings("unchecked")
		List<Map<?, ?>> list = (List<Map<?, ?>>) map.get(SUBJECT);
		if (list == null) {
			return null;
		}
		String mask = (String) map.get(MASK);
		Matcher matcher = PATTERN.matcher(mask);
		boolean result = matcher.find();
		StringBuilder filterSql = new StringBuilder();
		for (Map<?, ?> m : list) {
			String filter = EMPTY;
			if (m.get(SUBJECT) != null) {
				filter = parseFilter(m);
				filterSql.append(LEFT_BRACKET).append(filter).append(RIGHT_BRACKET);
			} else {
				if (m.get(LEFT) != null);
				String left = m.get(LEFT).toString().trim();
				String link = m.get(LINK).toString().trim();
				String right = EMPTY;
				if ((IN.equalsIgnoreCase(link)) || (NOT_IN.equalsIgnoreCase(link))) {
					Object rightObj = m.get(RIGHT);
					if (rightObj instanceof String) {
						right = String.join(String.valueOf(rightObj), LEFT_BRACKET, RIGHT_BRACKET);
					} else {
						List<?> rightList = (List<?>) rightObj;
						if ((rightList.size() > 0) && (rightList.get(0) instanceof String)) {
							right = Arrays.stream(rightList.toArray()).map(String::valueOf)
									.collect(Collectors.joining(QUOT + COMMA + QUOT, LEFT_BRACKET + QUOT, QUOT + RIGHT_BRACKET));
						} else if ((rightList.size() > 0) && (rightList.get(0) instanceof Boolean)) {
							right = Arrays.stream(rightList.toArray()).map(String::valueOf)
									.collect(Collectors.joining(COMMA, LEFT_BRACKET, RIGHT_BRACKET));
						} else if (rightList.size() > 0) {
							right = Arrays.stream(rightList.toArray()).map(decimalFormat::format)
									.collect(Collectors.joining(COMMA, LEFT_BRACKET, RIGHT_BRACKET));
						} else {
							right = LEFT_BRACKET + RIGHT_BRACKET;
						}
						
					}
				} else if ((BETWEEN.equalsIgnoreCase(link)) || (NOT_BETWEEN.equalsIgnoreCase(link))) {
					List<?> r = (List<?>) m.get(RIGHT);
					if (r.get(0) instanceof String) {
						right = new StringBuilder(String.join(String.valueOf(r.get(0)), QUOT, QUOT))
								.append(AND)
								.append(String.join(String.valueOf(r.get(1)), QUOT, QUOT))
								.toString();
					} else {
						right = new StringBuilder()
								.append(decimalFormat.format(r.get(0)))
								.append(AND)
								.append(decimalFormat.format(r.get(1)))
								.toString();
					}
				} else {
					Object r = m.get(RIGHT);
					if (NULL.equalsIgnoreCase(r.toString())) {
						right = null;
					} else if (r instanceof String) {
						right = String.join(String.valueOf(r), QUOT, QUOT);
					} else if (r instanceof Boolean) {
						right = r.toString();
					} else {
						right = decimalFormat.format(r);
					}
				}
				filter = new StringBuilder().append(left).append(SPACE).append(link).append(SPACE).append(right)
						.toString();
				filterSql.append(filter);

			}

			if (result) {
				filterSql.append(new StringBuilder().append(SPACE).append(matcher.group(0)).append(SPACE).toString());
				result = matcher.find();
			}
		}
		return filterSql.toString();
	}

	public static void main(String[] args) {
		String sql = "{\"subject\":[{\"subject\":[{\"subject\":[{\"left\":\"applierCompany.id\",\"link\":\"=\",\"right\":\"CeUAAAAAlCnM567U\"}],\"mask\":\"#0\"},{\"subject\":[{\"subject\":[{\"left\":\"bizReqDate\",\"link\":\">=\",\"right\":\"2017-08-01\"}],\"mask\":\"#0\"},{\"subject\":[{\"left\":\"bizReqDate\",\"link\":\"<\",\"right\":\"2017-09-01\"}],\"mask\":\"#0\"}],\"mask\":\"#0and#1\"}],\"mask\":\"#0and#1\"},{\"subject\":[{\"subject\":[{\"left\":\"state\",\"link\":\"=\",\"right\":\"20\"},{\"left\":\"date\",\"link\":\"between\",\"right\":[\"20\",\"30\"]}],\"mask\":\"#0and#1\"},{\"left\":\"name\", \"link\": \"like\", \"right\": \"%lilu%\"}],\"mask\":\"#0or#1\"}],\"mask\":\"#0and#1\"}";
		System.out.println(sql);
		JSONObject map = JSONUtil.parseObj(sql);
		sql = parseFilter(map);
		System.out.println(sql);
	}

}
