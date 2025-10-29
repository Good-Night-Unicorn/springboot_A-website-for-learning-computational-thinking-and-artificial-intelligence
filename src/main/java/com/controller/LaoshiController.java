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
import com.entity.TokenEntity;
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

import com.entity.LaoshiEntity;
import com.entity.view.LaoshiView;

import com.service.LaoshiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 教师
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 15:52:22
 */
@RestController
@RequestMapping("/laoshi")
public class LaoshiController {
    @Autowired
    private LaoshiService laoshiService;






    
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		// 根据登录查询用户信息
        LaoshiEntity u = laoshiService.selectOne(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", username));
        // 当用户不存在或验证密码不通过时
		if(u==null || !u.getMima().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
		
        // 获取登录token
		String token = tokenService.generateToken(u.getId(), username,"laoshi",  "老师" );
        //返回token
		return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody LaoshiEntity laoshi){
    	//ValidatorUtils.validateEntity(laoshi);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	LaoshiEntity u = laoshiService.selectOne(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
        //判断是否存在相同老师账号，否则返回错误信息
        if(laoshiService.selectCount(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()))>0) {
            return R.error("老师账号已存在");
        }
		Long uId = new Date().getTime();
		laoshi.setId(uId);
        //保存用户
        laoshiService.insert(laoshi);
        return R.ok();
    }

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        LaoshiEntity u = laoshiService.selectById(id);
        return R.ok().put("data", u);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        LaoshiEntity u = laoshiService.selectOne(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456
        u.setMima("123456");
        laoshiService.updateById(u);
        return R.ok("密码已重置为：123456");
    }



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,LaoshiEntity laoshi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<LaoshiEntity> ew = new EntityWrapper<LaoshiEntity>();


        //查询结果
		PageUtils page = laoshiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, laoshi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,LaoshiEntity laoshi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<LaoshiEntity> ew = new EntityWrapper<LaoshiEntity>();

        //查询结果
		PageUtils page = laoshiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, laoshi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( LaoshiEntity laoshi){
       	EntityWrapper<LaoshiEntity> ew = new EntityWrapper<LaoshiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( laoshi, "laoshi")); 
        return R.ok().put("data", laoshiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(LaoshiEntity laoshi){
        EntityWrapper< LaoshiEntity> ew = new EntityWrapper< LaoshiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( laoshi, "laoshi")); 
		LaoshiView laoshiView =  laoshiService.selectView(ew);
		return R.ok("查询教师成功").put("data", laoshiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        LaoshiEntity laoshi = laoshiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(laoshi,deSens);
        return R.ok().put("data", laoshi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        LaoshiEntity laoshi = laoshiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(laoshi,deSens);
        return R.ok().put("data", laoshi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增教师") 
    public R save(@RequestBody LaoshiEntity laoshi, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(laoshiService.selectCount(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()))>0) {
            return R.error("老师账号已存在");
        }
        //ValidatorUtils.validateEntity(laoshi);
        //验证账号唯一性，否则返回错误信息
        LaoshiEntity u = laoshiService.selectOne(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	laoshi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		laoshi.setId(new Date().getTime());
        laoshiService.insert(laoshi);
        return R.ok().put("data",laoshi.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增教师")
    @RequestMapping("/add")
    public R add(@RequestBody LaoshiEntity laoshi, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(laoshiService.selectCount(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()))>0) {
            return R.error("老师账号已存在");
        }
        //ValidatorUtils.validateEntity(laoshi);
        //验证账号唯一性，否则返回错误信息
        LaoshiEntity u = laoshiService.selectOne(new EntityWrapper<LaoshiEntity>().eq("laoshizhanghao", laoshi.getLaoshizhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	laoshi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		laoshi.setId(new Date().getTime());
        laoshiService.insert(laoshi);
        return R.ok().put("data",laoshi.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改教师")
    public R update(@RequestBody LaoshiEntity laoshi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(laoshi);
        //验证字段唯一性，否则返回错误信息
        if(laoshiService.selectCount(new EntityWrapper<LaoshiEntity>().ne("id", laoshi.getId()).eq("laoshizhanghao", laoshi.getLaoshizhanghao()))>0) {
            return R.error("老师账号已存在");
        }
        //全部更新
        laoshiService.updateById(laoshi);
        if(null!=laoshi.getLaoshizhanghao())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(laoshi.getLaoshizhanghao());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", laoshi.getId()));
        }
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除教师")
    public R delete(@RequestBody Long[] ids){
        laoshiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
