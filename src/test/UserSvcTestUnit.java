package test;

import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import core.logic.bean.persistence.UserBean;
import core.logic.bean.result.UserResult;
import core.logic.svc.SvcUser;

public class UserSvcTestUnit{
	private SvcUser	svc		= null;
	private final static Log logger	= LogFactory.getLog(UserSvcTestUnit.class);

	public UserSvcTestUnit() throws Exception{
		this.svc = new SvcUser();
	}
	


	@Test
	public void testLogin() throws Exception{
		
		boolean ret = false;
		
		String userID = "test";
		String userPWD = "test";
		
		UserResult result = svc.loginUser(userID, userPWD);
		
		if(result != null){
			ret = true;
		}

		assertTrue(ret);
	}
	
	@Test
	public void testSetUserInfo() throws Exception{
		String userId = "khm@test.com";
		String userPw = "test";
		String userName = "테스트";

		boolean ret = false;
		
		svc.writeBoard(0, "hello", "text");
		
		assertTrue(ret);
	}
	
	

}
