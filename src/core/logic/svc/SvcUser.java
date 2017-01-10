package core.logic.svc;

import java.util.List;

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
		String ERR_MSG = "User doesn't exist";
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
	
}
