package com.hong.test;

import org.python.util.PythonInterpreter;

import java.util.Properties;

/**
 * @author jiaohongtao
 * @since 2019/9/24 14:28
 */
public class JythonHelloWorld {
	public static void main(String[] args) {
		/*// 报错Exception in thread "main" ImportError: Cannot import site module and its dependencies: No module named site
		try (PythonInterpreter pythonInterpreter = new PythonInterpreter()) {
			pythonInterpreter.exec("print ('hello world!')");
		}*/

		Properties props = new Properties();
		props.put("python.home", "path to the Lib folder");
		props.put("python.console.encoding", "UTF-8");
		props.put("python.security.respectJavaAccessibility", "false");
		props.put("python.import.site", "false");
		Properties preprops = System.getProperties();
		PythonInterpreter.initialize(preprops, props, new String[0]);

		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
		interpreter.exec("print days[1];");
	}
}
