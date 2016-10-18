<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<link href="css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="css/easyui/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="css/easyui/icon.css" />
<script type="text/javascript" src="js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	
</script>
<c:set var="cxt" value="${pageContext.request.contextPath}" />
</head>
<body>
	<%
		Exception ex = (Exception)request.getAttribute("exception");
	%>
	<H2>
		Exception:
		<%=ex.getMessage()%></H2>
	<P />
	点击<a href="${cxt}/login.jsp">返回</a>首页
</body>
</html>
