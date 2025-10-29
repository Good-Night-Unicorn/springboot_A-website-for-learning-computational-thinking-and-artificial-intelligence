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

import com.entity.XuankexinxiEntity;
import com.entity.view.XuankexinxiView;

import com.service.XuankexinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 选课信息
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 15:52:23
 */
@RestController
@RequestMapping("/xuankexinxi")
public class XuankexinxiController {
    @Autowired
    private XuankexinxiService xuankexinxiService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XuankexinxiEntity xuankexinxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("laoshi")) {
			xuankexinxi.setLaoshizhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			xuankexinxi.setXuehao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<XuankexinxiEntity> ew = new EntityWrapper<XuankexinxiEntity>();


        //查询结果
		PageUtils page = xuankexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuankexinxi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,XuankexinxiEntity xuankexinxi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<XuankexinxiEntity> ew = new EntityWrapper<XuankexinxiEntity>();

        //查询结果
		PageUtils page = xuankexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuankexinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XuankexinxiEntity xuankexinxi){
       	EntityWrapper<XuankexinxiEntity> ew = new EntityWrapper<XuankexinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xuankexinxi, "xuankexinxi")); 
        return R.ok().put("data", xuankexinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XuankexinxiEntity xuankexinxi){
        EntityWrapper< XuankexinxiEntity> ew = new EntityWrapper< XuankexinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xuankexinxi, "xuankexinxi")); 
		XuankexinxiView xuankexinxiView =  xuankexinxiService.selectView(ew);
		return R.ok("查询选课信息成功").put("data", xuankexinxiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XuankexinxiEntity xuankexinxi = xuankexinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xuankexinxi,deSens);
        return R.ok().put("data", xuankexinxi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XuankexinxiEntity xuankexinxi = xuankexinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xuankexinxi,deSens);
        return R.ok().put("data", xuankexinxi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增选课信息") 
    public R save(@RequestBody XuankexinxiEntity xuankexinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuankexinxi);
        xuankexinxiService.insert(xuankexinxi);
        return R.ok().put("data",xuankexinxi.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增选课信息")
    @RequestMapping("/add")
    public R add(@RequestBody XuankexinxiEntity xuankexinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuankexinxi);
        xuankexinxiService.insert(xuankexinxi);
        return R.ok().put("data",xuankexinxi.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改选课信息")
    public R update(@RequestBody XuankexinxiEntity xuankexinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuankexinxi);
        //全部更新
        xuankexinxiService.updateById(xuankexinxi);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除选课信息")
    public R delete(@RequestBody Long[] ids){
        xuankexinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
