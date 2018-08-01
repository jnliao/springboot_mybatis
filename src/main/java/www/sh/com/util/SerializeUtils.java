package www.sh.com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Administrator
 */
public class SerializeUtils {

    /**
     * 序列化 返回字节
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return  bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化 返回字符串
     * @param object
     * @return
     */
    public static String serialize2(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toString("ISO-8859-1");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化 
     * @param message
     * @return object
     */
    public static Object unSerialize2(String message) {
    	try {
			ByteArrayInputStream bis = new ByteArrayInputStream(
					message.getBytes("ISO-8859-1"));//此处指定字符集将字符串编码成字节数组，此处的字符集需要与发布时的字符集保持一致
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois =new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

}
