<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	//Get the session and request objects
	HttpSession userSession = request.getSession();
	String currentUserType = (String) userSession.getAttribute("type");
	String currentUser = (String) userSession.getAttribute("username");
	String currentImage = (String) userSession.getAttribute("image");
    String contextPath = request.getContextPath();
%>
    

<div class="nav">
    <div class="logo">
    	<a href="<%=contextPath + "/index.jsp"%>">
    		<img src="<%=contextPath + "/resources/other/main-logo.png"%>" alt="Logo Image" >
    	</a>
         </div>
    <div class="hamburger">
        <div class="line1"></div>
        <div class="line2"></div>
        <div class="line3"></div>
    </div>
    <ul class="nav-links">
        <li><a href="<%
        				// Conditionally set the navbar text(service) based on user type
                        if ((currentUserType == "mentor") || (currentUserType == "seeker")) {
                            out.print(contextPath + StringUtils.PAGE_URL_WELCOME);
                        } else {
                            out.print(contextPath + StringUtils.SERVLET_URL_INDEX);
                        }
                     %>">Home</a></li>
        <li><a href="<%=contextPath + StringUtils.SERVLET_URL_SERVICES%>"><%
                        // Conditionally set the navbar text(service) based on user type
                        if (currentUserType == "mentor") {
                            out.print("My Services");
                        } else {
                            out.print("Services");
                        }
                    %></a></li>
        <li><a href="">About Us</a></li>
        
        <div class="nav-buttons">       
	  		<!-- Handling Buttons When Login/ Logout -->      
	        <% 
	        // if not logged in the display login and register buttons
	        if (currentUser == null) {
	        %>
	            <a href="<%=contextPath + StringUtils.PAGE_URL_LOGIN%>">
	            	<button class="login-button">Login</button>
	            </a>
	        
	            <a href="<%=contextPath + StringUtils.PAGE_URL_REGISTER_SEEKER%>">
	            	<button class="register-button">Register</button>
	            </a> 
	              
        	 <%
        	 // else display profile and logout button
        	 } else {
        	 %>
	        	<a href="<%=contextPath + StringUtils.SERVLET_URL_PROFILE%>">
	        		<img class="profile-button" src="<%=contextPath%>/resources/users/<%=currentImage%>"/>
				</a>
				<a href="<%=contextPath + StringUtils.SERVLET_URL_LOGOUT%>">
	            	<button class="logout-button">Logout</button>
	            </a>
            <% } %>
            
        </div>
        
         
     </ul>
</div>

    <script>
        const hamburger = document.querySelector(".hamburger");
        const navLinks = document.querySelector(".nav-links");
        const links = document.querySelectorAll(".nav-links li");

        hamburger.addEventListener('click', ()=>{
            //Animate Links
            navLinks.classList.toggle("open");
            links.forEach(link => {
                link.classList.toggle("fade");
            });

            //Hamburger Animation
            hamburger.classList.toggle("toggle");
        });
    </script>
</body>

    