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
	<table border="1" id="tid">
		<tr>
			
			<td>编号</td>
			<td>姓名</td>
			<td>性别</td>
			<td>头像</td>
			<td>生日</td>
			<td>部门</td>
		</tr>
		
		<tr v-for = "e in emps">
			
			<td v-text="e.eid"></td>
			<td v-text="e.ename"></td>
			<td v-text="e.sex"></td>
			<td></td>
			<td v-text="format(e.bir)"></td>
			<td v-text="e.dept.dname"></td>
		</tr>
	</table>
	<script type="text/javascript">
		var table = new Vue({
			el:"#tid",
			data:{
				emps:[]
			},
			created(){
				axios.post("${pageContext.request.contextPath }/emp/findAll.do").then(function(res){
					var elist = res.data;
					table.emps = elist; 
				})
			},
			methods:{
				format(bir){
					var year = new Date(bir).getFullYear();
					var month = new Date(bir).getMonth()+1;
					var date = new Date(bir).getDate();
					return year+"-"+month+"-"+date
				}
			}
		})
	</script>
	
	
	
	
	
	
</body>
</html>