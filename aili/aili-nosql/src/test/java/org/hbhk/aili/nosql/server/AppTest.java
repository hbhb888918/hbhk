package org.hbhk.aili.nosql.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class AppTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring.xml");
		MongoTemplate mongoOps = (MongoTemplate) context
				.getBean("mongoTemplate");
		Posts p = new Posts();
		p.setContent("dddddddddddddd");
		p.setId("iiiiiiiiiiiii");
		p.setTitle("tttttt");
		// mongoOps.insert(p);
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.where("id").is("iiiiiiiiiiiii");
		query.addCriteria(criteria);
		Posts p1 = mongoOps.findOne(query, Posts.class);

	}

}