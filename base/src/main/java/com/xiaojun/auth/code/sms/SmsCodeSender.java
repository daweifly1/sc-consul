/**
 * 
 */
package com.xiaojun.auth.code.sms;

/**
* @author jy88
 *
 */
public interface SmsCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
