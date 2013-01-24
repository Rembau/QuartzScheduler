package org.rembau.quartz.tools;

import java.io.File;
import java.io.OutputStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.rembau.quartz.Context;

public class CompileTool {
	public CompileTool(){
	}
	public static Object compile(String className){
		OutputStream out=null,err=null;
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, out, err, Context.JOB_COMPILTEPATH+""+File.separator+""+className+".java");
		if(result!=0){
			System.out.println("Compile faild!");
		} else {
			try {
				Thread t= Thread.currentThread();
				ClassLoader loader = t.getContextClassLoader();
				Class<?> loadClass = loader.loadClass("compile."+className);
				Object o = loadClass.newInstance();
				return o;
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			} catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	public static void main(String[] args) {
		CompileTool.compile("Test");
	}
}
