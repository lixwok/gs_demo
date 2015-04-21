package cn.com.starit.xlab.test;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2015/4/21.
 */
public class JunitHelloWorld {
    @Test
     public void helloWorldJunit(){
         HelloWorld helloWorld = new HelloWorld();
         assertEquals(helloWorld.sayHello("junit"),"ÄãºÃ:junit");
     }
}
