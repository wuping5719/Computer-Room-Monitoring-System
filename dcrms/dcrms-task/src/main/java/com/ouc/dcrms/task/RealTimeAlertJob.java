package com.ouc.dcrms.task;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.ouc.dcrms.core.service.AlertServiceCore;

/**
 * @author WuPing
 */

public class RealTimeAlertJob {

    private AlertServiceCore alertServiceCore;

    public Result run(Job job) throws Throwable {
	//AlertRecord alertRecord = alertServiceCore.getLatestAlert();
	
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
