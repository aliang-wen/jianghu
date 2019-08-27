package com.aliang.aliangbok_until.urlbydata;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GetUrlData {
    /**
     * @Title：httpClient获取网页数据
     * @Param: 网址
     * @Return: 返回网页数据
     * @author: 阿良
     * @CreateDate: 2019.4.5
     */
    public static String getUrlData(String url) {
        // 这里是新建一个请求对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
       try {
           // 新建一个post请求对象
           // HttpPost httpPost = new HttpPost(url);
           // 设置请求放拦截
           String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
           // 新建一个get请求对象
           HttpGet httpget = new HttpGet(url);
           httpget.setHeader("User-Agent",userAgent);
           //设置请求的报文头部的编码
           httpget.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            //设置期望服务端返回的编码
           httpget.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

           CloseableHttpResponse response = httpclient.execute(httpget);
           try {
               // 获取响应实体
               HttpEntity entity = response.getEntity();
               // 打印响应状态
               if (entity != null) {
                   return EntityUtils.toString(entity, "utf-8");
               }
           } catch (Exception e){
               System.out.println(e);
           } finally {
               response.close();
           }
       } catch (Exception e) {
          System.out.println(e);
       }
       return "";
    }
    /**
     * @Title：通过jsoup解析页面数据
     * @Description: [功能描述]
     * @Param: html页面
     * @Return: 自己解析出来的数据
     * @author: 阿良
     * @CreateDate: 2019.4.5
     */
    public static Document analyzeHTMLByString(String url) {
        String html = getUrlData(url);
        Document document = Jsoup.parse(html);
        return document;
    }

}
