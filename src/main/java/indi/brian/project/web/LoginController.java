package indi.brian.project.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import indi.brian.project.cons.CommonConstant;
import indi.brian.project.domain.User;
import indi.brian.project.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/doLogin")
	public ModelAndView login(HttpServletRequest request, User user) {
		User dbuser = userService.getUserByUserName(user.getUserName());
		ModelAndView view = new ModelAndView();
		view.setViewName("forward:/login.jsp");
		if (dbuser == null) {
			view.addObject("errorMsg", "用戶名不存在");
		} else if (!dbuser.getPassword().equals(user.getPassword())) {
			view.addObject("errorMsg", "用戶密碼不存在");
		} else if (dbuser.getLocked() == User.USER_LOCK) {
			view.addObject("errorMsg", "用戶已鎖定");
		} else {
			dbuser.setLastIp(request.getRemoteAddr());
			dbuser.setLastVisit(new Date());
			userService.loginSuccess(dbuser);
			setSessionUser(request, dbuser);
			String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);

			if (StringUtils.isEmpty(toUrl)) {
				toUrl = "/index.html";
			}
			view.setViewName("redirect:" + toUrl);
		}
		return view;
	}

	@RequestMapping("/doLogout")
	public String logout(HttpSession session) {
		session.removeAttribute(CommonConstant.USER_CONTEXT);
		return "forward:/index.jsp";
	}
}
