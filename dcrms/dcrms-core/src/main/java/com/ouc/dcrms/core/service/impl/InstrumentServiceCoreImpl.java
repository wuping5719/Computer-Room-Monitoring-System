package com.ouc.dcrms.core.service.impl;

import com.ouc.dcrms.core.model.Instrument;
import com.ouc.dcrms.core.service.InstrumentServiceCore;
import com.ouc.dcrms.core.dao.InstrumentDAO;

public class InstrumentServiceCoreImpl implements InstrumentServiceCore {
    
    private InstrumentDAO instrumentDAO;

    @Override
    public Instrument selectByPrimaryKey(Integer insid) {
	Instrument instrument = instrumentDAO.selectByPrimaryKey(insid);
	return instrument;
    }

    public InstrumentDAO getInstrumentDAO() {
	return instrumentDAO;
    }

    public void setInstrumentDAO(InstrumentDAO instrumentDAO) {
	this.instrumentDAO = instrumentDAO;
    }
    
}
