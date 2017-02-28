package com.ouc.dcrms.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ouc.dcrms.core.model.Resources;

public interface ResourcesDAO {
    int deleteByPrimaryKey(Integer resid);

    int insert(Resources record);

    int insertSelective(Resources record);

    Resources selectByPrimaryKey(Integer resid);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);
    
    int selectTotalNum(@Param("name") String name,
	    @Param("type") Byte type);
    
    List<Resources> selectResources(@Param("name") String name,
	    @Param("type") Byte type,
	    @Param("startIndex") int startIndex, 
	    @Param("pageSize") int pageSize);
    
    List<Resources> selectAllResource();
}