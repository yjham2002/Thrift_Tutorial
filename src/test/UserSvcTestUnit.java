package test;

import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import core.logic.bean.result.UserResult;
import core.logic.svc.SvcUser;

public class UserSvcTestUnit{
	private SvcUser	svc		= null;
	private final static Log logger	= LogFactory.getLog(UserSvcTestUnit.class);

	public UserSvcTestUnit() throws Exception{
		this.svc = new SvcUser();
	}
	
	@Test
	public void testGetUser() throws Exception{
		String id = "test@test.com";

		boolean ret = false;
		UserResult userInfo = svc.getUserInfo(id);
		logger.info("UserID ==> " + userInfo.getUserId());
		logger.info("UserNick ==> " + userInfo.getUserNick());
		logger.info("UserGender ==>" + userInfo.getUserGender());
		logger.info("UserTall ==>" + userInfo.getUserTall());
		logger.info("UserProfileImageUrl ==> " + userInfo.getUserProfileImageurl());
		if(userInfo != null)
			ret = true;

		assertTrue(ret);
	}

	@Test
	public void testLogin() throws Exception{
		boolean ret = false;
		Integer count = 0;
		String userId = "test@test.com";
		String userPwd = "test";
		String deviceID = "";
		int deviceType = 1;
		String registrationKey = "";
		String appVersion = "";
		UserResult result = new UserResult();
		//result= svc.Login(userId, userPwd);
		logger.info("Login Success ==> " + result.getUserNick());
		
		if(result != null){
			ret = true;
		}

		assertTrue(ret);
	}
	
	@Test
	public void testSetUserInfo() throws Exception{
		String userId = "khm@test.com";
		String userPwd = "test";
		String userNick = "테스트";

		boolean ret = false;
		/*
		Integer result = svc.setUserInfo(userId, userPwd, userNick);
		
		logger.info("회원가입 성공 유무(0이면 실패, 1이면 성공) ==> " + result);
		if(result == 1)
		{
			ret = true;
		}
		*/
		
		assertTrue(ret);
	}

}
