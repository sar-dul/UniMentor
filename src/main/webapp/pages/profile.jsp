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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!----======== CSS ======== -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/profile.css">

    <title></title> 
</head>
<body>

    <div class="container">
        <div class="title">Your Profile</div>
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
			<p>
			<%
			}
			if (successMsg != null) {
				// print
			%>
			<p style="color: green;">
				<%
				out.println(successMsg);
				%>
			<o>
			<%}
		%>

        <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
	       	<div class="upload">
	           <img src="${pageContext.request.contextPath}/resources/users/${userProfile.profilePhotoUrl}" width = 100 height = 100 alt="">
	           <div class="round">
	             <input type="file" name="photo">
	             <i class="fa fa-camera">✍🏻️️</i>

	           </div>
	        </div>
	          <div class="user__details">
	            <div class="input__box">
	              <span class="details">Full Name</span>
	              <input type="text" placeholder="John Smith" name="name" value="${userProfile.name}" required>
	            </div>
	            <div class="input__box">
	              <span class="details">Username</span>
	              <input type="text" placeholder="johnWC98" name="update_username" value="${userProfile.username}" required>
	            </div>
	            <div class="input__box">
	              <span class="details">Email</span>
	              <input type="email" placeholder="johnsmith@hotmail.com" name="email" value="${userProfile.email}" required>
	            </div>
	            <div class="input__box">
	              <span class="details">Phone Number</span>
	              <input type="text" placeholder="+XXX-XXXXXXXXXX" name="phone" value="${userProfile.phone}" required>
	            </div>
	              <input type="hidden" name="password" value="${userProfile.password}" required>   
	          </div>
	          <div class="button">
	            <input type="submit" value="Update Profile">
	          </div>
        </form>
        <hr>
        <form action="${pageContext.request.contextPath}/updatepassword" method="post">
         	<div class="user__details">
	           	<div class="input__box">
	              <span class="details">Old Password</span>
	              <input type="password" placeholder="Enter previous password" name="old_password" required>
	            </div> 
	            <div class="input__box">
	              <span class="details">New Password</span>
	              <input type="password" placeholder="Enter new password" name="new_password" required>
	            </div>    
	              <div class="update-button">
		            <input type="submit" value="Update Password">
		         </div>
		     </div>
         </form>
        
        <hr>
        
        <div class="other-button">
          <a href="<%=contextPath + StringUtils.PAGE_URL_WELCOME%>"><input class="cancel-btn" value="Cancel"></a>
          <form id="deleteForm-${userProfile.username}" action="${pageContext.request.contextPath}/profile" method="post">
          	<input type="hidden" name="delete_username" value="${userProfile.username}" />				
            <button type="button" onclick="confirmDelete('${userProfile.username}')"><input type="button" class="delete-btn" value="Delete Account"></button>
          </form>
        </div>
      </div>
  
</body>

<script>
	function confirmDelete(userName) {
	    if (confirm("Are you sure you want to delete your profile?")) {
	        document.getElementById("deleteForm-" + userName).submit();
	    }
	}
</script>
</html>