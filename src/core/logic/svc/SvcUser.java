package core.logic.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.appg.gpack.common.exception.ServiceException;
import com.appg.thrift.anno.ThriftAdminMethod;
import com.appg.thrift.anno.ThriftMethod;

import core.common.constants.Constants;
import core.common.util.FileUpload;
import core.engine.ServiceEngine;
import core.engine.ServiceEngineFactory;
import core.logic.bean.persistence.BoardBean;
import core.logic.bean.persistence.FileBean;
import core.logic.bean.result.UserResult;
import core.logic.mybatis.DBSessionManager;
import core.logic.mybatis.mapper.UserMapper;

/**
 * A Class for providing User account related services 
 * @author EuiJin Ham
 */
public class SvcUser extends DBSessionManager {
	
	private final static Log logger	= LogFactory.getLog(SvcUser.class);
	ServiceEngine serviceEngine	= null;
	
	/**
	 * Default Constructor
	 * @throws ServiceException
	 */
	public SvcUser() throws ServiceException {
		ServiceEngineFactory serviceEngineFactory = ServiceEngineFactory.getInstance();
		serviceEngine = serviceEngineFactory.getEngine();
	}

	/**
	 * A method for retieving user information with user key number
	 * @param id
	 * @return user Information
	 * @throws ServiceException
	 */
	@ThriftMethod
	public UserResult getUserInfo(String id) throws ServiceException{
		
		final String ERR_MSG = "User doesn't exist";
		String TAG = "getUserInfo : ";
		logger.info(TAG + id);
		
		SqlSession session = null;
		UserResult userInfo = null;

		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			userInfo = mapper.getUserInfo(id);
			if(userInfo == null){
				throw new ServiceException(-1, ERR_MSG);
			}
		}
		catch (ServiceException e){
			throw new ServiceException(-1, ERR_MSG);
		}
		finally{
			closeSession(session);
		}
		return userInfo;
	}
	
	/**
	 * An image file uploading function
	 * @param fileBeans
	 * @return file List
	 * @throws ServiceException
	 */
	@ThriftAdminMethod
	@ThriftMethod
	public List<FileBean> imgFileUpload(List<FileBean> fileBeans) throws ServiceException{
		List<FileBean> result = null;

		FileUpload fu = new FileUpload();

		try{
			result = fu.imgFileUpload(fileBeans, Constants.UPLOAD_IMAGE_SIZE);
		}
		catch (Exception e){
			throw new ServiceException(-1, "파일업로드 실패");
		}

		return result;
	}

	/**
	 * A File uploading method
	 * @param fileBeans
	 * @return
	 * @throws ServiceException
	 */
	@ThriftAdminMethod
	@ThriftMethod
	public List<FileBean> fileUpload(List<FileBean> fileBeans) throws ServiceException{
		List<FileBean> result = null;
		FileUpload fu = new FileUpload();

		try{
			result = fu.fileUpload(fileBeans);
		}
		catch (Exception e){
			throw new ServiceException(-1, "파일업로드 실패");
		}

		return result;
	}
	
	/**
	 * A method for checking whether the user typed id is redundant
	 * @param id
	 * @return It is redundant when 1 is returned 
	 * @throws ServiceException
	 */
	@ThriftMethod
	public String duplicateUserId(String id) throws ServiceException{
		String TAG = "duplicateUserId : ";
		logger.info(TAG + id);

		SqlSession session = null;
		int count = 0;
		String resultMsg = "";
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			count = mapper.duplicateUserId(id);
			if(count < 1){
				resultMsg = "사용가능한 아이디 입니다.";
			}
			else{
				throw new ServiceException(-1, "사용할 수 없는 아이디 입니다.");
			}
		}
		finally{
			closeSession(session);
		}
		return resultMsg;
	}
	
	/**
	 * 유저 로그인
	 * @param userID
	 * @param userPWD
	 * @return
	 * @throws ServiceException
	 */
	@ThriftAdminMethod
	public UserResult loginUser(String userID, String userPWD) throws ServiceException{
		UserResult retBean = null;
		SqlSession session = null;
		int result = 0;
		if(Constants.IS_DEBUG) {
			logger.info("[PARAMETER] userID => " + userID);
		}
		
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", userID);
			params.put("userPw", userPWD);
			
			result = mapper.Login(params);
			
			if(result < 1) {
				logger.info("Login Failed");
			}else{
				retBean = mapper.getUserInfo(userID);
				
				logger.info("Login Succeeded as [" + retBean.getName() + "]");
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(-9999, "알수없는에러");
		}
		finally{
			closeSession(session);
		}
		
		return retBean;
	}

	@ThriftAdminMethod
	public BoardBean getBoardDetail(int id) throws ServiceException{
		BoardBean retBean = null;
		SqlSession session = null;
		
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			
			retBean = mapper.getBoardDetail(id);
		}
		
		catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(-9999, "알수없는에러");
		}
		finally{
			closeSession(session);
		}
		
		return retBean;
	}
	
	@ThriftAdminMethod
	public void signupUser(String userId, String userPw, String name) throws ServiceException{
		SqlSession session = null;
		
		if(Constants.IS_DEBUG) logger.info("Trying to add an account for [" + userId + "/" + userPw + "/" + name + "]");
		
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			
			Map<String, String> params = new HashMap<>();
			params.put("userId", userId);
			params.put("userPw", userPw);
			params.put("name", name);
			
			if(mapper.duplicateUserId(userId) == 0) mapper.signupUser(params);
			else{
				throw new ServiceException(-999, "This id is redundant");
			}
			
		}catch(ServiceException e){
			e.printStackTrace();
			throw new ServiceException(-999, "Insertion failed");
		}finally{
			closeSession(session);
		}
	}
	
	@ThriftAdminMethod
	public List<BoardBean> getBoardAll() throws ServiceException{
		SqlSession session = null;
		
		if(Constants.IS_DEBUG) logger.info("Trying to retrieving board list");
		
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			List<BoardBean> lists = mapper.getBoardList();
			
			return lists;
		}catch(ServiceException e){
			e.printStackTrace();
			throw new ServiceException(-999, "Insertion failed");
		}finally{
			closeSession(session);
		}
	}
	
	@ThriftAdminMethod
	public void updateUser(String userId, String userPw, String name) throws ServiceException{
		SqlSession session = null;
		
		if(Constants.IS_DEBUG) logger.info("Trying to update an account.");
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			
			Map<String, String> params = new HashMap<>();
			params.put("userId", userId);
			params.put("userPw", userPw);
			params.put("name", name);
			
			mapper.modifyUser(params);
			
		}catch(ServiceException e){
			e.printStackTrace();
			throw new ServiceException(-991, "Updating failed");
		}finally{
			closeSession(session);
		}
	}
	
	@ThriftAdminMethod
	public void writeBoard(int uid, String title, String content, List<FileBean> fileBeans) throws ServiceException{
		SqlSession session = null;
		List<FileBean> fileList = null;
		if(Constants.IS_DEBUG) logger.info("Trying to add an article.");
		try{
			session = this.getSession();
			UserMapper mapper = session.getMapper(UserMapper.class);
			
			FileUpload fu = new FileUpload();
			
			fileList = fu.imgFileUpload(fileBeans, Constants.UPLOAD_IMAGE_SIZE);

			
			Map<String, Object> params = new HashMap<>();
			params.put("uid", Integer.toString(uid));
			params.put("title", title);
			params.put("content", content);
			
			mapper.writeBoard(params);
			
			params.put("fileList", fileList);
			
		}catch(ServiceException e){
			e.printStackTrace();
			throw new ServiceException(-991, "Insertion failed");
		}finally{
			closeSession(session);
		}
	}

}
