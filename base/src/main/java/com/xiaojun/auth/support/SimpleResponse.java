/**
 * 
 */
package com.xiaojun.auth.support;

/**
 * 简单响应的封装类
 * 
* @author jy88
 *
 */
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}
	
	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
}
