<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	//Get the session and request objects
	HttpSession userSession = request.getSession();
	String currentUserType = (String) userSession.getAttribute("type");
	String currentUser = (String) userSession.getAttribute("username");
    String contextPath = request.getContextPath();
%>  
    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title></title>
        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/modify.css">
                        
    </head>
    
    <body>
        <div class="container">
            
            <form action="${pageContext.request.contextPath}/addservice" method="post">
                <div class="header">
                    <a href="<%=contextPath + StringUtils.SERVLET_URL_SERVICES%>" class="return">< Cancel</a>
                    <h1>Add Service</h1>
                    <div></div>
                </div>
                <ul>
                    <li>
                        <label for="name"><span>Title</span></label>
                        <input type="text" id="name" name="title" required>
                    </li>
                    <li>
                        <label for="msg"><span>Description</span></label>
                        <textarea rows="4" cols="50" name="description" required></textarea>
                    </li>
                    <li>
                        <label for="mail"><span>Price</span></label>
                        <input type="number" id="mail" name="price" required>
                    </li>
                    <li>
                        <input type="submit" value="Add">
                    </li>
                </ul>
            </form>
        </div>
    </body>

</html>