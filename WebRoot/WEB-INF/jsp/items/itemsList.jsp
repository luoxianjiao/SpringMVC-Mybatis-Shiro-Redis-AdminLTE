<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../../resources/js/jquery-1.9.1.min.js"></script>
<title>查询商品列表</title>
</head>
<body> 
 
<%-- <script type="text/javascript">
	document.ready(function() {
		alert(document.getElementById("path").value);
	});
</script> --%>
<form action="${pageContext.request.contextPath }/items/queryItem.action" method="post">
<%-- <form action="${pageContext.request.contextPath }/items/deleteItemsBatch.action" method="post"> --%>
<input type="hidden" id="path" value="${pageContext.request.contextPath }"/>
查询条件：
<table width="100%" border=1>
<tr>
<td>
	<input type="submit" value="查询"/> &nbsp;
	<a href="${pageContext.request.contextPath }/items/addItems.action"><input type="button" value="新增"  /></a>	
</td>
</tr>
</table>
商品列表：
<table width="100%" border=1>
<tr>
	<td>
		<a href="${pageContext.request.contextPath }/items/deleteItemsBatch.action?items_id="+$("input[name='items_id']").val()><input type="button" value="批量删除" id="deleteItems"/></a>
	</td>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>生产日期</td>
	<td>商品描述</td>
	<td>操作</td>
</tr>
<c:forEach items="${itemsList}" var="item">
<tr>
	<td><input type="checkbox" name="items_id" value="${item.id}"></td>
	<td>${item.name}</td>
	<td>${item.price}</td>
	<td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	<td>${item.detail}</td>
	
	<td>
		<a href="${pageContext.request.contextPath }/items/editItems.action?id=${item.id}">修改</a>
		&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath }/items/deleteItems.action?id=${item.id}" >删除</a>		
	</td>

</tr>
</c:forEach>

</table>
</form>
</body>

</html>