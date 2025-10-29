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

import com.entity.DiscusskechengxinxiEntity;
import com.entity.view.DiscusskechengxinxiView;

import com.service.DiscusskechengxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 课程信息评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 15:52:25
 */
@RestController
@RequestMapping("/discusskechengxinxi")
public class DiscusskechengxinxiController {
    @Autowired
    private DiscusskechengxinxiService discusskechengxinxiService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscusskechengxinxiEntity discusskechengxinxi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscusskechengxinxiEntity> ew = new EntityWrapper<DiscusskechengxinxiEntity>();


        //查询结果
		PageUtils page = discusskechengxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengxinxi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,DiscusskechengxinxiEntity discusskechengxinxi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscusskechengxinxiEntity> ew = new EntityWrapper<DiscusskechengxinxiEntity>();

        //查询结果
		PageUtils page = discusskechengxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengxinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscusskechengxinxiEntity discusskechengxinxi){
       	EntityWrapper<DiscusskechengxinxiEntity> ew = new EntityWrapper<DiscusskechengxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discusskechengxinxi, "discusskechengxinxi")); 
        return R.ok().put("data", discusskechengxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscusskechengxinxiEntity discusskechengxinxi){
        EntityWrapper< DiscusskechengxinxiEntity> ew = new EntityWrapper< DiscusskechengxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discusskechengxinxi, "discusskechengxinxi")); 
		DiscusskechengxinxiView discusskechengxinxiView =  discusskechengxinxiService.selectView(ew);
		return R.ok("查询课程信息评论表成功").put("data", discusskechengxinxiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscusskechengxinxiEntity discusskechengxinxi = discusskechengxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discusskechengxinxi,deSens);
        return R.ok().put("data", discusskechengxinxi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscusskechengxinxiEntity discusskechengxinxi = discusskechengxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discusskechengxinxi,deSens);
        return R.ok().put("data", discusskechengxinxi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增课程信息评论表") 
    public R save(@RequestBody DiscusskechengxinxiEntity discusskechengxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusskechengxinxi);
        discusskechengxinxiService.insert(discusskechengxinxi);
        return R.ok().put("data",discusskechengxinxi.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增课程信息评论表")
    @RequestMapping("/add")
    public R add(@RequestBody DiscusskechengxinxiEntity discusskechengxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusskechengxinxi);
        discusskechengxinxiService.insert(discusskechengxinxi);
        return R.ok().put("data",discusskechengxinxi.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscusskechengxinxiEntity discusskechengxinxi = discusskechengxinxiService.selectOne(new EntityWrapper<DiscusskechengxinxiEntity>().eq("", username));
        return R.ok().put("data", discusskechengxinxi);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscusskechengxinxiEntity discusskechengxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusskechengxinxi);
        //全部更新
        discusskechengxinxiService.updateById(discusskechengxinxi);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除课程信息评论表")
    public R delete(@RequestBody Long[] ids){
        discusskechengxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscusskechengxinxiEntity discusskechengxinxi, HttpServletRequest request,String pre){
        EntityWrapper<DiscusskechengxinxiEntity> ew = new EntityWrapper<DiscusskechengxinxiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = discusskechengxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengxinxi), params), params));
        return R.ok().put("data", page);
    }








}
