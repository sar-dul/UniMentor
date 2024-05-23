<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    	<meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title></title>
        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/welcome.css">
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/header.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/footer.css" />
                        
    </head>
    <body>
    	<jsp:include page="<%=StringUtils.PAGE_URL_HEADER%>" />
    	
    	
    	 <!-- Conditionally set the welcome page based on user type --> 
    	<%
	       if (currentUserType == "mentor") {%>
	           <section class="hero-section" style="background-image: url('${pageContext.request.contextPath}/resources/other/mentor.jpeg');">
		            <div class="content" style="color: black;">
		              <h2>Welcome Mentor<br><span style="color:darkcyan ; font-size:5rem;"><%out.print(currentUser);%></span></h2>
		              <p>
		                Add a service and start earning now.
		              </p>
		              <a href="<%=contextPath + StringUtils.SERVLET_URL_SERVICES%>"><button class="btn">Add Service</button></a>
		            </div>
		        </section>
 
	       <%} else { %> <%%>
	    	   <section class="hero-section" style="background-image: url('${pageContext.request.contextPath}/resources/other/seeker.jpeg');">
	            <div class="content">
	              <h2>Welcome Seeker<br><span style="color:lightpink ; font-size:5rem;"><%out.print(currentUser);%></span></h2>
	              <p>
	                Use a service and start your university journey.
	              </p>
	              <a href="<%=contextPath + StringUtils.SERVLET_URL_SERVICES%>"><button class="btn">View Services</button></a>
	            </div>
	        </section>
	       <% }%>
	       

		<jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
    </body>
</html>

