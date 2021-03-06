package org.hbhk.aili.job.server;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hbhk.aili.job.share.pojo.QuartzEntity;
import org.hbhk.aili.job.share.pojo.QuartzMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class QuartzDao {
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	}



	/**
	 * 查找所有的定时任务
	 * @return
	 */
	public List<QuartzEntity> selectAllQuartJob() {
		String sql = "select QRTZ_JOB_DETAILS.JOB_NAME JOB_NAME ,QRTZ_TRIGGERS.TRIGGER_NAME TRIGGER_NAME"
				+ ",NEXT_FIRE_TIME,PREV_FIRE_TIME,TRIGGER_STATE,TRIGGER_TYPE,START_TIME,END_TIME"
				+ ",QRTZ_JOB_DETAILS.DESCRIPTION  DESCRIPTION from QRTZ_TRIGGERS inner join QRTZ_JOB_DETAILS "
				+ " on QRTZ_TRIGGERS.JOB_NAME = QRTZ_JOB_DETAILS.JOB_NAME order by start_time";
		
		return jdbcTemplate.query(sql,new HashMap<String, String>(), new QuartzMapper());
	}
}