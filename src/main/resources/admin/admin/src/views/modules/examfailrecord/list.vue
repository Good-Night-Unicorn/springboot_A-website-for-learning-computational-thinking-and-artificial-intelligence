<template>
	<div class="main-content" :style='{"padding":"20px 10px 20px 10px","fontSize":"15px"}'>
		<!-- 列表页 -->
		<template v-if="!showFlag">
			<el-form :style='{"margin":"0 10px 20px"}' :inline="true" :model="searchForm" class="center-form-pv">
				<el-row :style='{"alignItems":"center","padding":"10px","display":"flex"}'>
					<div :style='{"alignItems":"center","margin":"0 10px 0 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","color":"#666","display":"inline-block","width":"120px","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}' class="item-label">在线测试</label>
						<el-input v-model="searchForm.papername" placeholder="在线测试名称" clearable></el-input>
					</div>
					<div :style='{"alignItems":"center","margin":"0 10px 0 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","color":"#666","display":"inline-block","width":"120px","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}' class="item-label">在线测试</label>
						<el-input v-model="searchForm.questionname" placeholder="在线测试名称" clearable></el-input>
					</div>
					<el-button class="search" :style='{"border":"0","cursor":"pointer","padding":"0 10px","color":"#fff","borderRadius":"5px","background":"#097499","width":"auto","fontSize":"16px","height":"34px"}' type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing07" :style='{"margin":"0 2px","fontSize":"16px","color":"#fff","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"0 10px","fontSize":"14px","color":"#000"}'>
				<el-table
					:stripe='true'
					:style='{"width":"100%","borderColor":"#eee","fontSize":"inherit","borderRadius":"20px 20px 0 0","borderStyle":"solid","borderWidth":"1px 0 0 1px"}'
					:data="dataList"
					:border='true'
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler"
					style="width: 100%;"
				>
					<el-table-column :resizable='true' type="selection" header-align="center" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="username" header-align="center" align="center" label="姓名"></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="papername"
						header-align="center"
						align="center"
						label="在线测试"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="questionname"
						header-align="center"
						align="center"
						label="在线测试名称"
					>
						<template slot-scope="scope">
							<div class="ql-snow ql-editor" v-html="scope.row.questionname"></div>
						</template>
					</el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="type"
						header-align="center"
						align="center"
						label="在线测试类型"
					>
						<template slot-scope="scope">
							<el-tag type="success" v-if="scope.row.type==0">单选题</el-tag>
							<el-tag type="warning" v-if="scope.row.type==1">多选题</el-tag>
							<el-tag type="info" v-if="scope.row.type==2">判断题</el-tag>
							<el-tag type="primary" v-if="scope.row.type==3">填空题</el-tag>
							<el-tag type="danger" v-if="scope.row.type==4">主观题</el-tag>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="score" header-align="center" align="center" label="分值"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="right" header-align="center" align="center" label="正确答案"></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="myanswer"
						header-align="center"
						align="center"
						label="考生答案"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="analysis"
						header-align="center"
						align="center"
						label="在线测试分析"
					></el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="addtime"
						header-align="center"
						align="center"
						width="170"
						label="在线测试时间"
					></el-table-column>
				</el-table>
			</div>

			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:total="totalPage"
				:layout="layouts.join()"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="false"
				:style='{"padding":"0","boxShadow":" 0px 2px 4px 0px rgba(129,129,129,0.3)","margin":"20px auto 0","whiteSpace":"nowrap","color":"#333","textAlign":"center","display":"flex","justifyContent":"center","borderRadius":"90px","background":"#fff","width":"375px","fontSize":"inherit","position":"relative","fontWeight":"500","height":"50px"}'
			></el-pagination>
		</template>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				layouts: ["prev","next","pager"],
				searchForm: {
					key: ""
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: false,
				user: {}
			};
		},
		mounted() {
			this.$http({
				url: `${this.$storage.get("sessionTable")}/session`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.user = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
			this.init();
			this.getDataList();
		},
		components: {},
		methods: {
			init() {},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true;
				var params = {
					page: this.pageIndex,
					limit: this.pageSize,
					sort: "id",
					myscore: 0,
					userid: this.id,
					ismark: 1
				};
				if (this.searchForm.papername) {
					params["papername"] = `%${this.searchForm.papername}%`;
				}
				if (this.searchForm.questionname) {
					params["questionname"] = `%${this.searchForm.questionname}%`;
				}
				this.$http({
					url: this.$api.examrecordpage,
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let arr = []
						for(let x in data.data.list){
							data.data.list[x].questionname = data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
							if(data.data.list[x].type==0||data.data.list[x].type==2){
								arr = JSON.parse(data.data.list[x].options)
								for(let i in arr){
									if(data.data.list[x].answer == arr[i].code){
										data.data.list[x].right = arr[i].text
									}
								}
							}else if(data.data.list[x].type==1){
								arr = JSON.parse(data.data.list[x].options)
								data.data.list[x].answer.split(',').forEach(item=>{
									for(let i in arr){
										if (item == arr[i].code) {
											if (data.data.list[x].right) {
												data.data.list[x].right += ','
												data.data.list[x].right = data.data.list[x].right + arr[i].text
											}else{
												data.data.list[x].right = arr[i].text
											}
										}
									}
								})
							}else if(data.data.list[x].type==3){
								data.data.list[x].right = data.data.list[x].answer
							}else if(data.data.list[x].type==4){
								data.data.list[x].right = '略'
							}
						}
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListLoading = false;
				});
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 多选
			selectionChangeHandler(val) {
				this.dataListSelections = val;
			}
		}
	};
</script>
<style lang="scss" scoped>
	.form-content {
		background: transparent;
	}
	.table-content {
		background: transparent;
	}

	
	// form
	.center-form-pv .el-input {
				width: 100%;
			}
	
	.center-form-pv .el-input /deep/ .el-input__inner {
				border: 1px dashed #BFBFBF;
				border-radius: 0px;
				padding: 0 12px;
				color: #666;
				width: 170px;
				font-size: 16px;
				height: 34px;
			}
	
	// table
	.el-table /deep/ .el-table__header-wrapper thead {
				color: #000000;
				font-weight: 500;
				width: 100%;
			}
	
	.el-table /deep/ .el-table__header-wrapper thead tr {
				background: #fff;
			}
	
	.el-table /deep/ .el-table__header-wrapper thead tr th {
				padding: 4px 0;
				background: #F3F3F3;
				border-color: #eee;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
	.el-table /deep/ .el-table__header-wrapper thead tr th .cell {
				padding: 0 0 0 5px;
				word-wrap: normal;
				color: #666;
				white-space: normal;
				font-weight: bold;
				display: flex;
				vertical-align: middle;
				font-size: 13px;
				line-height: 24px;
				text-overflow: ellipsis;
				word-break: break-all;
				width: 100%;
				align-items: center;
				position: relative;
				min-width: 120px;
			}
	
	
	.el-table /deep/ .el-table__body-wrapper tbody {
				width: 100%;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr {
				background: #fff;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 4px 0;
				color: #9E9E9E;
				background: #fff;
				font-size: 12px;
				border-color: #9E9E9E;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
		.el-table /deep/ .el-table__body-wrapper tbody tr.el-table__row--striped td {
		background: #f8f8f8;
	}
		
	.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
				padding: 4px 0;
				color: #333;
				background: #f0f0f0;
				border-color: #eee;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 4px 0;
				color: #9E9E9E;
				background: #fff;
				font-size: 12px;
				border-color: #9E9E9E;
				border-width: 0 0px 1px 0;
				border-style: solid;
				text-align: left;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
				padding: 0 0 0 5px;
				overflow: hidden;
				word-break: break-all;
				white-space: normal;
				font-size: inherit;
				line-height: 24px;
				text-overflow: ellipsis;
			}
	
	// pagination
	.main-content .el-pagination /deep/ .el-pagination__total {
				margin: 0 10px 0 0;
				color: #666;
				font-weight: 400;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 48px;
				height: 48px;
			}
	
	.main-content .el-pagination /deep/ .btn-prev {
				border: none;
				padding: 0 5px 0 0;
				margin: 0;
				color: #5E5E5E;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				border-color: #C2C2C2;
				line-height: auto;
				border-radius: 2px;
				left: 20px;
				background: none;
				border-width: 0 1px 0 0;
				position: absolute;
				border-style: solid;
				min-width: 35px;
				height: 50px;
			}
	
	.main-content .el-pagination /deep/ .btn-next {
				border: none;
				padding: 0  0 0 5px;
				margin: 0;
				color: #5E5E5E;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				border-color: #C2C2C2;
				line-height: auto;
				right: 20px;
				border-radius: 2px;
				background: none;
				border-width: 0 0 0 1px;
				position: absolute;
				border-style: solid;
				min-width: 35px;
				height: 50px;
			}
	
	.main-content .el-pagination /deep/ .btn-prev:disabled {
				border: none;
				padding: 0 5px 0 0;
				margin: 0;
				color: #5E5E5E;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: auto;
				border-radius: 2px;
				background: none;
				position: absolute;
				min-width: 35px;
				height: 50px;
			}
	
	.main-content .el-pagination /deep/ .btn-next:disabled {
				border: none;
				padding: 0 5px 0 0;
				margin: 0;
				color: #5E5E5E;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: auto;
				border-radius: 2px;
				background: none;
				position: absolute;
				min-width: 35px;
				height: 50px;
			}
	
	.main-content .el-pagination /deep/ .el-pager {
				padding: 0;
				margin: 0;
				display: block;
				vertical-align: top;
				order: 2;
			}
	
	.main-content .el-pagination /deep/ .el-pager .number {
				cursor: pointer;
				border-radius: 2px;
				padding: 0 20px;
				margin: -6px -5px 0;
				color: #000;
				background: none;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: 62px;
				text-align: center;
				height: 56px;
			}
	
	.main-content .el-pagination /deep/ .el-pager .number:hover {
				cursor: default;
				border-radius: 2px;
				padding: 0 20px;
				margin: -6px -5px 0;
				color: #097499;
				background: none;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: 62px;
				text-align: center;
				height: 56px;
			}
	
	.main-content .el-pagination /deep/ .el-pager .number.active {
				cursor: default;
				border-radius: 2px;
				padding: 0 20px;
				margin: -6px -5px 0;
				color: #097499;
				background: none;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: 62px;
				text-align: center;
				height: 56px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes {
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: 48px;
				height: 48px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input {
				margin: 0 5px;
				width: 100px;
				position: relative;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__inner {
				border: 1px solid #DCDFE6;
				cursor: pointer;
				padding: 0 25px 0 8px;
				color: #606266;
				display: inline-block;
				font-size: 16px;
				line-height: 28px;
				border-radius: 3px;
				outline: 0;
				background: #FFF;
				width: 100%;
				text-align: center;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input span.el-input__suffix {
				top: 0;
				position: absolute;
				right: 0;
				height: 100%;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
				cursor: pointer;
				color: #C0C4CC;
				width: 25px;
				font-size: 14px;
				line-height: 48px;
				text-align: center;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump {
				margin: 0 0 0 24px;
				color: #606266;
				display: inline-block;
				vertical-align: top;
				font-size: 16px;
				line-height: 48px;
				height: 48px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input {
				border-radius: 3px;
				padding: 0 2px;
				margin: 0 2px;
				display: inline-block;
				width: 50px;
				font-size: 14px;
				line-height: 18px;
				position: relative;
				text-align: center;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input .el-input__inner {
				border: 1px solid #DCDFE6;
				cursor: pointer;
				padding: 0 3px;
				color: #606266;
				display: inline-block;
				font-size: 16px;
				line-height: 28px;
				border-radius: 3px;
				outline: 0;
				background: #FFF;
				width: 100%;
				text-align: center;
				height: 28px;
			}
	
	.center-form-pv .search {
				border: 0;
				cursor: pointer;
				border-radius: 5px;
				padding: 0 10px;
				color: #fff;
				background: #097499;
				width: auto;
				font-size: 16px;
				height: 34px;
			}
	
	.center-form-pv .search:hover {
				opacity: 0.8;
			}
</style>
