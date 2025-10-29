<template>
	<div class="main-content" :style='{"padding":"20px 10px 20px 10px","fontSize":"15px"}'>
		<!-- 列表页 -->
		<template v-if="!showFlag">
			<el-form :style='{"margin":"0 10px 20px"}' :inline="true" :model="searchForm" class="center-form-pv">
				<el-row :style='{"alignItems":"center","padding":"10px","display":"flex"}'>
					<div :style='{"alignItems":"center","margin":"0 10px 0 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","color":"#666","display":"inline-block","width":"120px","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}' class="item-label">在线测试</label>
						<el-input v-model="searchForm.name" placeholder="在线测试名称" clearable></el-input>
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
					<el-table-column :resizable='true' :sortable='true' prop="name" header-align="center" align="center" sortable label="在线测试">
						<template slot-scope="scope">{{scope.row.name}}</template>
					</el-table-column>
					<el-table-column
						:resizable='true' :sortable='true'
						prop="time"
						header-align="center"
						align="center"
						sortable
						label="在线测试时长/分钟"
					>
						<template slot-scope="scope">{{scope.row.time}}</template>
					</el-table-column>
					<el-table-column
						header-align="center"
						align="center"
						width="150"
						label="操作"
					>
						<template slot-scope="scope">
							<el-button class="view" :style='{"border":"0px solid #157ed2","cursor":"pointer","padding":"0 0px","margin":"0 5px 5px 0","color":"#00B2FF","backgroundImage":"url(http://codegen.caihongy.cn/20241007/e8174ac3e8e146629367830e5fbb5b5b.png)","minWidth":"60px","borderRadius":"2px","width":"auto","fontSize":"14px","backgroundSize":"100% 100%","backgroundRepeat":"no-repeat","height":"51px"}' type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xihuan" :style='{"margin":"0 2px","fontSize":"14px","color":"#fff","display":"none","height":"40px"}'></span>
								在线测试
							</el-button>
						</template>
					</el-table-column>
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
					status: "1"
				};
				if (this.searchForm.name) {
					params["name"] = `%${this.searchForm.name}%`;
				}
				this.$http({
					url: this.$api.exampaperpage,
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
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
			},
			// 添加/修改
			addOrUpdateHandler(id) {
				this.$http({
					url: `examrecord/deleteRecords?userid=${this.user.id}&paperid=${id}`,
					method: "post",
					data: {}
				}).then(({ data }) => {});
				this.$router.push({
					name: "adminexam",
					params: {
						id: id
					}
				});
			},
			// 删除
			deleteHandler(id) {
				var ids = id
					? [Number(id)]
					: this.dataListSelections.map(item => {
						return Number(item.id);
					});
				this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(() => {
					this.$http({
						url: this.$api.exampaperdelete,
						method: "post",
						data: ids
					}).then(({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "操作成功",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
						} else {
							this.$message.error(data.msg);
						}
					});
				});
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
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view {
				border: 0px solid #157ed2;
				cursor: pointer;
				padding: 0 0px;
				margin: 0 5px 5px 0;
				background-size: 100% 100%;
				color: #00B2FF;
				font-size: 14px;
				border-radius: 2px;
				background-repeat: no-repeat;
				width: auto;
				background-image: url(http://codegen.caihongy.cn/20241007/e8174ac3e8e146629367830e5fbb5b5b.png);
				min-width: 60px;
				height: 51px;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view:hover {
				opacity: 0.8;
			}
	
	// list one
	.one .list1-view {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 15px;
				margin: 0 5px 5px 0;
				outline: none;
				color: #fff;
				background: #157ed2;
				width: auto;
				font-size: 14px;
				height: 32px;
			}
	
	.one .list1-view:hover {
				opacity: 0.8;
			}
</style>
