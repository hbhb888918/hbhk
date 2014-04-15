package org.hbhk.aili.core.server.data;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
public class AiliSqlSessionDaoSupport extends SqlSessionDaoSupport {
	
	@Override
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated method stub
		super.setSqlSessionFactory(sqlSessionFactory);
	}

}
