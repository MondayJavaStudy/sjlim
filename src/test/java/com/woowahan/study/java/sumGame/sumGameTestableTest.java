package com.woowahan.study.java.sumGame;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.*;


public class sumGameTestableTest {
    @Test
    public void test() throws Exception
    {
        Reader input = new StringReader("1 2 3 4 5\n8");
        StringWriter output = new StringWriter();

        sumGameTestable.doIt(input, output);

        Assert.assertEquals("1 2 5\n" + "1 3 4\n", output.toString());
    }



}
