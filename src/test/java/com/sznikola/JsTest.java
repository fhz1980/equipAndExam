package com.sznikola;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-13  11:34
 */
public class JsTest {

    @org.junit.Test

    public void test() throws Exception {

        ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");

        jsEngine.eval("var say = function(name) {return 'Hello ' + name;}");

        Invocable jsScript = (Invocable) jsEngine;

        Object result = jsScript.invokeFunction("say", "XYZ");

        System.out.println(result);

    }

}
