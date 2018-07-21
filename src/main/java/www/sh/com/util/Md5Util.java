package www.sh.com.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author liaojinneng
 */
public class Md5Util {
	/**
	 * 普通MD5(32位，小写字母)
	 */
	public static String md5LowerCase(String input) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(input.getBytes("utf-8"));

			StringBuilder hexValue = new StringBuilder(md5Bytes.length * 2);
			for (byte md5Byte : md5Bytes) {
				int val = ((int) md5Byte) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 普通MD5(32位，大写字母)
	 */
	public static String md5UpperCase(String input) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(input.getBytes("utf-8"));

			StringBuilder hexValue = new StringBuilder(md5Bytes.length * 2);
			final char[] hexDigits = "0123456789ABCDEF".toCharArray();
			for (byte md5Byte : md5Bytes) {
				hexValue.append(hexDigits[(md5Byte >> 4) & 0x0f]);
				hexValue.append(hexDigits[md5Byte & 0x0f]);
			}

			return hexValue.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * SHA1加密
	 */
	public static String sha1(String input) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			return "check jdk";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		byte[] md5Bytes = md5.digest(input.getBytes(Charset.forName("utf-8")));
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加盐MD5
	 */
	public static String generate(String password) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		password = md5Hex(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}
	
	/**
	 * 生成salt
	 */
	public static String createSalt() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		return salt;
	}

	/**
	 * 校验加盐后是否和原文一致
	 */
	public static boolean verify(String password, String md5) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = md5.charAt(i);
			cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
			cs2[i / 3] = md5.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(password + salt).equals(new String(cs1));
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 自动生成授权账号对应的apiKey
	 */
	public static String generatorApiKey(String account){
		if(StringUtils.isBlank(account)){
			return null;
		}

		String apiKey = null;
		String rawData = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,!,@,#,$,%,^,&,*,(,),_,+";
		String[] arr = rawData.split(",");
		int size = 16;
		StringBuilder input = new StringBuilder(size);

		try {
			for(int i=0;i<size;i++){
                int j = (int) Math.round(arr.length * Math.random());
                if(j == arr.length){
                    j = j - 1;
                }
                input.append(arr[j]);
            }
			input.append(account);
			apiKey = Md5Util.md5LowerCase(input.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return apiKey;
	}


	public static void main(String[] args) {
		System.out.println(Md5Util.md5LowerCase("dared34sdf43"));
		System.out.println(Md5Util.md5UpperCase("dared34sdf43"));

		String account = "test1";
		System.out.println(account+"的apiKey:"+generatorApiKey(account));

		String password = "123456";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEncoder = encoder.encode(password);
		System.out.println(passwordEncoder);
	}

}
