package com.services.dpidcalarm.collect.job;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 15:40:51
 */
public class CollectDataBTA
{
    private Logger logger = LoggerFactory.getLogger(CollectDataBTA.class);
    private static final String ROOT_URL = "http://10.55.6.114";
    private static final String LOGIN_URL = "/Loginout.gc";
    private static final String ERROR_COUNT_URL = "/check/WarnReport_WarnReport.gc?tableType=city&field=南岸&dataType=day&dianshu=5";

    public CloseableHttpClient login()
            throws IOException
    {
        CloseableHttpClient client = HttpClients.createDefault();
        NameValuePair username = new BasicNameValuePair("user", "gtzh");
        NameValuePair password = new BasicNameValuePair("pass", "123456");
        List list = new ArrayList();
        list.add(username);
        list.add(password);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
        HttpPost post = new HttpPost("http://10.55.6.114/Loginout.gc");
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        this.logger.info(EntityUtils.toString(response.getEntity()));
        return client;
    }

    public String getErrorCount(CloseableHttpClient client)
            throws IOException
    {
        HttpGet get = new HttpGet("http://10.55.6.114/check/WarnReport_WarnReport.gc?tableType=city&field=南岸&dataType=day&dianshu=5");
        return EntityUtils.toString(client.execute(get).getEntity());
    }
}