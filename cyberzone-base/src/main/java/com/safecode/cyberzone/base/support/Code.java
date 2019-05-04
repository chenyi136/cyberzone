package com.safecode.cyberzone.base.support;

public enum Code {
	/** 200请求成功 */
	OK(200),
//	/** 401没有登录 */
//	UNAUTHORIZED(401),
	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR(500),
	/**601没有登录*/
	INTERNAL_NO_LOGIN(601),
	/**601没有登录*/
	INTERNAL_BUSINESS_ERROR(602),
	
	/**701账户名重复*/
	ACCOUNT_AGAIN(701),
	/**菜单已和子菜单做了关联*/
	MENU_RELEVANCE(702),
	/**菜单已和角色做了关联*/
	MENU_ROLE_RELEVANCE(703);
	
	private final Integer value;

	private Code(Integer value) {
		this.value = value;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public Integer value() {
		return this.value;
	}

	public String toString() {
		return this.value.toString();
	}
}