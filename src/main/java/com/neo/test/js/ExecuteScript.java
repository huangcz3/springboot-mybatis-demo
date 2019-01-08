package com.neo.test.js;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * @author Huangcz
 * @date 2018-10-24 11:26
 * @desc
 */
public class ExecuteScript {

    /**
     * 从给定的js文件中获取指定接口中的方法的实例
     *
     * @param fileLoacation js文件路径
     * @param clazz         接口的class
     * @return 返回一个指定接口方法的实例
     */
    public <T> T getMethod(String fileLoacation, Class<T> clazz) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            //String path = ExecuteScript.class.getResource("").getPath();
            //System.out.println(path);
            // FileReader的参数为所要执行的js文件的路径
            engine.eval(new FileReader("D:\\mypoject\\chunjiedeweixiao\\spring-boot-mybatis-xml\\target\\classes\\static\\conwork.js"));
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                T executeMethod = invocable.getInterface(clazz);
                return executeMethod;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
