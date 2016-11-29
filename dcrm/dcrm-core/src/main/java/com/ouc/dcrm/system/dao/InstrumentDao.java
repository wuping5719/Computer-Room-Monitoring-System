package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Instrument;

public interface InstrumentDao {
    int deleteByPrimaryKey(Integer insid);

    int insert(Instrument record);

    int insertSelective(Instrument record);

    Instrument selectByPrimaryKey(Integer insid);

    int updateByPrimaryKeySelective(Instrument record);

    int updateByPrimaryKey(Instrument record);
}