import VueRouter from 'vue-router'
//引入组件
import Index from '../pages'
import Home from '../pages/home/home'
import Login from '../pages/login/login'
import Register from '../pages/register/register'
import Center from '../pages/center/center'
import ExamPaper from '../pages/exam/examPaper'
import Exam from '../pages/exam/exam'
import ExamList from '../pages/exam/examList'
import ExamRecord from '../pages/exam/examRecord'
import Storeup from '../pages/storeup/list'
import News from '../pages/news/news-list'
import NewsDetail from '../pages/news/news-detail'
import payList from '../pages/pay'

import xueshengList from '../pages/xuesheng/list'
import xueshengDetail from '../pages/xuesheng/detail'
import xueshengAdd from '../pages/xuesheng/add'
import laoshiList from '../pages/laoshi/list'
import laoshiDetail from '../pages/laoshi/detail'
import laoshiAdd from '../pages/laoshi/add'
import kechengfenleiList from '../pages/kechengfenlei/list'
import kechengfenleiDetail from '../pages/kechengfenlei/detail'
import kechengfenleiAdd from '../pages/kechengfenlei/add'
import kechengxinxiList from '../pages/kechengxinxi/list'
import kechengxinxiDetail from '../pages/kechengxinxi/detail'
import kechengxinxiAdd from '../pages/kechengxinxi/add'
import xuankexinxiList from '../pages/xuankexinxi/list'
import xuankexinxiDetail from '../pages/xuankexinxi/detail'
import xuankexinxiAdd from '../pages/xuankexinxi/add'
import buzhizuoyeList from '../pages/buzhizuoye/list'
import buzhizuoyeDetail from '../pages/buzhizuoye/detail'
import buzhizuoyeAdd from '../pages/buzhizuoye/add'
import tijiaozuoyeList from '../pages/tijiaozuoye/list'
import tijiaozuoyeDetail from '../pages/tijiaozuoye/detail'
import tijiaozuoyeAdd from '../pages/tijiaozuoye/add'
import chengjixinxiList from '../pages/chengjixinxi/list'
import chengjixinxiDetail from '../pages/chengjixinxi/detail'
import chengjixinxiAdd from '../pages/chengjixinxi/add'
import ziyuanleixingList from '../pages/ziyuanleixing/list'
import ziyuanleixingDetail from '../pages/ziyuanleixing/detail'
import ziyuanleixingAdd from '../pages/ziyuanleixing/add'
import xuexiziyuanList from '../pages/xuexiziyuan/list'
import xuexiziyuanDetail from '../pages/xuexiziyuan/detail'
import xuexiziyuanAdd from '../pages/xuexiziyuan/add'
import tongzhigonggaoList from '../pages/tongzhigonggao/list'
import tongzhigonggaoDetail from '../pages/tongzhigonggao/detail'
import tongzhigonggaoAdd from '../pages/tongzhigonggao/add'
import chatmessageList from '../pages/chatmessage/list'
import chatmessageDetail from '../pages/chatmessage/detail'
import chatmessageAdd from '../pages/chatmessage/add'
import friendList from '../pages/friend/list'
import friendDetail from '../pages/friend/detail'
import friendAdd from '../pages/friend/add'
import syslogList from '../pages/syslog/list'
import syslogDetail from '../pages/syslog/detail'
import syslogAdd from '../pages/syslog/add'
import newstypeList from '../pages/newstype/list'
import newstypeDetail from '../pages/newstype/detail'
import newstypeAdd from '../pages/newstype/add'
import aboutusList from '../pages/aboutus/list'
import aboutusDetail from '../pages/aboutus/detail'
import aboutusAdd from '../pages/aboutus/add'
import discusskechengxinxiList from '../pages/discusskechengxinxi/list'
import discusskechengxinxiDetail from '../pages/discusskechengxinxi/detail'
import discusskechengxinxiAdd from '../pages/discusskechengxinxi/add'
import discussxuexiziyuanList from '../pages/discussxuexiziyuan/list'
import discussxuexiziyuanDetail from '../pages/discussxuexiziyuan/detail'
import discussxuexiziyuanAdd from '../pages/discussxuexiziyuan/add'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

//配置路由
export default new VueRouter({
	routes:[
		{
      path: '/',
      redirect: '/index/home'
    },
		{
			path: '/index',
			component: Index,
			children:[
				{
					path: 'home',
					component: Home
				},
				{
					path: 'center',
					component: Center,
				},
				{
					path: 'pay',
					component: payList,
				},
				{
					path: 'examPaper',
					component: ExamPaper
				},
				{
					path: 'examList',
					component:ExamList
				},
				{
					path: 'examRecord/:type',
					component:ExamRecord
				},
				{
					path: 'storeup',
					component: Storeup
				},
				{
					path: 'news',
					component: News
				},
				{
					path: 'newsDetail',
					component: NewsDetail
				},
				{
					path: 'xuesheng',
					component: xueshengList
				},
				{
					path: 'xueshengDetail',
					component: xueshengDetail
				},
				{
					path: 'xueshengAdd',
					component: xueshengAdd
				},
				{
					path: 'laoshi',
					component: laoshiList
				},
				{
					path: 'laoshiDetail',
					component: laoshiDetail
				},
				{
					path: 'laoshiAdd',
					component: laoshiAdd
				},
				{
					path: 'kechengfenlei',
					component: kechengfenleiList
				},
				{
					path: 'kechengfenleiDetail',
					component: kechengfenleiDetail
				},
				{
					path: 'kechengfenleiAdd',
					component: kechengfenleiAdd
				},
				{
					path: 'kechengxinxi',
					component: kechengxinxiList
				},
				{
					path: 'kechengxinxiDetail',
					component: kechengxinxiDetail
				},
				{
					path: 'kechengxinxiAdd',
					component: kechengxinxiAdd
				},
				{
					path: 'xuankexinxi',
					component: xuankexinxiList
				},
				{
					path: 'xuankexinxiDetail',
					component: xuankexinxiDetail
				},
				{
					path: 'xuankexinxiAdd',
					component: xuankexinxiAdd
				},
				{
					path: 'buzhizuoye',
					component: buzhizuoyeList
				},
				{
					path: 'buzhizuoyeDetail',
					component: buzhizuoyeDetail
				},
				{
					path: 'buzhizuoyeAdd',
					component: buzhizuoyeAdd
				},
				{
					path: 'tijiaozuoye',
					component: tijiaozuoyeList
				},
				{
					path: 'tijiaozuoyeDetail',
					component: tijiaozuoyeDetail
				},
				{
					path: 'tijiaozuoyeAdd',
					component: tijiaozuoyeAdd
				},
				{
					path: 'chengjixinxi',
					component: chengjixinxiList
				},
				{
					path: 'chengjixinxiDetail',
					component: chengjixinxiDetail
				},
				{
					path: 'chengjixinxiAdd',
					component: chengjixinxiAdd
				},
				{
					path: 'ziyuanleixing',
					component: ziyuanleixingList
				},
				{
					path: 'ziyuanleixingDetail',
					component: ziyuanleixingDetail
				},
				{
					path: 'ziyuanleixingAdd',
					component: ziyuanleixingAdd
				},
				{
					path: 'xuexiziyuan',
					component: xuexiziyuanList
				},
				{
					path: 'xuexiziyuanDetail',
					component: xuexiziyuanDetail
				},
				{
					path: 'xuexiziyuanAdd',
					component: xuexiziyuanAdd
				},
				{
					path: 'tongzhigonggao',
					component: tongzhigonggaoList
				},
				{
					path: 'tongzhigonggaoDetail',
					component: tongzhigonggaoDetail
				},
				{
					path: 'tongzhigonggaoAdd',
					component: tongzhigonggaoAdd
				},
				{
					path: 'chatmessage',
					component: chatmessageList
				},
				{
					path: 'chatmessageDetail',
					component: chatmessageDetail
				},
				{
					path: 'chatmessageAdd',
					component: chatmessageAdd
				},
				{
					path: 'friend',
					component: friendList
				},
				{
					path: 'friendDetail',
					component: friendDetail
				},
				{
					path: 'friendAdd',
					component: friendAdd
				},
				{
					path: 'syslog',
					component: syslogList
				},
				{
					path: 'syslogDetail',
					component: syslogDetail
				},
				{
					path: 'syslogAdd',
					component: syslogAdd
				},
				{
					path: 'newstype',
					component: newstypeList
				},
				{
					path: 'newstypeDetail',
					component: newstypeDetail
				},
				{
					path: 'newstypeAdd',
					component: newstypeAdd
				},
				{
					path: 'aboutus',
					component: aboutusList
				},
				{
					path: 'aboutusDetail',
					component: aboutusDetail
				},
				{
					path: 'aboutusAdd',
					component: aboutusAdd
				},
				{
					path: 'discusskechengxinxi',
					component: discusskechengxinxiList
				},
				{
					path: 'discusskechengxinxiDetail',
					component: discusskechengxinxiDetail
				},
				{
					path: 'discusskechengxinxiAdd',
					component: discusskechengxinxiAdd
				},
				{
					path: 'discussxuexiziyuan',
					component: discussxuexiziyuanList
				},
				{
					path: 'discussxuexiziyuanDetail',
					component: discussxuexiziyuanDetail
				},
				{
					path: 'discussxuexiziyuanAdd',
					component: discussxuexiziyuanAdd
				},
			]
		},
		{
			path: '/login',
			component: Login
		},
		{
			path: '/register',
			component: Register
		},
		{
			path: '/exam',
			component: Exam
		}
	]
})
