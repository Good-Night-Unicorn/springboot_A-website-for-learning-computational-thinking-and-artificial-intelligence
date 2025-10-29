package com.dao;

import com.entity.LaoshiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.LaoshiVO;
import com.entity.view.LaoshiView;


/**
 * 教师
 * 
 * @author 
 * @email 
 * @date 2025-02-16 15:52:22
 */
public interface LaoshiDao extends BaseMapper<LaoshiEntity> {
	
	List<LaoshiVO> selectListVO(@Param("ew") Wrapper<LaoshiEntity> wrapper);
	
	LaoshiVO selectVO(@Param("ew") Wrapper<LaoshiEntity> wrapper);
	
	List<LaoshiView> selectListView(@Param("ew") Wrapper<LaoshiEntity> wrapper);

	List<LaoshiView> selectListView(Pagination page,@Param("ew") Wrapper<LaoshiEntity> wrapper);

	
	LaoshiView selectView(@Param("ew") Wrapper<LaoshiEntity> wrapper);
	

}
