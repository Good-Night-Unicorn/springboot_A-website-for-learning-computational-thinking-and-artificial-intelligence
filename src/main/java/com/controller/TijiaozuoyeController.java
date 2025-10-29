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

import com.entity.TijiaozuoyeEntity;
import com.entity.view.TijiaozuoyeView;

import com.service.TijiaozuoyeService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 提交作业
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 15:52:23
 */
@RestController
@RequestMapping("/tijiaozuoye")
public class TijiaozuoyeController {
    @Autowired
    private TijiaozuoyeService tijiaozuoyeService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TijiaozuoyeEntity tijiaozuoye,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("laoshi")) {
			tijiaozuoye.setLaoshizhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			tijiaozuoye.setXuehao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<TijiaozuoyeEntity> ew = new EntityWrapper<TijiaozuoyeEntity>();


        //查询结果
		PageUtils page = tijiaozuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijiaozuoye), params), params));
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
    public R list(@RequestParam Map<String, Object> params,TijiaozuoyeEntity tijiaozuoye, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<TijiaozuoyeEntity> ew = new EntityWrapper<TijiaozuoyeEntity>();

        //查询结果
		PageUtils page = tijiaozuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijiaozuoye), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TijiaozuoyeEntity tijiaozuoye){
       	EntityWrapper<TijiaozuoyeEntity> ew = new EntityWrapper<TijiaozuoyeEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tijiaozuoye, "tijiaozuoye")); 
        return R.ok().put("data", tijiaozuoyeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TijiaozuoyeEntity tijiaozuoye){
        EntityWrapper< TijiaozuoyeEntity> ew = new EntityWrapper< TijiaozuoyeEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tijiaozuoye, "tijiaozuoye")); 
		TijiaozuoyeView tijiaozuoyeView =  tijiaozuoyeService.selectView(ew);
		return R.ok("查询提交作业成功").put("data", tijiaozuoyeView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TijiaozuoyeEntity tijiaozuoye = tijiaozuoyeService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(tijiaozuoye,deSens);
        return R.ok().put("data", tijiaozuoye);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TijiaozuoyeEntity tijiaozuoye = tijiaozuoyeService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(tijiaozuoye,deSens);
        return R.ok().put("data", tijiaozuoye);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增提交作业") 
    public R save(@RequestBody TijiaozuoyeEntity tijiaozuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tijiaozuoye);
        tijiaozuoyeService.insert(tijiaozuoye);
        return R.ok().put("data",tijiaozuoye.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增提交作业")
    @RequestMapping("/add")
    public R add(@RequestBody TijiaozuoyeEntity tijiaozuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tijiaozuoye);
        tijiaozuoyeService.insert(tijiaozuoye);
        return R.ok().put("data",tijiaozuoye.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改提交作业")
    public R update(@RequestBody TijiaozuoyeEntity tijiaozuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tijiaozuoye);
        //全部更新
        tijiaozuoyeService.updateById(tijiaozuoye);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除提交作业")
    public R delete(@RequestBody Long[] ids){
        tijiaozuoyeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
