package indi.brian.project.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import indi.brian.project.cons.CommonConstant;
import indi.brian.project.domain.User;

public class ForumFilter implements Filter {

	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

	/**
	 * 不須登入即可查看的頁面
	 */
	private static final String[] INNEHERENT_ESCAPE_URIS = { "/index.jsp", "/index.html", "/login.jsp",
			"/login/doLogin.html", "/register.jsp", "/register.html", "/board/listBoardTopics-",
			"/board/listTopicPosts-" };

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE); // 一個請求只會進過濾器一次
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			User user = getSessionUser(httpRequest);

			if (user == null && !isURILogin(httpRequest.getRequestURI(), httpRequest)) {
				String toUrl = httpRequest.getRequestURL().toString();
				if (!StringUtils.isEmpty(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				httpRequest.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, toUrl);
				httpRequest.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			chain.doFilter(request, response);
		}
	}

	private boolean isURILogin(String requestURI, HttpServletRequest request) {
		if (request.getContextPath().equals(requestURI) || (request.getContextPath() + "/").equals(requestURI))
			return true;
		for (String uri : INNEHERENT_ESCAPE_URIS) {
			if (requestURI != null && requestURI.indexOf(uri) >= 0) {
				return true;
			}
		}
		return false;
	}

	private User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
