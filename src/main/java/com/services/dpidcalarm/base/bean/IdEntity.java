/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/10 0010 15:09:47
 */
package com.services.dpidcalarm.base.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class IdEntity
{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}