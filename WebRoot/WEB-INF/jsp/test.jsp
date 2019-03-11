<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp测试页面</title>

</head>
<body>
<%
	String aa = (String) request.getAttribute("aa");  //request  作用域为一次请求
	String bb = (String) response.getHeader("bb");   //response
	String userId = (String) session.getAttribute("userId");//session 范围在一个会话中
	application.setAttribute("server", "tomcat");//application 整个应用中有效，直到服务器关闭
	
	out.print("输出流");//out 输出流
	out.print(config.getServletName());  // config
	pageContext.getAttribute("aa");  //pageContext 对象的作用是取得任何范围的参数，通过它可以获取 JSP页面的out、request、reponse、session、application 等对象
	page.equals("a");//代表jsp本身，只在jsp页面内才是合法的
	
	// exception   exception 对象的作用是显示异常信息，只有在包含 isErrorPage="true" 的页面中才可以被使用，在一般的JSP页面中使用该对象将无法编译JSP文件 
	 
	
%>
<%-- <%String bb = (String) response.getHeader("bb"); %> --%>

</body>
</html>