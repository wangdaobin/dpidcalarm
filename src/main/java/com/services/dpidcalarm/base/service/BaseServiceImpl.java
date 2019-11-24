package com.services.dpidcalarm.base.service;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 11:13:46
 */
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.base.bean.MyMapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseServiceImpl<T>
        implements BaseService<T>
{
    @Override
    public abstract MyMapper<T> getBaseMapper();

    @Transactional(readOnly=true)
    @Override
    public PageInfo<T> pageList(T t, Integer pageNum, Integer pageSize)
    {
        if ((pageNum != null) && (pageSize != null)) {
            PageHelper.startPage(pageNum.intValue(), pageSize.intValue());
        }
        return new PageInfo(getBaseMapper().select(t));
    }

    @Transactional(readOnly=true)
    @Override
    public T findById(Integer id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }
    @Override
    public void add(T t) throws Exception
    {
        getBaseMapper().insert(t);
    }
    @Override
    public void update(T t) throws Exception
    {
        getBaseMapper().updateByPrimaryKey(t);
    }

    @Override
    public void delete(Integer id) throws Exception
    {
        getBaseMapper().deleteByPrimaryKey(id);
    }
}