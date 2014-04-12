package org.hbhk.aili.core.server.dao;

import org.hbhk.aili.core.share.domain.security.UsersEntity;

public interface IUserDao {

	 void userTest();
	 UsersEntity queryUsers(String username);
}
