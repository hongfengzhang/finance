package com.waben.stock.interfaces.commonapi.netease.config;

/**
 * 网易云即时通讯配置
 * 
 * @author luomengan
 *
 */
public class NeteaseImConfig {

	public static final String AppKey = "d0fc8a8448dabce125efce3dcc90c611";

	public static final String AppSecret = "05a13f09d796";
	/**
	 * 创建网易云通信ID
	 */
	public static final String CreateUrl = "https://api.netease.im/nimserver/user/create.action";
	/**
	 * 更新并获取新token
	 */
	public static final String RefreshTokenUrl = "https://api.netease.im/nimserver/user/refreshToken.action";
	/**
	 * 创建聊天室
	 */
	public static final String ChatroomCreateUrl = "https://api.netease.im/nimserver/chatroom/create.action";

}
