package cn.com.starit.xlab.test;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.*;

/**
 *
 * Created by Administrator on 2015/4/23.
 */
/**
 * HttpClient,DefaultHttpClient,AbstractHttpClient区别
 * HttpClient是一个接口，定义了连接和访问规范
 * AbstractHttpClient 是一个实现了HttpClient接口的抽象类
 * DefaultHttpClient 是一个继承AbstractHttpClient的类
 */
public class LoginChinaUnicomWithCaptcha{
    public static void main(String[] args) throws Exception  {
        String name ="";
        /*手机服务密码*/
        String pwd  ="";
//      HttpClient httpClient = new DefaultHttpClient(); 4.x版本
//      HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = new DefaultHttpClient();
        //自定义所需要的cookie
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //生成验证码的链接
        String createCaptchaUrl = "http://uac.10010.com/portal/Service/CreateImage";
        //get captcha，获取验证码
        HttpGet captchaHttpGet = new HttpGet(createCaptchaUrl);
        HttpResponse captchaResponse = httpClient.execute(captchaHttpGet);
        if (captchaResponse.getStatusLine().getStatusCode() == 200){
            //将验证码写入本地
            saveToLocal(captchaResponse.getEntity(),"chinaunicom.capthca."+System.currentTimeMillis()+".png");
        }
        HttpResponse verifyResponse  = null;
        String captcha = null;
        String uvc = null;
//        do{
//
//        }while(EntityUtils.toString(verifyResponse.getEntity()).contains("true"));
        //输入验证码,读入键盘输入
        captcha = JOptionPane.showInputDialog("请输入验证码:");
        String verifyCaptchaUrl = "http://uac.10010.com/portal/Service/CtaIdyChk?verifyCode="+ captcha +"&verifyType=1";
        HttpGet verifyCaptchaGet = new HttpGet(verifyCaptchaUrl);
        verifyResponse = httpClient.execute(verifyCaptchaGet);
        AbstractHttpClient abstractHttpClient = (AbstractHttpClient) httpClient;
        for (Cookie cookie : abstractHttpClient.getCookieStore().getCookies()) {
            System.out.println(cookie.getName() + "--------:" + cookie.getValue());
            if (cookie.getName().equals("uacverifykey")){
                uvc = cookie.getValue();
                System.out.println("uvc = [" + uvc + "]");
            }
        }
        //登录
        String loginUrl = "https://uac.10010.com/portal/Service/MallLogin?userName=" +name+"&password=" +pwd+"&pwdType = 01&productType=01&verifyCode=" + captcha+ "&redirectType=03&uvc=" + uvc;;
        HttpGet loginGet = new HttpGet(loginUrl);
        CloseableHttpResponse loginResponse = httpclient.execute(loginGet);
        System.out.println("result: " +EntityUtils.toString(loginResponse.getEntity()));
        //抓取基本信息数据
        HttpPost basicHttpGet = new HttpPost("http://iservice.10010.com/ehallService/static/acctBalance/execute/YH102010005/QUERY_AcctBalance.processData/Result");
        saveToLocal(httpClient.execute(basicHttpGet).getEntity(),"chinaunicom.basic.html");
    }
    //将验证码写入本地
    public static void saveToLocal(HttpEntity httpEntity , String fileName){
        File dir = new File("D:/idea/image");
        if (!dir.isDirectory())
            dir.mkdir();
        File file = new File(dir.getAbsolutePath()+"/" + fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            InputStream inputStream = httpEntity.getContent();
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bytes))>0){
                out.write(bytes,0,length);
            }
            inputStream.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
