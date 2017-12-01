<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Upload portal</title>
	<link href="css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div id="loginForm">
		<h1>Upload Portal Login</h1>
		<form action="LoginServlet" method="POST">
			<input type="text" name="user" placeholder="Username" required="true" /><br>
			<input type="password" name="pass" placeholder="Password" required="true" /><br>
			<input type="submit" value="Login" />
		</form>
		<%
			if(session.getAttribute("error") != null) {
		         out.println(session.getAttribute("error"));
			}
		%>
</div>
</body>
</html>


