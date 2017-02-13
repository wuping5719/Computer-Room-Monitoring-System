package com.ouc.dcrms.core.service.impl;

import com.ouc.dcrms.core.model.Reason;
import com.ouc.dcrms.core.service.ReasonServiceCore;
import com.ouc.dcrms.core.dao.ReasonDAO;

public class ReasonServiceCoreImpl implements ReasonServiceCore {
    
    private ReasonDAO reasonDAO;

    @Override
    public Reason selectByPrimaryKey(Integer reasonid) {
	Reason reason = reasonDAO.selectByPrimaryKey(reasonid);
	return reason;
    }

    public ReasonDAO getReasonDAO() {
	return reasonDAO;
    }

    public void setReasonDAO(ReasonDAO reasonDAO) {
	this.reasonDAO = reasonDAO;
    }
    
}
