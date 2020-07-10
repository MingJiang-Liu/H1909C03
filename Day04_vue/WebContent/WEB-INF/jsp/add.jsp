<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
</head>
<body>
	<form action="" id="fid">
		姓名:<input type="text" v-model="emp.ename"><br>
		性别:<input type="radio" value="男" v-model="emp.sex">男
			<input type="radio" value="女" v-model="emp.sex">女<br>
		头像:<input type="file"><br>
		生日:<input type="date" v-model="emp.bir"><br>
		部门:<select v-model="emp.depid">
				<option v-for="dept in dlist" :value="dept.depid" v-text="dept.dname"></option>
			</select><br>
		<input type="button" @click="addE" value="添加">
	</form>
	<script type="text/javascript">
		var form = new Vue({
			el:"#fid",
			data:{
				dlist:[],
				emp:{}
			},
			created(){
				axios.post("${pageContext.request.contextPath }/emp/findDept.do").then(function(res){
					form.dlist = res.data;
				})
			},
			methods:{
				addE(){
					axios.post("${pageContext.request.contextPath }/emp/add.do",form.emp).then(function(res){
						if(res.data>0){
							location.href="${pageContext.request.contextPath }/emp/toshow.do";
							
						}
					})
				}
			}
		});
	</script>
</body>
</html>