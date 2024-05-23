package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.StringUtils;

public class AuthenticationFilter implements Filter {

	/**
	 * Initializes the filter.
	 * @param filterConfig The filter configuration.
	 * @throws ServletException If the initialization fails.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Filters incoming requests and performs authentication and authorization checks.
	 * @param request The ServletRequest object.
	 * @param response The ServletResponse object.
	 * @param chain The FilterChain object for invoking the next filter in the chain.
	 * @throws IOException If an I/O error occurs.
	 * @throws ServletException If the request processing fails.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Cast request and response objects to HttpServletRequest and HttpServletResponse for type safety
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    // Get the requested URI
	    String uri = req.getRequestURI();

	    // Allow access to static resources (CSS) and the index page without checking login
	    if (uri.endsWith(".css") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
	        chain.doFilter(request, response);
	        return;
	    }
	    
	    if(uri.endsWith("/")) {
			request.getRequestDispatcher(StringUtils.SERVLET_URL_INDEX).forward(request, response);
	        return;
    	}

	    // Separate flags for login, login/logout servlets, and register page/servlet for better readability
	    boolean isLoginPage = uri.endsWith(StringUtils.PAGE_URL_LOGIN);
	    boolean isLoginServlet = uri.endsWith(StringUtils.SERVLET_URL_LOGIN);
	    boolean isLogoutServlet = uri.endsWith(StringUtils.SERVLET_URL_LOGOUT);
	 
	    boolean isRegisterPage = (uri.endsWith(StringUtils.PAGE_URL_REGISTER_MENTOR) || uri.endsWith(StringUtils.PAGE_URL_REGISTER_SEEKER));
	    boolean isRegisterServlet = (uri.endsWith(StringUtils.SERVLET_URL_REGISTER_MENTOR) || uri.endsWith(StringUtils.SERVLET_URL_REGISTER_SEEKER));
	    
	    
	    // Separate flag for index page and servlet for better readability
	    boolean isIndexPage = uri.endsWith(StringUtils.URL_INDEX);
	    boolean isIndexServlet = uri.endsWith(StringUtils.SERVLET_URL_INDEX);
	    
	    // Separate flag for other pages for better readability
	    boolean isProfilePage = uri.endsWith(StringUtils.URL_PROFILE);
	    boolean isServicePage = uri.endsWith(StringUtils.URL_SERVICE);
	    boolean isHeaderPage = uri.endsWith(StringUtils.URL_HEADER);
	    boolean isFooterPage = uri.endsWith(StringUtils.URL_FOOTER);
	    		
	    // Check if a session exists and if the username attribute is set to determine login status
	    HttpSession session = req.getSession(false); // Don't create a new session if one doesn't exist
	    boolean isLoggedIn = session != null && session.getAttribute(StringUtils.USERNAME) != null;

	    //  FOR FOR NEW/LOGGED OUT USERS
	    if (!isLoggedIn) {
	    	
	    	// Redirect to login if trying to access a protected resource
	    	if (!(isLoginPage || isLoginServlet || isRegisterPage || isRegisterServlet || isIndexServlet || isIndexPage)) {
	    		res.sendRedirect(req.getContextPath() + StringUtils.PAGE_URL_LOGIN);
	    	}
	    	
	    	// Redirect to index servlet if trying to access index page
	    	else if (isIndexPage) {
	    		request.getRequestDispatcher(StringUtils.SERVLET_URL_INDEX).forward(request, response);
	    	}
	    	
	    	// Allow access to the requested resource 
	    	else {
		        chain.doFilter(request, response);
		    }
	    } 
	    
	    // FOR LOGGED IN USERS
	    else if (isLoggedIn) { 	    
	    	
	    	// Redirect to the welcome page if trying to access login page again
	    	if (isLoginPage || isRegisterPage || isHeaderPage || isFooterPage) {
	    		res.sendRedirect(req.getContextPath() + StringUtils.PAGE_URL_WELCOME);
	    	}
	    	
	    	// Redirect to index servlet if trying to access index page
	    	else if (isIndexPage) {
	    		request.getRequestDispatcher(StringUtils.SERVLET_URL_INDEX).forward(request, response);
	    	}
	    	
	    	// Redirect to services servlet if trying to access service page
	    	else if (isServicePage) {
	    		request.getRequestDispatcher(StringUtils.SERVLET_URL_SERVICES).forward(request, response);
	    	}
	    	
	    	// Redirect to services servlet if trying to access service page
	    	else if (isProfilePage) {
	    		request.getRequestDispatcher(StringUtils.SERVLET_URL_PROFILE).forward(request, response);
	    	}
	    	
	    	// Allow access to the requested resource 
	    	else {
		        chain.doFilter(request, response);
		    }
	    	
	    } else {
	        // Allow access to the requested resource if user is logged in or accessing unprotected resources
	        chain.doFilter(request, response);
	    }
	   
	}

	/**
	 * Cleans up resources used by the filter.
	 */
	@Override
	public void destroy() {
	}
}
