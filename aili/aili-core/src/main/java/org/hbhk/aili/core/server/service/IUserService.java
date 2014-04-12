package org.hbhk.aili.core.server.service;

import org.hbhk.aili.core.share.domain.UserEntity;
import org.hbhk.aili.core.share.domain.security.UsersEntity;

public interface IUserService {

	UserEntity getUserByName(String username);

	int insertUser(UserEntity user);

	UsersEntity queryUsers(String username);

}
