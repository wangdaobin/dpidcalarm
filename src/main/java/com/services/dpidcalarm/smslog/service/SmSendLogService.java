package com.services.dpidcalarm.smslog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.base.service.BaseServiceAdapter;
import com.services.dpidcalarm.smslog.bean.SmSendLog;
import com.services.dpidcalarm.smslog.dao.SmSendLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

@Service
@Transactional
public class SmSendLogService extends BaseServiceAdapter<SmSendLog>
{

    @Autowired
    private SmSendLogDao smSendLogDao;

    // @Autowired
    // private UserDao userDao;

    @Override
    public MyMapper<SmSendLog> getBaseMapper()
    {

        return this.smSendLogDao;
    }

    @Transactional(readOnly=true)
    @Override
    public PageInfo<SmSendLog> pageList(SmSendLog smSendLog, Integer pageNum, Integer pageSize)
    {
        if ((pageNum != null) && (pageSize != null)) {
            PageHelper.startPage(pageNum.intValue(), pageSize.intValue());
        }
        Example example = new Example(SmSendLog.class);
        Criteria criteria = example.createCriteria().andEqualTo("indicatorId", smSendLog.getIndicatorId());
        if ((smSendLog.getContent() != null) && (smSendLog.getContent().length() > 0)) {
            criteria.andCondition("content like concat('" + smSendLog.getContent() + "','%')");
        }
        if ((smSendLog.getStartTimeStr() != null) && (smSendLog.getStartTimeStr().length() > 0)) {
            smSendLog.setStartTimeStr(smSendLog.getStartTimeStr() + " 00:00:00");
            criteria.andGreaterThanOrEqualTo("createTime", smSendLog.getStartTimeStr());
        }
        if ((smSendLog.getEndTimeStr() != null) && (smSendLog.getEndTimeStr().length() > 0)) {
            smSendLog.setEndTimeStr(smSendLog.getEndTimeStr() + " 23:59:59");
            criteria.andLessThanOrEqualTo("createTime", smSendLog.getEndTimeStr());
        }
        example.orderBy("createTime").desc();
        List<SmSendLog> list = this.smSendLogDao.selectByExample(example);
        /**zt  需要调整**/
        for (SmSendLog smSendLog1 : list) {
            // User user = this.userDao.findByTelephone(smSendLog1.getReceiver());
            // smSendLog1.setReceiverRealName(user.getRealName());
        }
        return new PageInfo(list);
    }
}