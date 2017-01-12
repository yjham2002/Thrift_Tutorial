package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.appg.gpack.common.exception.ServiceException;

import core.logic.bean.persistence.BoardBean;
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
		
		//UserResult result = svc.loginUser(userID, userPWD);
		//ret = result != null;

		assertTrue(ret);
	}
	
	@Test
	public void testBoardList(){
		boolean ret = false;
		List<BoardBean> lists;
		try {
			lists = svc.getBoardAll();
			System.out.println("content size : " + lists.get(5).toString());
			for(int i = 0; i < 5; i++){
				logger.info("-");
				logger.info("content : " + lists.get(i));
			}
			
			ret = !(lists.size() < 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		assertTrue(ret);
	}
	
	
	@Test
	public void testBoardDetail() throws Exception{
		boolean ret = false;
		BoardBean lists;
		lists = svc.getBoardDetail(10);
		System.out.println("id : " + lists.getTitle());
		
	}
	
	

}
