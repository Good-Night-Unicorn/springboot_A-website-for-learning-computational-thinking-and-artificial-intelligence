const base = {
    get() {
        return {
            url : "http://localhost:8080/springboot3270a91w/",
            name: "springboot3270a91w",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/springboot3270a91w/front/dist/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "基于SpringBoot的计算思维与人工智能学习网站设计与实现"
        } 
    }
}
export default base
