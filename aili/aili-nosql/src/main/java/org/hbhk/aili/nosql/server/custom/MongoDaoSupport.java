package org.hbhk.aili.nosql.server.custom;

import org.hbhk.aili.nosql.share.util.PaginationUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public abstract class MongoDaoSupport {

	private MongoDBConfig dbConfig;

	/**
	 * @Description:分页查询 建议使用这种分页
	 * @author 何波
	 * @param collection
	 * @param limit
	 * @param id
	 * @param queryID
	 * @return DBCursor
	 * @throws
	 * @date 2014年4月24日 下午4:28:43
	 */
	public DBCursor getDBCursor(String name, int limit, String id,
			String queryID) {
		DBCollection collection = dbConfig.getDBCollection(name);
		DBObject object = new BasicDBObject();
		object.put(id, new BasicDBObject("$gt", queryID));
		DBCursor cursor = collection.find(object)
				.sort(new BasicDBObject("id", 1)).limit(limit);
		return cursor;
	}

	public DBCursor getDBCursor(String name, int limit, int pageNo) {
		DBCollection collection = dbConfig.getDBCollection(name);
		int startIndex = PaginationUtil.getStartIndex(pageNo, limit);
		DBCursor cursor = collection.find().skip(startIndex).limit(limit);
		return cursor;
	}

	public MongoDBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(MongoDBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public DB getDb() {
		return dbConfig.getDb();
	}

	public DB getDb(String database) {
		return dbConfig.getDb(database);
	}

}
