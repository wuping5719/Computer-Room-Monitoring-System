package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Instrument;

public interface InstrumentDAO {
    int deleteByPrimaryKey(Integer insid);

    int insert(Instrument record);

    int insertSelective(Instrument record);

    Instrument selectByPrimaryKey(Integer insid);

    int updateByPrimaryKeySelective(Instrument record);

    int updateByPrimaryKey(Instrument record);
}