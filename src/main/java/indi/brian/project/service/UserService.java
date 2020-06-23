package indi.brian.project.service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.brian.project.dao.LoginLogDao;
import indi.brian.project.dao.UserDao;
import indi.brian.project.domain.LoginLog;
import indi.brian.project.domain.User;
import indi.brian.project.exception.UserExistException;

@Service
public class UserService {
	private UserDao userDao;
	private LoginLogDao loginLogDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}

	public void update(User user) {
		userDao.update(user);
	}
		
	public void register(User user) throws UserExistException {
		User u = userDao.getUserByUserName(user.getUserName());
		if (u != null) {
			throw new UserExistException("用戶已存在");
		} else {
			user.setCredit(100);
			user.setUserType(1);
			user.setLastIp("127.0.0.1");
			user.setLastVisit(new Date());
			userDao.save(user);
		}
	}
	
	public User getUserById(int userId) {
		return userDao.get(userId);
	}
	
	public void lockUser(String userName) {
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_LOCK);
		userDao.update(user);
	}
	
	public void unlockUser(String userName) {
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.update(user);
	}
	
	public User getUserByUserName(String userName){		
        return userDao.getUserByUserName(userName);
    }
	
	public List<User> queryUserByUserName(String userName){
		return userDao.queryUserByUserName(userName);
	}
	
	public List<User> getAllUsers(){
		return userDao.loadAll();
	}
	
	public void loginSuccess(User user) {
		user.setCredit( 5 + user.getCredit());
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(new Date());
        userDao.update(user);
        loginLogDao.save(loginLog);
	}	
}
