/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jdbc.datasource.embedded;

/**
 * <p>嵌入式数据库类型</p>
 * A supported embedded database type.
 *
 * @author Keith Donald
 * @author Oliver Gierke
 * @since 3.0
 */
public enum EmbeddedDatabaseType {

	/** The <a href="https://hsqldb.org">Hypersonic</a> Embedded Java SQL Database. */
	HSQL,

	// h2 是一种内存数据库，使用java编写，下方有它的网站地址，可以点进去查看下
	/** The <a href="https://h2database.com">H2</a> Embedded Java SQL Database Engine. */
	H2,

	/** The <a href="https://db.apache.org/derby">Apache Derby</a> Embedded SQL Database. */
	DERBY

}
