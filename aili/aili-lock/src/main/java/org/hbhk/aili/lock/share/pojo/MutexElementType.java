/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/define/MutexElementType.java
 * 
 * FILE NAME        	: MutexElementType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.define
 * FILE    NAME: MutexElementType.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package org.hbhk.aili.lock.share.pojo;

/**
 * 
 * 锁定业务类型
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:053990,date:2014-2-12 上午11:36:16,content:定义业务锁类型对象 </p>
 * @author 053990
 * @date 2014-2-12 上午11:36:16
 * @since
 * @version
 */
public class MutexElementType {

	/**
	 * 业务锁前缀
	 */
	private String prefix;

	/**
	 * 业务锁名称
	 */
	private String name;

	public MutexElementType(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	/**
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
