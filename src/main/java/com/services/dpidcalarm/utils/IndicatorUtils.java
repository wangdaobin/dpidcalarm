package com.services.dpidcalarm.utils;

import com.services.dpidcalarm.sysManager.bean.Indicator;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2020/4/22 0022 23:06:24
 */
public class IndicatorUtils {
    private List<Indicator> listIndicatorOld = null;
    private List<Indicator> listIndicatorNew = null;

    /**
     * 更新旧指标
     * @param tempListIndicator
     */
    public void setListIndicatorOld(List<Indicator> tempListIndicator){
        this.listIndicatorOld = tempListIndicator;
    }

    /**
     * 更新新指标
     * @param tempListIndicator
     */
    public void setListIndicatorNew(List<Indicator> tempListIndicator){
        this.listIndicatorNew = tempListIndicator;
    }
    /**
     * 获得指标的最后更新时间
     * @param indicatorID
     * @return
     */
    public Date getIndicatorLastTime(int indicatorID){
        if(this.listIndicatorNew!=null){
            for(Indicator indicator:this.listIndicatorNew){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getLastTime();
                }
            }
        }
        return null;
    }

    //获得指标的最后更新时间是否是当天
    public boolean getIndicatorIsUpdateToday(int indicatorID){
        Date lastTimeDate = this.getIndicatorLastTime(indicatorID);
        if(lastTimeDate!=null){
            Date currentDate = new Date();
            return DateUtils.isSameDay(lastTimeDate,currentDate);
        }
        return  false;

    }


    /**
     * 获得指标的告警短信发送次数
     * @param indicatorID
     * @return
     */
    public int getIndicatorSentMsgCount(int indicatorID){
        if(this.listIndicatorNew!=null){
            for(Indicator indicator:this.listIndicatorNew){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getSendMsgCount();
                }
            }
        }
        return -1;

    }

    /**
     * 获得指标的告警限值
     * @param indicatorID
     * @return
     */
    public float getIndicatorLimitValue(int indicatorID){
        if(this.listIndicatorNew!=null){
            for(Indicator indicator:this.listIndicatorNew){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getLimitValue();
                }
            }
        }
        return -1;

    }

    /**
     * 获得指标的值
     * @param indicatorID
     * @return
     */
    /**
     *
     * @param indicatorID 指标ID
     * @param isNewIndicator  是否是更新后的指标
     * @return
     */
    public float getIndicatorLastValue(int indicatorID, int isNewIndicator){
        if(isNewIndicator==1 && this.listIndicatorNew!=null){
            for(Indicator indicator:this.listIndicatorNew){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getLastValue();
                }
            }
        }else if(isNewIndicator==0 && this.listIndicatorNew!=null){
            for(Indicator indicator:this.listIndicatorOld){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getLastValue();
                }
            }
        }
        return -1;

    }

    /**
     * 获得指标的名称
     * @param indicatorID
     * @return
     */
    public String getIndicatorName(int indicatorID){
        if(this.listIndicatorOld!=null){
            for(Indicator indicator:this.listIndicatorOld){
                //找到该ID
                if(indicatorID==indicator.getId()){
                    return indicator.getName();
                }
            }
        }
        return null;

    }

}