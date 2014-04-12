package org.hbhk.aili.core.server.service.impl;

import javax.annotation.Resource;

import org.hbhk.aili.core.server.dao.IUserDao;
import org.hbhk.aili.core.server.service.IUserService;
import org.hbhk.aili.core.share.domain.UserEntity;
import org.hbhk.aili.core.share.domain.security.UsersEntity;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserServiceImpl extends SqlSessionDaoSupport implements
		IUserService {

	@Resource
	private IUserDao userDao;

	public UserEntity getUserByName(String username) {

		UserEntity u = new UserEntity();
		u.setPassword("135246");
		u.setUsername(username);
		return u;
	}

	public int insertUser(UserEntity user) {
		int num = this.getSqlSession().insert("org.hbhk.insertUser", user);
		return num;
	}

	@Override
	public UsersEntity queryUsers(String username) {
		UsersEntity usersCache = userDao.queryUsers(username);
		return usersCache;
	}


	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
