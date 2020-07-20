<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
<style>
	.show{
		display:black
	}
	.hidden{
		display:none
	}
</style>
</head>
<body>
	<a href="${pageContext.request.contextPath }/emp/toadd.do">添加</a>
<div id="did">
	<table border="1" id="tid" :class="flag2">
		<tr>
			<td>选择</td>
			<td>编号</td>
			<td>姓名</td>
			<td>性别</td>
			<td>头像</td>
			<td>生日</td>
			<td>部门</td>
			<td>操作</td>
		</tr>
		
		<tr v-for = "(e,index) in emps">
			<td><input type="checkbox" v-model="ids" :value="e.eid"></td>
			<td v-text="e.eid"></td>
			<td v-text="e.ename"></td>
			<td v-text="e.sex"></td>
			<td></td>
			<td v-text="format(e.bir)"></td>
			<td v-text="e.dept.dname"></td>
			<td><input type="button" @click="toupdate(index)" value="修改"></td>
		</tr>
	</table>
	<input type="button" @click="del" value="删除">
	<form action="" id="fid" :class="flag">
		<input type="hidden" v-model="emp.eid">
		姓名:<input type="text" v-model="emp.ename"><br>
		性别:<input type="radio" value="男" v-model="emp.sex">男
			<input type="radio" value="女" v-model="emp.sex">女<br>
		头像:<input type="file"><br>
		生日:<input type="date" v-model="emp.bir"><br>
		部门:<select v-model="emp.depid">
				<option v-for="dept in dlist" :value="dept.depid" v-text="dept.dname"></option>
			</select><br>
 		<input type="button" @click="updateE" value="修改">
	</form>
</div>
	<script type="text/javascript">
		var table = new Vue({
			el:"#did",
			data:{
				emps:[],
				emp:{},
				dlist:[],
				flag:'hidden',
				flag2:'show',
				ids:[]
			},
			created(){
				axios.post("${pageContext.request.contextPath }/emp/findAll.do?pageNum=1").then(function(res){
					table.emps = res.data.elist;
			
				});
				axios.post("${pageContext.request.contextPath }/emp/findDept.do").then(function(res){
					table.dlist = res.data;
				})
			},
			methods:{
				format(bir){
					var year = new Date(bir).getFullYear();
					var month1= new Date(bir).getMonth()+1;
					var month = month1<10?"0"+month1:month1;
					var date = new Date(bir).getDate()<10?"0"+new Date(bir).getDate():new Date(bir).getDate();
					return  year+"-"+month+"-"+date
				},
				toupdate(i){
					this.emp = this.emps[i];
					this.emp.bir = this.format(this.emp.bir);
					this.flag = "show";
					this.flag2 = "hidden";
				},
				updateE(){
					axios.post("${pageContext.request.contextPath }/emp/update.do",table.emp).then(function(res){
						if(res.data>0){
							this.flag = "show";
							this.flag2 = "hidden";
							location.href="${pageContext.request.contextPath }/emp/toshow.do"
						}
					})
				},
				del(){
					axios.post("${pageContext.request.contextPath }/emp/deleteAll.do",this.ids).then(function(res){
						if(res.data>0){
							location.href="${pageContext.request.contextPath }/emp/toshow.do"
						}
					})
				}
			}
		})
	</script>
	
	
	
	
	
	
</body>
</html>