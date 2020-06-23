package indi.brian.project.web;

import javax.servlet.http.HttpServletRequest;

import indi.brian.project.cons.CommonConstant;
import indi.brian.project.domain.User;

public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";

	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}

	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}

	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		return request.getContextPath() + url;
	}
}
