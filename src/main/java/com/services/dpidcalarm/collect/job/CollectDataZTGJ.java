package com.services.dpidcalarm.collect.job;

import com.services.dpidcalarm.collect.bean.ErrorResult;
import com.services.dpidcalarm.collect.bean.SvgUtil;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
import com.services.dpidcalarm.smslog.bean.SmSendLog;
import com.services.dpidcalarm.utils.CommonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Description：采集状态估计合格率
 * @Author：zhangtao
 * @Date：2020/1/15 0015 9:21:11
 */
public class CollectDataZTGJ {

    //电站列表
    public static final List<String> stations = Arrays.asList(new String[] { "南湖站", "惠民站", "木洞站", "接龙站", "纳溪沟站", "安澜站", "鹿角站", "永隆站", "界石站", "五布站", "广阳站", "双河站", "姜家站", "苏家湾站", "东港站", "莲池站", "柳银站", "桥口坝站", "黄桷垭站", "回龙湾站", "百步梯站", "响水洞站", "阳光一百站", "弹子石站", "长生站", "海棠站", "花溪站", "峡口站", "迎龙站", "武堂站", "高峰寺站", "南彭站", "鱼洞站", "南坪站", "李家沱站", "土桥站", "天文站", "梓桐站", "丹桂站", "金竹站", "二塘站", "光国站", "天星寺站", "龙门浩站", "龙洲湾站", "柏子桥站", "书房站", "花红站", "白马山站", "金家岩站", "虎啸站", "四公里站", "鸡冠石站", "走马羊站" });

    //客户端句柄
    private CloseableHttpClient client = null;
    private Logger logger = LoggerFactory.getLogger(CollectDataSPOPS.class);



    /**
     * 登录服务
     * @return
     * @throws IOException
     */
    public boolean login(String loginURL, String user, String pwd) throws IOException {

        this.client = HttpClients.createDefault();
        try {
            //创建登录post对象，登录URL
            HttpPost post = new HttpPost("http://10.55.1.99:8000/svg/SvgLoginServlet");
            //post参数 账号&密码
            HttpEntity requestEntity = new StringEntity("wzdd" + "&" + "000");
            post.setEntity(requestEntity);
            //执行登录请求
            HttpResponse response = client.execute(post);
            this.logger.info("状态估计合格率登录结果：" + EntityUtils.toString(response.getEntity()));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取获取状态估计合格率的结果
     * @param url
     * @return
     * @throws IOException
     */
    public String getSvgImageForKHZB(String url) throws IOException {
        String result = null;


        url = "http://10.55.1.99:8000/svg/file/SvgTableGraphServlet" + "?timeStamp=" +  (new Date()).getTime();
        //请求指标的地址：根目录 + 相对目录 + 时间戳
        HttpPost post = new HttpPost(url);
        //请求参数，可能需要根据实际情况在调试：
        String param = "<root><TableData table_id='2038' app_id='201100' HisDataType='0' col_name_list='fa_name,name,sum_st,rate_dv,srate_meas,sum_meas,good_meas,bad_point,sum_v,good_v,sum_ln,good_ln,sum_xf,good_xf,visible,sum_ld,good_ld,sum_un,good_un' sql=\"select fa_name,name,sum_st,rate_dv,srate_meas,sum_meas,good_meas,bad_point,sum_v,good_v,sum_ln,good_ln,sum_xf,good_xf,visible,sum_ld,good_ld,sum_un,good_un from pasdv where visible = 1 \" finish_num='0' record_num='0' value='' m_SQL=\" f15 = 1 \" m_WhereFieldList_string=\"f15\" m_FieldInfoVec_string=\"0,f0,1,-1,-1,-1,-1;1,f1,7,-1,-1,-1,-1;2,f2,6,-1,-1,-1,-1;3,f3,12,-1,-1,-1,-1;4,f4,15,-1,-1,-1,-1;5,f5,143,-1,-1,-1,-1;6,f6,20,-1,-1,-1,-1;7,f7,21,-1,-1,-1,-1;8,f8,23,-1,-1,-1,-1;9,f9,28,-1,-1,-1,-1;10,f10,29,-1,-1,-1,-1;11,f11,24,-1,-1,-1,-1;12,f12,25,-1,-1,-1,-1;13,f13,30,-1,-1,-1,-1;14,f14,31,-1,-1,-1,-1;15,f15,11,-1,-1,-1,-1;16,f16,62,-1,-1,-1,-1;17,f17,63,-1,-1,-1,-1;18,f18,26,-1,-1,-1,-1;19,f19,27,-1,-1,-1,-1\" m_StatusQueryVec_string=\"\"/></root>";
        HttpEntity requestEntity = new StringEntity(param);
        post.setEntity(requestEntity);
        //执行请求
        HttpResponse response = this.client.execute(post);
        String svgResutl = EntityUtils.toString(response.getEntity());
        this.logger.info("状态估计合格率获取--原始结果：" + svgResutl);

        return svgResutl;
    }

    /**
     *
     * @param
     * @return
     * @throws IOException
     */
    public String getNananTestError(String shortName) {
        try {
            String result = null;
            HttpPost post = new HttpPost("http://10.55.1.99:8000/svg/file/SvgTableGraphServlet?timeStamp=" + (new Date()).getTime());
            String param = "<root><TableData table_id='2064' app_id='201100' HisDataType='1081085952' col_name_list='dv_name,st_id,descr,measure_type,value_,value_pre,value_res,value_r,base' sql=\"select dv_name,st_id,descr,measure_type,value_,value_pre,value_res,value_r,base from pas_allmeasure where descr LIKE '%" +
                    shortName +
                    "%' \" finish_num='0' record_num='0' value='' m_SQL=\" f3 LIKE '%" +
                    shortName +
                    "%' \" m_WhereFieldList_string=\"f3\" m_FieldInfoVec_string=\"0,f0,1,-1,-1,-1,-1;1,f1,2,-1,-1,-1,-1;2,f2,4,-1,-1,-1,-1;3,f3,5,-1,-1,-1,-1;4,f4,6,-1,-1,-1,-1;5,f5,7,-1,-1,-1,-1;6,f6,8,-1,-1,-1,-1;7,f7,9,-1,-1,-1,-1;8,f8,12,-1,-1,-1,-1;9,f9,10,-1,-1,-1,-1\" m_StatusQueryVec_string=\"\"/></root>";
            //String param = "%3Croot%3E%3CTableData+table_id%3D%272064%27+app_id%3D%27201100%27+HisDataType%3D%271081085952%27+col_name_list%3D%27dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase%27+sql%3D%22select+dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase+from+pas_allmeasure+where+descr+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+finish_num%3D%270%27+record_num%3D%270%27+value%3D%27%27+m_SQL%3D%22+f3+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+m_WhereFieldList_string%3D%22f3%22+m_FieldInfoVec_string%3D%220%2Cf0%2C1%2C-1%2C-1%2C-1%2C-1%3B1%2Cf1%2C2%2C-1%2C-1%2C-1%2C-1%3B2%2Cf2%2C4%2C-1%2C-1%2C-1%2C-1%3B3%2Cf3%2C5%2C-1%2C-1%2C-1%2C-1%3B4%2Cf4%2C6%2C-1%2C-1%2C-1%2C-1%3B5%2Cf5%2C7%2C-1%2C-1%2C-1%2C-1%3B6%2Cf6%2C8%2C-1%2C-1%2C-1%2C-1%3B7%2Cf7%2C9%2C-1%2C-1%2C-1%2C-1%3B8%2Cf8%2C12%2C-1%2C-1%2C-1%2C-1%3B9%2Cf9%2C10%2C-1%2C-1%2C-1%2C-1%22+m_StatusQueryVec_string%3D%22%22%2F%3E%3C%2Froot%3E";

            HttpEntity requestEntity = new StringEntity(URLEncoder.encode(param, "utf-8"));
            post.setEntity(requestEntity);
            HttpResponse response = this.client.execute(post);
            String svgResutl = EntityUtils.toString(response.getEntity());
            this.logger.info("getNananTestError状态估计合格率--结果1：" + svgResutl);
            InputStream is = response.getEntity().getContent();
            result = SvgUtil.svgzHandler(is, "svg");
            this.logger.info("getNananTestError状态估计合格率--结果2：" + result);
            List<ErrorResult> thisErrs = SvgUtil.getErrorResult(result);
            StringBuilder sb = new StringBuilder();
            for (ErrorResult er : thisErrs) {
                sb.append(";设备名称").append(er.getSbName()).append(",估计值").append(er.getGjValue()).append(",量测值").append(er.getLcValue());
            }
            this.logger.info("getNananTestError状态估计合格率--出错详情整理：" + sb.toString());
            return result;
        }catch (Exception e) {
            this.logger.info("getNananTestError状态估计合格率--出错：" + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    // //获取状态估计错误详情
    // public String getSvgImageForKHZBError(String url)
    //         throws IOException
    // {
    //     String result = null;
    //     url = "http://10.55.1.99:8000/svg/file/SvgTableGraphServlet?timeStamp=" + (new Date()).getTime();
    //
    //     HttpPost post = new HttpPost(url);
    //     String param = "%3Croot%3E%3CTableData+table_id%3D%272064%27+app_id%3D%27201100%27+HisDataType%3D%271081085952%27+col_name_list%3D%27dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase%27+sql%3D%22select+dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase+from+pas_allmeasure+where+descr+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+finish_num%3D%270%27+record_num%3D%270%27+value%3D%27%27+m_SQL%3D%22+f3+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+m_WhereFieldList_string%3D%22f3%22+m_FieldInfoVec_string%3D%220%2Cf0%2C1%2C-1%2C-1%2C-1%2C-1%3B1%2Cf1%2C2%2C-1%2C-1%2C-1%2C-1%3B2%2Cf2%2C4%2C-1%2C-1%2C-1%2C-1%3B3%2Cf3%2C5%2C-1%2C-1%2C-1%2C-1%3B4%2Cf4%2C6%2C-1%2C-1%2C-1%2C-1%3B5%2Cf5%2C7%2C-1%2C-1%2C-1%2C-1%3B6%2Cf6%2C8%2C-1%2C-1%2C-1%2C-1%3B7%2Cf7%2C9%2C-1%2C-1%2C-1%2C-1%3B8%2Cf8%2C12%2C-1%2C-1%2C-1%2C-1%3B9%2Cf9%2C10%2C-1%2C-1%2C-1%2C-1%22+m_StatusQueryVec_string%3D%22%22%2F%3E%3C%2Froot%3E";
    //     HttpEntity requestEntity = new StringEntity(param);
    //     post.setEntity(requestEntity);
    //     HttpResponse response = client.execute(post);
    //     // InputStream is = response.getEntity().getContent();
    //     // result = SvgUtil.svgzHandler(is, "svg");
    //     String svgResutl = EntityUtils.toString(response.getEntity());
    //     this.logger.info("状态估计合格率获取--原始结果：" + svgResutl);
    //     return result;
    // }






    /**
     *
     * @param svgResult
     */
    public float getResultZTGJ(String  svgResult, String comShortName) {
        if(null==svgResult || "".equals(svgResult)) {
            this.logger.info("获取的svgResult为空或者null，不处理：");
            return -1;
        }
        //获取厂站合格率
        float val = SvgUtil.getQulifyRateFloat(svgResult,comShortName);
        logger.info(new StringBuilder().append(comShortName + "状态估计合格率 val:-------").append(val).toString());
        return val;
    }


    public void printZTGJError(String comShortName){
        //详细错误点数
        String ersStr = this.getNananTestError(comShortName);
        List<ErrorResult> errorResultList = SvgUtil.getErrorResult(ersStr);

        for (ErrorResult er1 : errorResultList) {
            logger.info(
                    new StringBuilder().append("状态估计错误点数详情：设备名称：")
                            .append(er1.getSbName())
                            .append(",估计值：")
                            .append(er1.getGjValue())
                            .append(",量测值：")
                            .append(er1.getLcValue()).toString()
            );
        }
    }



}