package indi.brian.project.dao;

import org.springframework.stereotype.Repository;

import indi.brian.project.domain.LoginLog;

@Repository
public class LoginLogDao extends BaseDao<LoginLog> {
	public void save(LoginLog loginLog) {
		this.getHibernateTemplate().save(loginLog);
	}
}
