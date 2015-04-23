package cn.com.starit.xlab.test;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/22.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        System.out.println("hello world");
        get();
        return "hello world";
    }
    /**
     * post��ʽ�ύ����ģ���û���¼����
     */
    public void postForm() {
        // ����Ĭ�ϵ�httpClientʵ��.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // ����httppost
        HttpPost httppost = new HttpPost("http://60.174.249.204:8888/in/");
        // ������������
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", "wei.changjie"));
        formparams.add(new BasicNameValuePair("password", "weichangjie"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // �ر�����,�ͷ���Դ
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** * ���� get����
     */
    public void get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // ����httpget
            HttpGet httpget = new HttpGet("http://60.174.249.204:8888/in/");
            System.out.println("executing request " + httpget.getURI());
            // ִ��get����.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // ��ȡ��Ӧʵ��
                HttpEntity entity =  response.getEntity();
                System.out.println("--------------------------------------");
                // ��ӡ��Ӧ״̬
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // ��ӡ��Ӧ���ݳ���
                    System.out.println("Response content length: " + entity.getContentLength());
                    // ��ӡ��Ӧ����
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // �ر�����,�ͷ���Դ
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}