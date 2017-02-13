package com.ouc.dcrms.core.service;

import com.ouc.dcrms.core.model.Instrument;

/**
 * @author WuPing
 * @version 2017年2月10日 下午7:59:27
 */

public interface InstrumentServiceCore {
    
    public Instrument selectByPrimaryKey(Integer insid);
     
}

