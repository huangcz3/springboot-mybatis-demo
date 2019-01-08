package com.neo.js;

import com.neo.test.js.ExecuteScript;
import com.neo.test.js.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * @author Huangcz
 * @date 2018-10-24 11:21
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvokeJsTest {
    public String path = String.valueOf(getClass().getClassLoader().getResource("static/"));
    @Test
    public void getScriptEngineFactory() {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            System.out.println(factory.getNames());
        }
    }

    @Test
    public void test(){
        ExecuteScript executeScript = new ExecuteScript();
        String fileLocation = path + "conwork.js";
        //通过下面一行代码就可以获取指定接口中方法的实例
        Method method = executeScript.getMethod(fileLocation, Method.class);
        String result = method.encodeInp("14142400755");
        System.out.println(result);
    }


}
