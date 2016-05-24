/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.mapper.entity;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 数据库表对应的列
 *
 * @author liuzh
 */
public class EntityColumn {
	private EntityTable table;
	private String property;
	private String column;
	private Class<?> javaType;
	private JdbcType jdbcType;
	private Class<? extends TypeHandler<?>> typeHandler;
	private String sequenceName;
	private boolean id = false;
	private boolean uuid = false;
	private boolean snowflake = false;
	private boolean identity = false;
	private String generator;
	// 排序
	private String orderBy;
	// 可插入
	private boolean insertable = true;
	// 可更新
	private boolean updatable = true;

	public EntityColumn() {
	}

	public EntityColumn(EntityTable table) {
		this.table = table;
	}

	public EntityTable getTable() {
		return table;
	}

	public void setTable(EntityTable table) {
		this.table = table;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public Class<? extends TypeHandler<?>> getTypeHandler() {
		return typeHandler;
	}

	public void setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
		this.typeHandler = typeHandler;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public boolean isId() {
		return id;
	}

	public void setId(boolean id) {
		this.id = id;
	}

	public boolean isUuid() {
		return uuid;
	}

	public void setUuid(boolean uuid) {
		this.uuid = uuid;
	}

	public boolean isIdentity() {
		return identity;
	}

	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isSnowflake() {
		return snowflake;
	}

	public void setSnowflake(boolean snowflake) {
		this.snowflake = snowflake;
	}

	/**
	 * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @return
	 */
	public String getColumnEqualsHolder() {
		return getColumnEqualsHolder(null);
	}

	/**
	 * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @return
	 */
	public String getColumnEqualsHolder(String entityName) {
		return this.column + " = " + getColumnHolder(entityName);
	}

	/**
	 * 返回格式如:#{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @return
	 */
	public String getColumnHolder() {
		return getColumnHolder(null);
	}

	/**
	 * 返回格式如:#{entityName.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @return
	 */
	public String getColumnHolder(String entityName) {
		return getColumnHolder(entityName, null);
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 *
	 * @param entityName
	 * @param suffix
	 * @return
	 */
	public String getColumnHolder(String entityName, String suffix) {
		return getColumnHolder(entityName, null, null);
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 * ,
	 *
	 * @param entityName
	 * @param suffix
	 * @return
	 */
	public String getColumnHolderWithComma(String entityName, String suffix) {
		return getColumnHolder(entityName, suffix, ",");
	}

	/**
	 * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
	 * +separator
	 *
	 * @param entityName
	 * @param suffix
	 * @param separator
	 * @return
	 */
	public String getColumnHolder(String entityName, String suffix, String separator) {
		StringBuffer sb = new StringBuffer("#{");
		if (StringUtil.isNotEmpty(entityName)) {
			sb.append(entityName);
			sb.append(".");
		}
		sb.append(this.property);
		if (StringUtil.isNotEmpty(suffix)) {
			sb.append(suffix);
		}
		if (this.jdbcType != null) {
			sb.append(",jdbcType=");
			sb.append(this.jdbcType.toString());
		} else if (this.typeHandler != null) {
			sb.append(",typeHandler=");
			sb.append(this.typeHandler.getCanonicalName());
		} else if (!this.javaType.isArray()) {// 当类型为数组时，不设置javaType#103
			sb.append(",javaType=");
			sb.append(javaType.getCanonicalName());
		}
		sb.append("}");
		if (StringUtil.isNotEmpty(separator)) {
			sb.append(separator);
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((generator == null) ? 0 : generator.hashCode());
		result = prime * result + (id ? 1231 : 1237);
		result = prime * result + (identity ? 1231 : 1237);
		result = prime * result + (insertable ? 1231 : 1237);
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((jdbcType == null) ? 0 : jdbcType.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((sequenceName == null) ? 0 : sequenceName.hashCode());
		result = prime * result + (snowflake ? 1231 : 1237);
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		result = prime * result + ((typeHandler == null) ? 0 : typeHandler.hashCode());
		result = prime * result + (updatable ? 1231 : 1237);
		result = prime * result + (uuid ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityColumn other = (EntityColumn) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (generator == null) {
			if (other.generator != null)
				return false;
		} else if (!generator.equals(other.generator))
			return false;
		if (id != other.id)
			return false;
		if (identity != other.identity)
			return false;
		if (insertable != other.insertable)
			return false;
		if (javaType == null) {
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType))
			return false;
		if (jdbcType != other.jdbcType)
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (sequenceName == null) {
			if (other.sequenceName != null)
				return false;
		} else if (!sequenceName.equals(other.sequenceName))
			return false;
		if (snowflake != other.snowflake)
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		if (typeHandler == null) {
			if (other.typeHandler != null)
				return false;
		} else if (!typeHandler.equals(other.typeHandler))
			return false;
		if (updatable != other.updatable)
			return false;
		if (uuid != other.uuid)
			return false;
		return true;
	}

}
