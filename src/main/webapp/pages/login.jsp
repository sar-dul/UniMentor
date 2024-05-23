<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title> Login </title>
        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/login.css">
                        
    </head>
    <body>
        <section class="container forms" style="background-image:url('${pageContext.request.contextPath}/resources/other/background.jpg');">
            <div class="form login">
                <div class="form-content">
                    <header>Login</header>
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="field input-field">
                            <input type="text" placeholder="Username" name="username" class="input" required>
                        </div>
                        <div class="field input-field">
                            <input type="password" placeholder="Password" name="password" class="password" required>
                        </div>
                        <div class="form-link">
                            <a href="#" class="forgot-pass">Forgot password?</a>
                        </div>
                        <div class="field button-field">
                            <button type="submit">Login</button>
                        </div>
                    </form>
                    <div class="form-link">
                        <span>Don't have an account? <a href="${pageContext.request.contextPath}/pages/register-mentor.jsp" class="link signup-link">Signup</a></span>
                    </div>
                </div>
                <br>
                <%
                String errMsg = (String) request.getAttribute(StringUtils.MESSAGE_ERROR);
				String successMsg = (String) request.getAttribute(StringUtils.MESSAGE_SUCCESS);
	
				if (errMsg != null) {
					// print
				%>
				<p style="color: red;">
					<%
					out.println(errMsg);
					%>
				</p>
				<%}%>
            </div>    
        </section>
    </body>
</html>