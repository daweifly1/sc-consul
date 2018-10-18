/**
 * 
 */
package com.xiaojun.auth.social.weixin.api;

/**
 * 微信API调用接口
 * 
* @author jy88
 *
 */
public interface Weixin {

	/* (non-Javadoc)
	 * @see com.ymt.pz365.framework.security.social.api.SocialUserProfileService#getUserProfile(java.lang.String)
	 */
	WeixinUserInfo getUserInfo(String openId);
	
}
