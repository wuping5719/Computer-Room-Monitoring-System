package com.ouc.dcrms.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ouc.dcrms.core.model.AlertRecord;

public interface AlertRecordDAO {
    int deleteByPrimaryKey(Long alertid);

    int insert(AlertRecord record);

    int insertSelective(AlertRecord record);

    AlertRecord selectByPrimaryKey(Long alertid);

    AlertRecord selectLatestAlert();
    
    int selectTotalNum(@Param("siteid") Integer siteid,
	    @Param("reasonlevel") Integer reasonlevel,
	    @Param("startTime") Date startTime,
	    @Param("endTime") Date endTime);
    
    List<AlertRecord> selectAlertRecords(@Param("siteid") Integer siteid,
	    @Param("reasonlevel") Integer reasonlevel,
	    @Param("startTime") Date startTime,
	    @Param("endTime") Date endTime,
	    @Param("startIndex") int startIndex, 
	    @Param("pageSize") int pageSize);
    
    int updateByPrimaryKeySelective(AlertRecord record);

    int updateByPrimaryKey(AlertRecord record);
}