package core.common.util;

import java.security.MessageDigest;

import com.appg.gpack.common.exception.ServiceException;

public class EncryptMD5 
{
	public String decrypt_md5(String msg,String key)  throws Exception
	{
		String decodeMsg = Base64Coder.decodeString(msg);
		
		StringBuffer ret = new StringBuffer();
		
		String buffer 	= "";
		String key2 	= "";
		
		while(decodeMsg.length() > 0)
		{
			key2 		= this.genMD5(key + key2 + buffer);
			int calcLength = decodeMsg.length() < 16 ? decodeMsg.length() : 16;
			
			buffer 		= this.bytexor(decodeMsg.substring(0,calcLength),key2);
			decodeMsg 	= decodeMsg.substring(calcLength);
			ret.append(buffer);
		}
		return ret.toString();
	}
	public String encrypt_md5(String msg,String key)  throws Exception
	{
		StringBuffer ret = new StringBuffer();
		
		String buffer 	= "";
		String key2 	= "";
		
		while(msg.length() > 0)
		{
			key2 	= this.genMD5((key + key2 + buffer));
			int calcLength = msg.length() < 16 ? msg.length() : 16;
			
			buffer 	= msg.substring(0,calcLength);
			msg 	= msg.substring(calcLength);
			ret.append(this.bytexor(buffer,key2));
		}
		return Base64Coder.encodeString(ret.toString());
	}
	
	private String bytexor(String a,String b)
	{
		StringBuffer ret = new StringBuffer();

		for(int i=0; i< a.length(); i++)
		{
			ret.append((char)(a.charAt(i) ^ b.charAt(i)));
		}
		
		return ret.toString();
	}
	
	private String genMD5(String mdText) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(mdText.getBytes());

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<byteData.length;i++)
		{
			String hex=Integer.toHexString(0xff & byteData[i]);
			if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
		}
		
		return hexString.toString();
	}
}
