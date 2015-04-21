package cn.com.starit.xlab.test;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2015/4/21.
 */
//ctrl + alt + l 排版
//双击alt显示各输出框
//shift+esc最小化
//ctrl + b实现
//ctrl + shift + b 查看声明
//ctrl + shift + 上下键 改变输出框大小
//ctrl + w 选中标识符、行、多行
//ctrl + z
//ctrl + shift +z

public class JunitHelloWorld {
    @Test
     public void helloWorldJunit(){
         HelloWorld helloWorld = new HelloWorld();
         assertEquals(helloWorld.sayHello("junit"),"你好:junit");
     }
}
