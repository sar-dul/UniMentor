<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/index.css">
  <link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/header.css" />
  <link rel="stylesheet" type="text/css" href="<%=contextPath%>/stylesheets/footer.css" /> 
</head>

<body>
	<jsp:include page="<%=StringUtils.PAGE_URL_HEADER%>" />
	
  <!-- Split Screen
  =============================== -->
  <div class="content">
    <div class="split left" style="background-image:url('${pageContext.request.contextPath}/resources/other/mentor.jpeg');">
      <div class="text">
        <p class="subtitle">become a</p>
        <h1 class="title">mentor</h1>
        <p class="desc">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Alias sint voluptatem tempora ipsam
          expedita?</p>
        <a href="${pageContext.request.contextPath}/pages/register-mentor.jsp">
        	<button class="button">Join</button>
        </a>
      </div>
    </div>
    <div class="split right" style="background-image:url('${pageContext.request.contextPath}/resources/other/seeker.jpeg');">
      <div class="text">
        <p class="subtitle">become a</p>
        <h1 class="title">seeker</h1>
        <p class="desc">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Alias sint voluptatem tempora ipsam
          expedita?</p>
        <a href="${pageContext.request.contextPath}/pages/register-seeker.jsp">
        	<button class="button">Join</button>
        </a>
      </div>
    </div>
  </div>

    <!-- Most Popular (Card Slider)
  =============================== -->

  <h2 class="section__header">OUR TOP MENTORS</h2>
  <div class="wrapper">
    <i id="left"> < </i>
    <ul class="carousel">
    	<c:forEach var="mentor" items="${topMentorsLists}">
	      <li class="card">
	        <div class="img"><img src="${pageContext.request.contextPath}/resources/users/${mentor.profilePhotoUrl}" alt="img" draggable="false"></div>
	        <h2>${mentor.name}</h2>
	        <span>${mentor.universityName}</span>
	      </li>
      	</c:forEach>
    </ul>
    <i id="right"> > </i>
  </div>


  <!-- Why Join US ?
  =============================== -->
  <section class="section__container">
      <h2 class="section__header">WHY JOIN US ?</h2>
      <div class="join__image">
        <img src="<%=contextPath + "/resources/other/students.jpeg"%>" alt="Join" />
        <div class="join__grid">
          <div class="join__card">
            <span><i class="ri-user-star-fill"></i></span>
            <div class="join__card__content">
              <h4>Personal Trainer</h4>
              <p>Unlock your potential with our expert Personal Trainers.</p>
            </div>
          </div>
          <div class="join__card">
            <span><i class="ri-vidicon-fill"></i></span>
            <div class="join__card__content">
              <h4>Practice Sessions</h4>
              <p>Elevate your fitness with practice sessions.</p>
            </div>
          </div>
          <div class="join__card">
            <span><i class="ri-building-line"></i></span>
            <div class="join__card__content">
              <h4>Good Management</h4>
              <p>Supportive management, for your fitness success.</p>
            </div>
          </div>
        </div>
      </div>
    </section>

  <!-- Join
  =============================== -->

  
  <section class="section__container">
    <h2 class="section__header">JOIN TODAY</h2>
    <div class="register__grid">
      <div class="register__content">
        <h4>Abroad Study Services at your Fingertips</h4>
        <div class="register__btn">
        <a href="${pageContext.request.contextPath}/pages/register-seeker.jsp">
          <button class="button">Join Now</button>
        </a>
        </div>
      </div>
      <div class="register__image">
        <img src="<%=contextPath + "/resources/other/join.png"%>" alt="register" />
      </div>
    </div>
  </section>


  <!-- js -->
  <script>
    // ========= split screen javascript ========== //
    const content = document.querySelector(".content");
    const left = document.querySelector(".left");
    const right = document.querySelector(".right");

    left.addEventListener('mouseenter', () => {
    content.classList.add('hover-left');
    })

    left.addEventListener('mouseleave', () => {
    content.classList.remove('hover-left');
    })

    right.addEventListener('mouseenter', () => {
    content.classList.add('hover-right');
    })

    right.addEventListener('mouseleave', () => {
    content.classList.remove('hover-right');
    })

    // ========= image slider javascript ========== //
    const wrapper = document.querySelector(".wrapper");
    const carousel = document.querySelector(".carousel");
    const firstCardWidth = carousel.querySelector(".card").offsetWidth;
    const arrowBtns = document.querySelectorAll(".wrapper i");
    const carouselChildrens = [...carousel.children];

    let isDragging = false, isAutoPlay = true, startX, startScrollLeft, timeoutId;

    // Get the number of cards that can fit in the carousel at once
    let cardPerView = Math.round(carousel.offsetWidth / firstCardWidth);

    // Insert copies of the last few cards to beginning of carousel for infinite scrolling
    carouselChildrens.slice(-cardPerView).reverse().forEach(card => {
        carousel.insertAdjacentHTML("afterbegin", card.outerHTML);
    });

    // Insert copies of the first few cards to end of carousel for infinite scrolling
    carouselChildrens.slice(0, cardPerView).forEach(card => {
        carousel.insertAdjacentHTML("beforeend", card.outerHTML);
    });

    // Scroll the carousel at appropriate postition to hide first few duplicate cards on Firefox
    carousel.classList.add("no-transition");
    carousel.scrollLeft = carousel.offsetWidth;
    carousel.classList.remove("no-transition");

    // Add event listeners for the arrow buttons to scroll the carousel left and right
    arrowBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            carousel.scrollLeft += btn.id == "left" ? -firstCardWidth : firstCardWidth;
        });
    });

    const dragStart = (e) => {
        isDragging = true;
        carousel.classList.add("dragging");
        // Records the initial cursor and scroll position of the carousel
        startX = e.pageX;
        startScrollLeft = carousel.scrollLeft;
    }

    const dragging = (e) => {
        if(!isDragging) return; // if isDragging is false return from here
        // Updates the scroll position of the carousel based on the cursor movement
        carousel.scrollLeft = startScrollLeft - (e.pageX - startX);
    }

    const dragStop = () => {
        isDragging = false;
        carousel.classList.remove("dragging");
    }

    const infiniteScroll = () => {
        // If the carousel is at the beginning, scroll to the end
        if(carousel.scrollLeft === 0) {
            carousel.classList.add("no-transition");
            carousel.scrollLeft = carousel.scrollWidth - (2 * carousel.offsetWidth);
            carousel.classList.remove("no-transition");
        }
        // If the carousel is at the end, scroll to the beginning
        else if(Math.ceil(carousel.scrollLeft) === carousel.scrollWidth - carousel.offsetWidth) {
            carousel.classList.add("no-transition");
            carousel.scrollLeft = carousel.offsetWidth;
            carousel.classList.remove("no-transition");
        }

        // Clear existing timeout & start autoplay if mouse is not hovering over carousel
        clearTimeout(timeoutId);
        if(!wrapper.matches(":hover")) autoPlay();
    }

    const autoPlay = () => {
        if(window.innerWidth < 800 || !isAutoPlay) return; // Return if window is smaller than 800 or isAutoPlay is false
        // Autoplay the carousel after every 2500 ms
        timeoutId = setTimeout(() => carousel.scrollLeft += firstCardWidth, 2500);
    }
    autoPlay();

    carousel.addEventListener("mousedown", dragStart);
    carousel.addEventListener("mousemove", dragging);
    document.addEventListener("mouseup", dragStop);
    carousel.addEventListener("scroll", infiniteScroll);
    wrapper.addEventListener("mouseenter", () => clearTimeout(timeoutId));
    wrapper.addEventListener("mouseleave", autoPlay);

  </script>
  
  <jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
</body>

</html>