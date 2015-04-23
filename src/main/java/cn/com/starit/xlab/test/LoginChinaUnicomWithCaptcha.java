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
 * HttpClient,DefaultHttpClient,AbstractHttpClient����
 * HttpClient��һ���ӿڣ����������Ӻͷ��ʹ淶
 * AbstractHttpClient ��һ��ʵ����HttpClient�ӿڵĳ�����
 * DefaultHttpClient ��һ���̳�AbstractHttpClient����
 */
public class LoginChinaUnicomWithCaptcha{
    public static void main(String[] args) throws Exception  {
        String name ="";
        /*�ֻ���������*/
        String pwd  ="";
//      HttpClient httpClient = new DefaultHttpClient(); 4.x�汾
//      HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = new DefaultHttpClient();
        //�Զ�������Ҫ��cookie
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //������֤�������
        String createCaptchaUrl = "http://uac.10010.com/portal/Service/CreateImage";
        //get captcha����ȡ��֤��
        HttpGet captchaHttpGet = new HttpGet(createCaptchaUrl);
        HttpResponse captchaResponse = httpClient.execute(captchaHttpGet);
        if (captchaResponse.getStatusLine().getStatusCode() == 200){
            //����֤��д�뱾��
            saveToLocal(captchaResponse.getEntity(),"chinaunicom.capthca."+System.currentTimeMillis()+".png");
        }
        HttpResponse verifyResponse  = null;
        String captcha = null;
        String uvc = null;
//        do{
//
//        }while(EntityUtils.toString(verifyResponse.getEntity()).contains("true"));
        //������֤��,�����������
        captcha = JOptionPane.showInputDialog("��������֤��:");
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
        //��¼
        String loginUrl = "https://uac.10010.com/portal/Service/MallLogin?userName=" +name+"&password=" +pwd+"&pwdType = 01&productType=01&verifyCode=" + captcha+ "&redirectType=03&uvc=" + uvc;;
        HttpGet loginGet = new HttpGet(loginUrl);
        CloseableHttpResponse loginResponse = httpclient.execute(loginGet);
        System.out.println("result: " +EntityUtils.toString(loginResponse.getEntity()));
        //ץȡ������Ϣ����
        HttpPost basicHttpGet = new HttpPost("http://iservice.10010.com/ehallService/static/acctBalance/execute/YH102010005/QUERY_AcctBalance.processData/Result");
        saveToLocal(httpClient.execute(basicHttpGet).getEntity(),"chinaunicom.basic.html");
    }
    //����֤��д�뱾��
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
