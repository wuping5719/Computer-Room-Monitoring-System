package com.ouc.dcrms.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.ouc.dcrms.core.service.AlertServiceCore;
import com.ouc.dcrms.core.model.AlertRecord;

/**
 * @author WuPing
 */

public class RealTimeAlertJob {

    private AlertServiceCore alertServiceCore;

    private static final Logger logger = LoggerFactory.getLogger(RealTimeAlertJob.class);

    @SuppressWarnings("deprecation")
    public Result run(Job job) throws Throwable {
	Date now = new Date();
	AlertRecord alertRecord = alertServiceCore.getLatestAlert();
	logger.info(now.toLocaleString()+ ":" + alertRecord.getAlertid());
	
	Result successResult = new Result(Action.EXECUTE_SUCCESS, "实时查询报警信息操作成功");
	return successResult;
    }

    public AlertServiceCore getAlertServiceCore() {
	return alertServiceCore;
    }

    public void setAlertServiceCore(AlertServiceCore alertServiceCore) {
	this.alertServiceCore = alertServiceCore;
    }
}
