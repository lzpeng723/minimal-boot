package com.lzpeng.common.utils;

import cn.hutool.script.ScriptUtil;
import lombok.SneakyThrows;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * var imp = JavaImporter(
 *		Packages.java.lang,
 *		Packages.java.util,
 *		Packages.cn.hutool.extra.spring
 * );
 * with (imp) {
 *     var date = new Date();
 *     System.out.println(date);
 *     var bean = SpringUtil.getBean("beanName");
 * }
 * Nashorn脚本 执行工具
 * @date: 2020/4/8
 * @time: 00:00
 * @author: 李志鹏
 * @see {https://www.jianshu.com/p/467aaf5254f8}
 */
public class NashornUtil {
	
	/**
	 * 动态执行 Nashorn 脚本
	 * @param script 脚本内容
	 * @param map 需要向脚本中注入的java对象
	 * @throws ScriptException 
	 * @throws NoSuchMethodException 
	 * @return
	 */
	@SneakyThrows({ScriptException.class, NoSuchMethodException.class})
	public static Object execute(String script, Map<String, Object> map) {
		ScriptEngine nashorn = ScriptUtil.getScript("nashorn");
		StringBuilder builder = new StringBuilder();
		if (map != null) {
			builder.append("function _(");
			String params = map.keySet().stream().collect(Collectors.joining(", "));
			builder.append(params);
			builder.append(") {");
			builder.append(script);
			builder.append("}");
			nashorn.eval(builder.toString());
			Invocable invocable = (Invocable) nashorn;
			Object result = invocable.invokeFunction("_", map.values().toArray());
			return result;
		} else {
			builder.append(script);
			Object result = nashorn.eval(builder.toString());
			return result;
		}
	}
	
	/**
	 * 动态执行 Nashorn 脚本
	 * @param scriptFile 脚本文件
	 * @param map 需要向脚本中注入的java对象
	 * @throws IOException 
	 * @throws ScriptException 
	 * @throws NoSuchMethodException 
	 * @return
	 */
	public static Object execute(File scriptFile, Map<String, Object> map) throws IOException {
		List<String> scripts = Files.readAllLines(scriptFile.toPath());
		String script = String.join(System.lineSeparator(), scripts);
		return execute(script, map);
	}


}
