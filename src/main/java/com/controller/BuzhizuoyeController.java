package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.BuzhizuoyeEntity;
import com.entity.view.BuzhizuoyeView;

import com.service.BuzhizuoyeService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 布置作业
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 15:52:23
 */
@RestController
@RequestMapping("/buzhizuoye")
public class BuzhizuoyeController {
    @Autowired
    private BuzhizuoyeService buzhizuoyeService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,BuzhizuoyeEntity buzhizuoye,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("laoshi")) {
			buzhizuoye.setLaoshizhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			buzhizuoye.setXuehao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<BuzhizuoyeEntity> ew = new EntityWrapper<BuzhizuoyeEntity>();


        //查询结果
		PageUtils page = buzhizuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, buzhizuoye), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BuzhizuoyeEntity buzhizuoye, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<BuzhizuoyeEntity> ew = new EntityWrapper<BuzhizuoyeEntity>();

        //查询结果
		PageUtils page = buzhizuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, buzhizuoye), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BuzhizuoyeEntity buzhizuoye){
       	EntityWrapper<BuzhizuoyeEntity> ew = new EntityWrapper<BuzhizuoyeEntity>();
      	ew.allEq(MPUtil.allEQMapPre( buzhizuoye, "buzhizuoye")); 
        return R.ok().put("data", buzhizuoyeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BuzhizuoyeEntity buzhizuoye){
        EntityWrapper< BuzhizuoyeEntity> ew = new EntityWrapper< BuzhizuoyeEntity>();
 		ew.allEq(MPUtil.allEQMapPre( buzhizuoye, "buzhizuoye")); 
		BuzhizuoyeView buzhizuoyeView =  buzhizuoyeService.selectView(ew);
		return R.ok("查询布置作业成功").put("data", buzhizuoyeView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BuzhizuoyeEntity buzhizuoye = buzhizuoyeService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(buzhizuoye,deSens);
        return R.ok().put("data", buzhizuoye);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BuzhizuoyeEntity buzhizuoye = buzhizuoyeService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(buzhizuoye,deSens);
        return R.ok().put("data", buzhizuoye);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增布置作业") 
    public R save(@RequestBody BuzhizuoyeEntity buzhizuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(buzhizuoye);
        buzhizuoyeService.insert(buzhizuoye);
        return R.ok().put("data",buzhizuoye.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增布置作业")
    @RequestMapping("/add")
    public R add(@RequestBody BuzhizuoyeEntity buzhizuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(buzhizuoye);
        buzhizuoyeService.insert(buzhizuoye);
        return R.ok().put("data",buzhizuoye.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改布置作业")
    public R update(@RequestBody BuzhizuoyeEntity buzhizuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(buzhizuoye);
        //全部更新
        buzhizuoyeService.updateById(buzhizuoye);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除布置作业")
    public R delete(@RequestBody Long[] ids){
        buzhizuoyeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
