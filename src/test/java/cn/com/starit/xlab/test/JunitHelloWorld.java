package cn.com.starit.xlab.test;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2015/4/21.
 */
//ctrl + alt + l �Ű�
//˫��alt��ʾ�������
//shift+esc��С��
//ctrl + bʵ��
//ctrl + shift + b �鿴����
//ctrl + shift + ���¼� �ı�������С
//ctrl + w ѡ�б�ʶ�����С�����
//ctrl + z
//ctrl + shift +z

public class JunitHelloWorld {
    @Test
     public void helloWorldJunit(){
         HelloWorld helloWorld = new HelloWorld();
         assertEquals(helloWorld.sayHello("junit"),"���:junit");
     }
}
