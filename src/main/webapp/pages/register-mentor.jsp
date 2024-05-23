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


    <title>Mentor Registration</title> 
</head>
<body style="background-image: url('${pageContext.request.contextPath}/resources/other/login-background.jpg');">
    <div class="container">
        <header>
            <h2>Register as a Mentor</h2>
            <a href="<%=contextPath + "/pages/register-seeker.jsp"%>">
            	<button class="registerBtn"> 
                <span class="btnText">Register As A Seeker ➡</span>      
            </button>
            </a>
        </header>
        <form action="${pageContext.request.contextPath}/registermentor" method="post" enctype="multipart/form-data">
            <div class="form first">
                <div class="details personal">
                    <span class="title">Personal Details</span>

                    <div class="fields">
                        <div class="input-field">
                            <label>Full Name</label>
                            <input type="text" placeholder="Enter your name" name = "mentor_name" required>
                        </div>

                        <div class="input-field">
                            <label>Username</label>
                            <input type="text" placeholder="Enter username" name = "mentor_username" required>
                        </div>

                        <div class="input-field">
                            <label>Email</label>
                            <input type="email" placeholder="Enter your email" name = "mentor_email" required>
                        </div>

                        <div class="input-field">
                            <label>Mobile Number</label>
                            <input type="text" placeholder="+XXX-XXXXXXXXXX" name = "mentor_phonenumber" required>
                        </div>

                        <div class="input-field">
                            <label>Password</label>
                            <input type="password" placeholder="Enter password" name = "mentor_password" required>
                        </div>

                        <div class="input-field">
                            <label>Confirm Password</label>
                            <input type="password" placeholder="Retype Password" name = "mentor_retypepassword" required>
                        </div>
                    </div>
                </div>

                <div class="details ID">
                    <span class="title">University Details</span>

                    <div class="fields">
                        <div class="input-field">
                            <label>University Name</label>
                            <input type="text" placeholder="Enter university name" name = "mentor_universityname" required>
                        </div>

                        <div class="input-field">
                            <label>University Country</label>
                            <input type="text" placeholder="Enter country" name = "mentor_universitycountry" required>
                        </div>

                        <div class="input-field">
                            <label>Major</label>
                            <input type="text" placeholder="Enter your major" name = "mentor_major" required>
                        </div>

                        <div class="input-field">
                            <label>Tuition Fee</label>
                            <input type="number" placeholder="Enter your fee ($)" name = "mentor_tuitionfee" required>
                        </div>

                        <div class="input-field">
                            <label>Scholarship</label>
                            <input type="text" placeholder="Enter scholarship (% or $)" name = "mentor_scholarship" required>
                        </div>
                        
                        <div class="input-field">
                            <label>Profile Photo</label>
                            <input class="file" type="file" name = "mentor_profilephoto">
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