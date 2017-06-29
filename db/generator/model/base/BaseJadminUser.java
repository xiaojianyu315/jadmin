package org.xjy.jadmin.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseJadminUser<M extends BaseJadminUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return get("id");
	}

	public void setProjectCode(java.lang.String projectCode) {
		set("projectCode", projectCode);
	}

	public java.lang.String getProjectCode() {
		return get("projectCode");
	}

	public void setUserName(java.lang.String userName) {
		set("userName", userName);
	}

	public java.lang.String getUserName() {
		return get("userName");
	}

	public void setPassWord(java.lang.String passWord) {
		set("passWord", passWord);
	}

	public java.lang.String getPassWord() {
		return get("passWord");
	}

	public void setRealName(java.lang.String realName) {
		set("realName", realName);
	}

	public java.lang.String getRealName() {
		return get("realName");
	}

	public void setMobile(java.lang.String mobile) {
		set("mobile", mobile);
	}

	public java.lang.String getMobile() {
		return get("mobile");
	}

	public void setMenuIds(java.lang.String menuIds) {
		set("menuIds", menuIds);
	}

	public java.lang.String getMenuIds() {
		return get("menuIds");
	}

	public void setIsEnable(java.lang.Integer isEnable) {
		set("isEnable", isEnable);
	}

	public java.lang.Integer getIsEnable() {
		return get("isEnable");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

}