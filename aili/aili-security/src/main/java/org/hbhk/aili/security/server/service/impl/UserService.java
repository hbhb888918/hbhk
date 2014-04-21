package org.hbhk.aili.security.server.service.impl;

import javax.annotation.Resource;

import org.hbhk.aili.security.server.context.UserContext;
import org.hbhk.aili.security.server.dao.IUserDao;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.pojo.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Resource
	private IUserDao userDao;

	@Override
	public UserInfo getMe(String username) {
		return null;
	}

	@Override
	public boolean login(String username, String password) {
		UserInfo userInfo = userDao.login(username, password);
		if (userInfo != null) {
			UserContext.setCurrentUser(userInfo);
			UserContext.setCurrentUserName(username);
			return true;
		}
		return false;
	}

	@Override
	public boolean validate(String url) {
		return false;
	}

	@Override
	public boolean validate(String username, String url) {
		return false;
	}

	@Override
	public void logout() {
		UserContext.remove();
	}

}