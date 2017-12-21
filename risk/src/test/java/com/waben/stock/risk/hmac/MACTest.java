package com.waben.stock.risk.hmac;

import com.waben.stock.interfaces.util.JacksonUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * MAC算法工具类
 * 对于HmacMD5、HmacSHA1、HmacSHA256、HmacSHA384、HmacSHA512应用的步骤都是一模一样的。具体看下面的代码
 */
class MACCoder {
    /**
	 * 产生HmacMD5摘要算法的密钥
	 */
	public static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacMD5");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		System.out.println(secretKey.getAlgorithm());
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacMd5摘要算法
	 * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 */
	public static String encodeHmacMD5(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化mac
		mac.init(secretKey);
		//执行消息摘要
		byte[] digest = mac.doFinal(data);
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串
	}
	
	
	/**
	 * 产生HmacSHA1摘要算法的密钥
	 */
	public static byte[] initHmacSHAKey() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA1");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacSHA1摘要算法
	 * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 */
	public static String encodeHmacSHA(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化mac
		mac.init(secretKey);
		//执行消息摘要
		byte[] digest = mac.doFinal(data);
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串
	}
	
	
	
	/**
	 * 产生HmacSHA256摘要算法的密钥
	 */
	public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacSHA1摘要算法
	 * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 */
	public static String encodeHmacSHA256(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化mac
		mac.init(secretKey);
		//执行消息摘要
		byte[] digest = mac.doFinal(data);
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串
	}
	
	

	/**
	 * 产生HmacSHA256摘要算法的密钥
	 */
	public static byte[] initHmacSHA384Key() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA384");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacSHA1摘要算法
	 * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 */
	public static String encodeHmacSHA384(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA384");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化mac
		mac.init(secretKey);
		//执行消息摘要
		byte[] digest = mac.doFinal(data);
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串
	}
	
	

	/**
	 * 产生HmacSHA256摘要算法的密钥
	 */
	public static byte[] initHmacSHA512Key() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA512");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacSHA1摘要算法
	 * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 */
	public static String encodeHmacSHA512(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		//初始化mac
		mac.init(secretKey);
		//执行消息摘要
		byte[] digest = mac.doFinal(data);
		return new HexBinaryAdapter().marshal(digest);//转为十六进制的字符串
	}
}

public class MACTest {
	public static void main(String[] args) throws Exception {
//		sign();
		pay();
//		byte[] keyHmacMD5=MACCoder.initHmacMD5Key();
//		System.out.println(new String(keyHmacMD5));
//		System.out.println(MACCoder.encodeHmacMD5(testString.getBytes(),"E1DF87DFCAC7261B5562E57CAED5F6A9".getBytes()));
		
//		byte[] keyHmacSHA1=MACCoder.initHmacSHAKey();
//		System.out.println(MACCoder.encodeHmacSHA(testString.getBytes(),keyHmacSHA1));
//
//		byte[] keyHmacSHA256=MACCoder.initHmacSHA256Key();
//		System.out.println(MACCoder.encodeHmacSHA256(testString.getBytes(),keyHmacSHA256));
//
//		byte[] keyHmacSHA384=MACCoder.initHmacSHA384Key();
//		System.out.println(MACCoder.encodeHmacSHA384(testString.getBytes(),keyHmacSHA384));
//
//		byte[] keyHmacSHA512=MACCoder.initHmacSHA512Key();
//		System.out.println(MACCoder.encodeHmacSHA512(testString.getBytes(),keyHmacSHA512));
	}

	public static void sign() {
		Map<String, String> sign = new HashMap<>();
		sign.put("sendTime", "20171220141915");
		sign.put("sendSeqId", "123456789");
		sign.put("transType", "A001");
		sign.put("organizationId", "283673885");
		String json = JacksonUtil.encode(sign);
		System.out.println(json);
	}

	public static void pay() {

		Map<String, String> pay = new HashMap<>();
		pay.put("bankCode", "ICBC");
		pay.put("body", "比特亞洲充值");
		pay.put("callBackUrl", "http://47.74.140.194/gczf/returnURL.html");
		pay.put("cardType", "0");
		pay.put("mobile", "13128814416");
		pay.put("name", "比特亞洲充值");
		pay.put("notifyUrl", "http://47.74.140.194/gczf/notifyURL.html");
		pay.put("organizationId", "283673885");
		pay.put("payType", "T1");
		pay.put("sendSeqId", "20171221171639289");
		pay.put("sendTime", "20171221171639");
		pay.put("transAmt", "200");
		pay.put("transType", "BP02");
		pay.put("subject", "MAC");

		String json = JacksonUtil.encode(pay);
		System.out.println(json);
		String makeMac = makeMac(json.toString(), "BDBEA61420BAEA211846DEB12FC99EC5");
		pay.put("mac", makeMac);
		String result = JacksonUtil.encode(pay);
		System.out.println(result);
	}


	public static String makeMac(String json,String macKey){
		Map<String, String> contentData = (Map<String, String>) Tools.parserToMap(json);
		String macStr="";
		Object[] key_arr = contentData.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			Object value = contentData.get(key);
			if (value != null ) {
				if (!key.equals("mac") ) {
					macStr+= value.toString();
				}
			}
		}
		String rMac = DESUtil.mac(macStr,macKey);
		return rMac;
	}
}