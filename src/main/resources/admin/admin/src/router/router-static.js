import Vue from 'vue';
//配置路由
import VueRouter from 'vue-router'
Vue.use(VueRouter);
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import pay from '@/views/pay'
import register from '@/views/register'
import center from '@/views/center'
import adminexam from '@/views/modules/exampaperlist/exam'
	import news from '@/views/modules/news/list'
	import aboutus from '@/views/modules/aboutus/list'
	import examfailrecord from '@/views/modules/examfailrecord/list'
	import ziyuanleixing from '@/views/modules/ziyuanleixing/list'
	import xuesheng from '@/views/modules/xuesheng/list'
	import examquestion from '@/views/modules/examquestion/list'
	import syslog from '@/views/modules/syslog/list'
	import laoshi from '@/views/modules/laoshi/list'
	import exampaper from '@/views/modules/exampaper/list'
	import discusskechengxinxi from '@/views/modules/discusskechengxinxi/list'
	import kechengxinxi from '@/views/modules/kechengxinxi/list'
	import buzhizuoye from '@/views/modules/buzhizuoye/list'
	import discussxuexiziyuan from '@/views/modules/discussxuexiziyuan/list'
	import kechengfenlei from '@/views/modules/kechengfenlei/list'
	import chengjixinxi from '@/views/modules/chengjixinxi/list'
	import tongzhigonggao from '@/views/modules/tongzhigonggao/list'
	import tijiaozuoye from '@/views/modules/tijiaozuoye/list'
	import exampaperlist from '@/views/modules/exampaperlist/list'
	import examquestionbank from '@/views/modules/examquestionbank/list'
	import config from '@/views/modules/config/list'
	import examrecord from '@/views/modules/examrecord/list'
	import xuankexinxi from '@/views/modules/xuankexinxi/list'
	import xuexiziyuan from '@/views/modules/xuexiziyuan/list'
	import newstype from '@/views/modules/newstype/list'


//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		// 这里不设置值，是把main作为默认页面
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/pay',
		name: '支付',
		component: pay,
		meta: {icon:'', title:'pay'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}
	,{
		path: '/news',
		name: '公告信息',
		component: news
	}
	,{
		path: '/aboutus',
		name: '关于我们',
		component: aboutus
	}
	,{
		path: '/examfailrecord',
		name: '错题本',
		component: examfailrecord
	}
	,{
		path: '/ziyuanleixing',
		name: '资源类型',
		component: ziyuanleixing
	}
	,{
		path: '/xuesheng',
		name: '学生',
		component: xuesheng
	}
	,{
		path: '/examquestion',
		name: '在线测试管理',
		component: examquestion
	}
	,{
		path: '/syslog',
		name: '系统日志',
		component: syslog
	}
	,{
		path: '/laoshi',
		name: '教师',
		component: laoshi
	}
	,{
		path: '/exampaper',
		name: '在线测试管理',
		component: exampaper
	}
	,{
		path: '/discusskechengxinxi',
		name: '课程信息评论',
		component: discusskechengxinxi
	}
	,{
		path: '/kechengxinxi',
		name: '课程信息',
		component: kechengxinxi
	}
	,{
		path: '/buzhizuoye',
		name: '布置作业',
		component: buzhizuoye
	}
	,{
		path: '/discussxuexiziyuan',
		name: '学习资源评论',
		component: discussxuexiziyuan
	}
	,{
		path: '/kechengfenlei',
		name: '课程分类',
		component: kechengfenlei
	}
	,{
		path: '/chengjixinxi',
		name: '成绩信息',
		component: chengjixinxi
	}
	,{
		path: '/tongzhigonggao',
		name: '通知公告',
		component: tongzhigonggao
	}
	,{
		path: '/tijiaozuoye',
		name: '提交作业',
		component: tijiaozuoye
	}
	,{
		path: '/exampaperlist',
		name: '在线测试列表',
		component: exampaperlist
	}
	,{
		path: '/examquestionbank',
		name: '在线测试库管理',
		component: examquestionbank
	}
	,{
		path: '/config',
		name: '轮播图管理',
		component: config
	}
	,{
		path: '/examrecord',
		name: '在线测试记录',
		component: examrecord
	}
	,{
		path: '/xuankexinxi',
		name: '选课信息',
		component: xuankexinxi
	}
	,{
		path: '/xuexiziyuan',
		name: '学习资源',
		component: xuexiziyuan
	}
	,{
		path: '/newstype',
		name: '公告信息分类',
		component: newstype
	}
	]
	},
	{
		path: '/adminexam',
		name: 'adminexam',
		component: adminexam,
		meta: {icon:'', title:'adminexam'}
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	/*hash模式改为history*/
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
