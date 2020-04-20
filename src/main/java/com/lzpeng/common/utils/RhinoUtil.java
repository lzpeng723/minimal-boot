package com.lzpeng.common.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * var imp = JavaImporter();
 * imp.importPackage(Packages.java.lang);
 * imp.importPackage(Packages.java.util);
 * imp.importPackage(Packages.cn.hutool.extra.spring);
 * with (imp) {
 *     var date = new Date();
 *     System.out.println(date);
 *     var bean = SpringUtil.getBean("beanName");
 * }
 * Rhino脚本 执行工具
 * @date: 2020/4/7
 * @time: 00:00
 * @author: 李志鹏
 * @see {https://github.com/mozilla/rhino}
 * @see {https://developer.mozilla.org/zh-CN/docs/Mozilla/Projects/Rhino/Scripting_Java}
 */
public class RhinoUtil {

	/**
	 * 动态执行 Rhino 脚本
	 * @param scriptName 脚本名称
	 * @param script 脚本内容
	 * @param map 需要向脚本中注入的java对象
	 * @return
	 */
	public static Object execute(String scriptName, String script, Map<String, Object> map) {
		Context context = Context.enter();  //获取环境设置
		Scriptable scope = context.initStandardObjects(); //初始化本地对象
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				Object jsObj = Context.javaToJS(entry.getValue(), scope); // 将java对象转为js对象
				ScriptableObject.putProperty(scope, entry.getKey(), jsObj);// 将java对象放置JS的作用域中
			}
		}
		// load外部的js文件获取文件内容
		// result 为执行JS产生的结果,如果JS中没有需要计算的表达式,结果为undefined
		Object result = context.evaluateString(scope, script, scriptName, 1, null); //执行
		Context.exit();  //退出
		return result;
	}
	
	/**
	 * 动态执行 Rhino 脚本
	 * @param scriptName 脚本名称
	 * @param scriptFile 脚本文件
	 * @param map 需要向脚本中注入的java对象
	 * @return
	 * @throws IOException 
	 */
	public static Object execute(String scriptName, File scriptFile, Map<String, Object> map) throws IOException {
		List<String> scripts = Files.readAllLines(scriptFile.toPath());
		String script = String.join(System.lineSeparator(), scripts);
		return execute(scriptName, script, map);
	}

}
