export default {
	baseUrl: 'http://localhost:8080/springboot3270a91w/',
	name: '/springboot3270a91w',
	indexNav: [
		{
			name: '课程信息',
			url: '/index/kechengxinxi',
		},
		{
			name: '学习资源',
			url: '/index/xuexiziyuan',
		},
		{
			name: '在线测试',
			url: '/index/examPaper'
		}, 
		{
			name: '公告信息',
			url: '/index/news'
		},
	],
	cateList: [
		{
			name: '课程信息',
			refTable: 'kechengfenlei',
			refColumn: 'kechengfenlei',
		},
		{
			name: '公告信息',
			refTable: 'newstype',
			refColumn: 'typename',
		},
	]
}
