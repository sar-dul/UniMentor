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
    
    <!----======== CSS ======== -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/register.css">


    <title>Seeker Registration</title> 
</head>
<body style="background-image: url('${pageContext.request.contextPath}/resources/other/login-background.jpg');">
    <div class="container">
        <header>
            <h2>Register as a Seeker</h2>
            <a href="<%=contextPath + "/pages/register-mentor.jsp"%>"> 
	            <button class="registerBtn">   
	                <span class="btnText">Register As A Mentor ➡</span>
	            </button>
            </a>
        </header>
        <form action="${pageContext.request.contextPath}/registerseeker" method="post" enctype="multipart/form-data">
            <div class="form first">
                <div class="details personal">
                    <span class="title">Personal Details</span>

                    <div class="fields">
                        <div class="input-field">
                            <label>Full Name</label>
                            <input type="text" placeholder="Enter your name" name = "seeker_name" required>
                        </div>
                    
                        <div class="input-field">
                            <label>Username</label>
                            <input type="text" placeholder="Enter username" name = "seeker_username" required>
                        </div>

                        <div class="input-field">
                            <label>Email</label>
                            <input type="email" placeholder="Enter your email" name = "seeker_email" required>
                        </div>

                        <div class="input-field">
                            <label>Mobile Number</label>
                            <input type="text" placeholder="+XXX-XXXXXXXXXX" name = "seeker_phonenumber" required>
                        </div>

                        <div class="input-field">
                            <label>Password</label>
                            <input type="password" placeholder="Enter password" name = "seeker_password" required>
                        </div>

                        <div class="input-field">
                            <label>Confirm Password</label>
                            <input type="password" placeholder="Retype Password" name = "seeker_retypepassword" required>
                        </div>
                    </div>
                </div>

                <div class="details ID">

                    <div class="fields">
                        <div class="input-field">
                            <label>Location</label>
                            <input type="text" placeholder="Enter location" name = "seeker_location" required>
                        </div>

                        <div class="input-field">
                            <label>Education Level</label>
                            <select name="seeker_educationlevel" required>
							    <option value="" disabled selected>Select Education Level</option>
							    <option value="+2">+2</option>
							    <option value="Bachelors">Bachelors</option>
							    <option value="Masters">Masters</option>
							</select>

                        </div>

                        <div class="input-field">
                            <label>Profile Photo</label>
                            <input class="file" type="file" name = "seeker_profilephoto">
                        </div>
                      
                    </div>

                    <button class="registerBtn" type="submit">
                        <span class="btnText">Register</span>
                    </button>
                </div> 
            </div>
        </form>
        <div class="form-link">
            <span>Already have an account? <a href="${pageContext.request.contextPath}/pages/login.jsp">Login</a></span>
        </div>
        <br>
        <%
			String errMsg = (String) request.getAttribute(StringUtils.MESSAGE_ERROR);
			String successMsg = (String) request.getAttribute(StringUtils.MESSAGE_SUCCESS);
	
			if (errMsg != null) {
				
				// split error message in segments
				String[] errorSegments = errMsg.split("\\.");
			
				// displaying each message from error segments array
				for (String msg: errorSegments) {
					%>
					<p style="color: red;">
						<%out.println(msg);
					%><p><%
				}
			}
	
			if (successMsg != null) {
				// print
			%>
			<h3 style="color: green;">
				<%
				out.println(successMsg);
				%>
			</h3>
			<%}
		%>
    </div>
</body>
</html>