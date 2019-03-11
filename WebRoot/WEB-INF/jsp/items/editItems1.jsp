<!-- 
	自定义包装类型pojo参数绑定 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../../resources/js/jquery-1.9.1.min.js"></script>
<title>修改商品信息</title>

</head>
<body> 

<form id="itemForm" action="${pageContext.request.contextPath }/items/editItemsSubmit_1.action" enctype="multipart/form-data" method="post" >
<input type="hidden" name="itemsCustom.id" value="${itemsCustom.id }"/>
修改商品信息：
	<table width="100%" border=1>
		<tr>
			<td>商品名称</td>
			<td><input type="text" name="itemsCustom.name" value="${itemsCustom.name }"/></td>
		</tr>
		<tr>
			<td>商品价格</td>
			<td><input type="text" name="itemsCustom.price" value="${itemsCustom.price }"/></td>
		</tr>
		<tr>
			<td>商品生产日期</td>
			<td><input type="text" name="itemsCustom.createtime" value="<fmt:formatDate value="${itemsCustom.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
		</tr>
		<tr>
			<td>商品图片</td>
			<td>
				<%-- <c:if test="${itemsCustom.pic !=null && itemsCustom.pic != ""}"> --%>
				<c:if test="${not empty itemsCustom.pic}">
					<img src="/pic/${itemsCustom.pic}" width=100 height=100/>
					<br/>
				</c:if>
				<input type="file"  name="items_pic" id="image" value="d:/stuff/${itemsCustom.pic}"/> 
			</td>
		</tr>
		<tr>
			<td>商品简介</td>
			<td>
			<textarea rows="3" cols="30" name="itemsCustom.detail">${itemsCustom.detail }</textarea>
			</td>
		</tr>
		<tr>
		<td colspan="2" align="center">
			<input type="submit" value="提交"/>
			<input type="button" id="requestJson" value="requestJson"/>
		</td>
		</tr>
	</table>

</form>
</body>
<script type="text/javascript">
	$(function() {
		$("#requestJson").click(function() {
/* 			requestJson(); */
/* 			requestJson_1(); */
			requestJson_2();
		});
	});
	/*json自动绑定到springmvc中dto参数（加RequestBody注解）时，需要加contentType:"application/json;charset=utf-8"
	,且json要严格的的格式，注意引号 */	
	function requestJson() {
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/items/requestJson.action",
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			async:false,
			data:'{"name":"手机","price":1021,"detail":"222"}',
			success:function(data) {
				console.log(data);
			},
			error:function(XMLHttpRequest, textStatus,errorThrown) {
				console.log("异常");
			}
		});
	}
	
	/*不让方法形参直接转成dto，而让request 一个个参数接收，此时不需要加contentType，
	且json格式最外层不需要引号*/
	function requestJson_1() {
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/items/requestJson_1.action",
			dataType:"json",			
			async:false,
			data:{name:"手机",price:1021,detail:"222"},
			success:function(data) {
				console.log(data);
			},
			error:function(XMLHttpRequest, textStatus,errorThrown) {
				console.log("异常");
			}
		});
	}
	/*请求为key/value型, 不需要指定contentType,ajax默认请求就是key/value*/
	function requestJson_2() {
		var detail = "不错啊";
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/items/requestJson_2.action",
			dataType:"json",			
			async:false,
			data:"name=手机&price=99"+"&detail="+detail,
			success:function(data) {
				console.log(data);
			},
			error:function(XMLHttpRequest, textStatus,errorThrown) {
				console.log("异常");
			}
		});
	}
</script>
</html>