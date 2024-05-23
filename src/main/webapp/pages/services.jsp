<%@page import="utils.StringUtils"%>
<%@page import="model.ServiceModel"%>
<%@page import="java.util.ArrayList"%>
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
    <title>
    	<%
	       // Conditionally set the services header based on user type
	       if (currentUserType == "mentor") {
	           out.print("My Services");
	       } else {
	           out.print("All Services");
	       }
	   %>
    </title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/services.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/header.css" />
  	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/footer.css" />
      
</head>
	

<body>
	<jsp:include page="<%=StringUtils.PAGE_URL_HEADER%>" />

	<section class="search">
	    <!-- search bar starts -->
	    <div class="search-bar">
	        <!-- Dropdown start -->
	        <div class="dropdown">
	          <div id="drop-text" class="dropdown-text">
	            <span id="span">Service Name</span>
	          </div>
	          <ul id="list" class="dropdown-list">
	            <li class="dropdown-list-item">Service Name</li>
	            <li class="dropdown-list-item">University</li>
	            <li class="dropdown-list-item">Country</li>
	            
	          </ul>
	        </div>
	        <!-- Dropdown ends -->
	  
	        <!-- Search box input start -->
	        <div class="search-box">
	          <form>
		          <input type="text" id="search-input" placeholder="Search by Service Name..." name="search"/>
		          <button type="submit" style="height: 30px; margin-right: 11px;"><img src="<%=contextPath%>/resources/other/search.png"></button>
		          <a href="<%=contextPath + StringUtils.SERVLET_URL_SERVICES%>"><img src="<%=contextPath%>/resources/other/clear.png" style="height: 30px;"></a>  
	          </form>
	        </div>
	        
	         <!-- Search box input ends -->
	    </div>
	    <!-- search bar ends -->
	    
	</section>
	

    <!-- all jobs section starts -->

    <section class="jobs-container">    
        <h1 class="heading">
        <%
            // Conditionally set the services header based on user type
            if (currentUserType == "mentor") {
                out.print("My Services");
            } else {
                out.print("All Services");
            }
        %>
        </h1>
        <div class="box-container">
        			

	        <!-- All services are displayed. (Only for seeker) -->
	        <c:if test="${isSeeker}">
	        	<c:if test="${empty serviceLists}">
					<p>No services found.</p>
				</c:if>
			
	        	<c:forEach var="service" items="${serviceLists}">
					<div class="box">
		                <div class="company">
			                <c:forEach var="mentor" items="${mentorMap}">
	                        	<c:if test="${service.mentorUsername eq mentor.value.username}">
			                    <img src="${pageContext.request.contextPath}/resources/users/${mentor.value.profilePhotoUrl}" alt="image of face">
			                    	<div>
		                        		<h3>${mentor.value.name}</h3>
				                        <p>${mentor.value.universityName}</p>
				                        <p>(${mentor.value.scholarship} Scholarship)</p>
				                    </div>
		                        </c:if>
	                        </c:forEach>
	                    </div>
		                <h3 class="job-title">${service.title}</h3>
		                <p class="description"><span>${service.description}</span></p>
		                <div class="tags">
		                    <p><span> 💲${service.price}</span></p>
		                    <p><span>${service.rating} ⭐</span></p>
		                    <p>${service.users} Users</p>
		                </div>
		                <div class="flex-btn">
		                    <a href="" class="btn">view details</a>
		                    <button type="submit" class="far fa-heart" name="like">♡</button>
		                </div>
		            </div>
				</c:forEach>
	        </c:if>

			<!-- Only their services are displayed. (Only for mentor) -->
			<c:if test="${isMentor}">
				<c:if test="${empty mentorServiceLists}">
					<p>You have not created a service.</p>
				</c:if>
	        	<c:forEach var="service" items="${mentorServiceLists}">
					<div class="box">
		                <div class="company">
			                <c:forEach var="mentor" items="${mentorMap}">
	                        	<c:if test="${service.mentorUsername eq mentor.value.username}">
			                    <img src="${pageContext.request.contextPath}/resources/users/${mentor.value.profilePhotoUrl}" alt="">
			                    		<div>
			                        		<h3>${mentor.value.name}</h3>
					                        <p>${mentor.value.universityName}</p>
					                        <p>(${mentor.value.scholarship} Scholarship)</p>
					                    </div>
		                       	 </c:if>
	                        </c:forEach>
	                    </div>
		                <h3 class="job-title">${service.title}</h3>
		                <p class="description"><span>${service.description}</span></p>
		                <div class="tags">
		                    <p><span> 💲${service.price}</span></p>
		                    <p><span>${service.rating} ⭐</span></p>
		                    <p>${service.users} Users</p>
		                </div>
		                <div class="flex-btn">
		                	<form action="${pageContext.request.contextPath}/modify" method="get">
			                	<button type="submit"><img src="<%=contextPath + "/resources/other/update.png"%>"></button>
			                	<input type="hidden" name="update_service_id" value="${service.id}">
		                	</form>
		                    <form id="deleteForm-${service.id}" action="${pageContext.request.contextPath}/modify" method="post">
		                    	<button type="button" onclick="confirmDelete('${service.title}', '${service.id}')"><img src="<%=contextPath + "/resources/other/delete.png"%>"></button>
			                    <input type="hidden" name="delete_service_id" value="${service.id}">
		                    </form>       
		                </div>
		            </div>
				</c:forEach>
				
				<!-- Add button (only for mentor) -->
				<a href="<%=contextPath + StringUtils.PAGE_URL_ADD%>">
	                <div class="box-add">
	                    <h3>Add Service</h3>
	                    <p>⨁</p>            
	                </div>
	            </a>
	        </c:if>
        </div>
    </section>
    <!-- all jobs section ends -->

    <script>
        let dropdownBtnText = document.getElementById("drop-text");
        let span = document.getElementById("span");
        let icon = document.getElementById("icon");
        let list = document.getElementById("list");
        let input = document.getElementById("search-input");
        let listItems = document.querySelectorAll(".dropdown-list-item");

        dropdownBtnText.onclick = function () {
        list.classList.toggle("show");
        icon.style.rotate = "-180deg";
        };

        window.onclick = function (e) {
        if (
            e.target.id !== "drop-text" &&
            e.target.id !== "icon" &&
            e.target.id !== "span"
        ) {
            list.classList.remove("show");
            icon.style.rotate = "0deg";
        }
        };

        for (item of listItems) {
        item.onclick = function (e) {
            span.innerText = e.target.innerText;
            if (e.target.innerText == "Everything") {
            input.placeholder = "Search Anything...";
            } else {
            input.placeholder = "Search in " + e.target.innerText + "...";
            }
        };
        }
        
        function confirmDelete(serviceName, id) {
    		if (confirm("Are you sure you want to delete this service: " + serviceName
    				+ "?")) {
    			document.getElementById("deleteForm-" + id).submit();
    		}
    	}
    </script>
	
	<jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
</body>
</html>