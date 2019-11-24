package com.services.dpidcalarm.collect.job;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 15:41:55
 */

import com.services.dpidcalarm.collect.bean.SvgUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class CollectDataSPOPS
{
    //服务根路径
    private static final String ROOT_URL = "http://10.55.1.99:8000/svg";
    //登录服务路径
    private static final String LOGIN_URL = "/SvgLoginServlet";
    //考核指标路径
    private static final String KHZB_URL = "/file/SvgTableGraphServlet";
    //账号
    private static final String USERNAME = "wzdd";
    //密码
    private static final String PASSWORD = "000";
    //测试路径
    private static final String TEST_ERROR_URL = "/file/SvgTableGraphServlet";
    private Logger logger = LoggerFactory.getLogger(CollectDataSPOPS.class);

    /**
     * 登录服务
     * @return
     * @throws IOException
     */
    public CloseableHttpClient login() throws IOException {
        BufferedReader br = null;
        InputStream is = null;
        HttpResponse response = null;
        StringBuffer sb = new StringBuffer();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //创建登录post对象，登录URL
            HttpPost post = new HttpPost(ROOT_URL + LOGIN_URL);
            //post参数 账号&密码
            HttpEntity requestEntity = new StringEntity(USERNAME + "&" + PASSWORD);
            post.setEntity(requestEntity);
            //执行登录请求
            response = client.execute(post);
            //拿到返回结果
            HttpEntity responseEntity = response.getEntity();
            is = responseEntity.getContent();
            //解析返回值
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while ((s = br.readLine()) != null){
                sb.append(s);
            }

        }
        finally {
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
        }
        this.logger.info("登录操作:\n返回结果:" + sb.toString());
        return client;
    }

    /**
     * 获取考核指标
     * @param client
     * @return
     * @throws IOException
     */
    public String getSvgImageForKHZB(CloseableHttpClient client) throws IOException {
        String result = null;

        //请求指标的地址：根目录 + 相对目录 + 时间戳
        HttpPost post = new HttpPost(ROOT_URL + KHZB_URL + "?timeStamp=" +  (new Date()).getTime());
        //请求参数，可能需要根据实际情况在调试：
        String param = "<root><TableData table_id='2038' app_id='201100' HisDataType='0' col_name_list='fa_name,name,sum_st,rate_dv,srate_meas,sum_meas,good_meas,bad_point,sum_v,good_v,sum_ln,good_ln,sum_xf,good_xf,visible,sum_ld,good_ld,sum_un,good_un' sql=\"select fa_name,name,sum_st,rate_dv,srate_meas,sum_meas,good_meas,bad_point,sum_v,good_v,sum_ln,good_ln,sum_xf,good_xf,visible,sum_ld,good_ld,sum_un,good_un from pasdv where visible = 1 \" finish_num='0' record_num='0' value='' m_SQL=\" f15 = 1 \" m_WhereFieldList_string=\"f15\" m_FieldInfoVec_string=\"0,f0,1,-1,-1,-1,-1;1,f1,7,-1,-1,-1,-1;2,f2,6,-1,-1,-1,-1;3,f3,12,-1,-1,-1,-1;4,f4,15,-1,-1,-1,-1;5,f5,143,-1,-1,-1,-1;6,f6,20,-1,-1,-1,-1;7,f7,21,-1,-1,-1,-1;8,f8,23,-1,-1,-1,-1;9,f9,28,-1,-1,-1,-1;10,f10,29,-1,-1,-1,-1;11,f11,24,-1,-1,-1,-1;12,f12,25,-1,-1,-1,-1;13,f13,30,-1,-1,-1,-1;14,f14,31,-1,-1,-1,-1;15,f15,11,-1,-1,-1,-1;16,f16,62,-1,-1,-1,-1;17,f17,63,-1,-1,-1,-1;18,f18,26,-1,-1,-1,-1;19,f19,27,-1,-1,-1,-1\" m_StatusQueryVec_string=\"\"/></root>";
        HttpEntity requestEntity = new StringEntity(param);
        post.setEntity(requestEntity);
        //执行请求
        HttpResponse response = client.execute(post);
        //请求的返回值
        InputStream is = response.getEntity().getContent();
        //处理解析结果
        result = SvgUtil.svgzHandler(is, "svg");

        return result;
    }

    /**
     *
     * @param client
     * @return
     * @throws IOException
     */
    public String getNananTestError(CloseableHttpClient client) throws IOException {
        String result = null;
        HttpPost post = new HttpPost("http://10.55.1.99:8000/svg/file/SvgTableGraphServlet?timeStamp=" + (new Date()).getTime());
        String param = "%3Croot%3E%3CTableData+table_id%3D%272064%27+app_id%3D%27201100%27+HisDataType%3D%271081085952%27+col_name_list%3D%27dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase%27+sql%3D%22select+dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase+from+pas_allmeasure+where+descr+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+finish_num%3D%270%27+record_num%3D%270%27+value%3D%27%27+m_SQL%3D%22+f3+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+m_WhereFieldList_string%3D%22f3%22+m_FieldInfoVec_string%3D%220%2Cf0%2C1%2C-1%2C-1%2C-1%2C-1%3B1%2Cf1%2C2%2C-1%2C-1%2C-1%2C-1%3B2%2Cf2%2C4%2C-1%2C-1%2C-1%2C-1%3B3%2Cf3%2C5%2C-1%2C-1%2C-1%2C-1%3B4%2Cf4%2C6%2C-1%2C-1%2C-1%2C-1%3B5%2Cf5%2C7%2C-1%2C-1%2C-1%2C-1%3B6%2Cf6%2C8%2C-1%2C-1%2C-1%2C-1%3B7%2Cf7%2C9%2C-1%2C-1%2C-1%2C-1%3B8%2Cf8%2C12%2C-1%2C-1%2C-1%2C-1%3B9%2Cf9%2C10%2C-1%2C-1%2C-1%2C-1%22+m_StatusQueryVec_string%3D%22%22%2F%3E%3C%2Froot%3E";
        HttpEntity requestEntity = new StringEntity("%3Croot%3E%3CTableData+table_id%3D%272064%27+app_id%3D%27201100%27+HisDataType%3D%271081085952%27+col_name_list%3D%27dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase%27+sql%3D%22select+dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase+from+pas_allmeasure+where+descr+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+finish_num%3D%270%27+record_num%3D%270%27+value%3D%27%27+m_SQL%3D%22+f3+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+m_WhereFieldList_string%3D%22f3%22+m_FieldInfoVec_string%3D%220%2Cf0%2C1%2C-1%2C-1%2C-1%2C-1%3B1%2Cf1%2C2%2C-1%2C-1%2C-1%2C-1%3B2%2Cf2%2C4%2C-1%2C-1%2C-1%2C-1%3B3%2Cf3%2C5%2C-1%2C-1%2C-1%2C-1%3B4%2Cf4%2C6%2C-1%2C-1%2C-1%2C-1%3B5%2Cf5%2C7%2C-1%2C-1%2C-1%2C-1%3B6%2Cf6%2C8%2C-1%2C-1%2C-1%2C-1%3B7%2Cf7%2C9%2C-1%2C-1%2C-1%2C-1%3B8%2Cf8%2C12%2C-1%2C-1%2C-1%2C-1%3B9%2Cf9%2C10%2C-1%2C-1%2C-1%2C-1%22+m_StatusQueryVec_string%3D%22%22%2F%3E%3C%2Froot%3E");
        post.setEntity(requestEntity);
        HttpResponse response = client.execute(post);
        InputStream is = response.getEntity().getContent();
        result = SvgUtil.svgzHandler(is, "svg");
        return result;
    }
}