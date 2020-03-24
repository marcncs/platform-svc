package com.winsafe.hbm.entity.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.winsafe.hbm.util.Encrypt;

public class EncryptedMobile implements UserType {

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 自定义类型的完全复制方法,构造返回对象 1. 当nullSafeGet方法调用之后，我们获得了自定义数据对象，在向用户返回自定义数据之前
	 * deepCopy方法被调用，它将根据自定义数据对象构造一个完全拷贝，把拷贝返还给客户使用。 2.此时我们就得到了自定义数据对象的两个版本
	 * 原始版本由hibernate维护，用作脏数据检查依据，复制版本由用户使用，hibernate将在 脏数据检查过程中比较这两个版本的数据。
	 * 
	 * 
	 */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		if (x == null || y == null)
			return false;
		x = (String) x;
		y = (String) y;
		return x.equals(y);
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	/**
	 * 读取数据转换为自定义类型返回 names包含了自定义类型的映射字段名称
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		// 取得字段名称并查询
		String value = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
		if (value != null) {
			if(value.length() < 24) {
				return value;
			}
			return Encrypt.getSecret(value, 2);
		} else {
			return null;
		}
	}

	/**
	 * 数据保存时被调用
	 */
	@Override
	public void nullSafeSet(PreparedStatement ps, Object value, int index)
			throws HibernateException, SQLException {
		if (value != null) {
			String str = Encrypt.getSecret((String) value, 3);
			// 保存数据
			Hibernate.STRING.nullSafeSet(ps, str, index);
		} else {
			// 空值就直接保存了
			Hibernate.STRING.nullSafeSet(ps, value, index);
		}

	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return null;
	}

	/**
	 * 修改类型对应的java类型 我们这边使用String类型
	 */
	@Override
	public Class returnedClass() {
		return String.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}
}
