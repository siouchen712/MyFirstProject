package indi.brian.project.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import indi.brian.project.domain.User;
import indi.brian.project.exception.UserExistException;
import indi.brian.project.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/doRegister")
	public ModelAndView register(HttpServletRequest request, User user) {
		ModelAndView view = new ModelAndView();
		view.setViewName("success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			view.addObject("errorMsg", "用戶名已存在，請改用其他名字");
			view.setViewName("forward:/register.jsp");
		}
		setSessionUser(request, user);
		return view;
	}
}
