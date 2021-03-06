package server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.appg.gpack.common.exception.ServiceException;
import com.appg.thrift.util.ThriftConvertUtil;

import core.engine.ServiceEngine;
import core.engine.ServiceEngineFactory;
import core.logic.bean.persistence.BoardBean;
import core.logic.bean.persistence.FileBean;
import ma.glasnost.orika.MapperFacade;
import thrift.gen.javacode.ThriftAdminService;
import thrift.gen.javacode.ThriftBoardBean;
import thrift.gen.javacode.ThriftFileBean;
import thrift.gen.javacode.ThriftServiceException;
import thrift.gen.javacode.ThriftUserResult;

public class ThriftAdminServiceHandler implements ThriftAdminService.Iface{
	
	private ServiceEngine	serviceEngine;
	private MapperFacade	mp;
	public ThriftAdminServiceHandler(){
		ServiceEngineFactory serviceEngineFactory = ServiceEngineFactory.getInstance();

		try{
			this.serviceEngine = serviceEngineFactory.getEngine();
			this.mp = serviceEngine.getMpFactory().getMapper();
		}
		catch (ServiceException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<ThriftFileBean> fileUpload(List<ThriftFileBean> param1) throws ThriftServiceException, TException{
		try{
			List<FileBean> fileBeans = new ArrayList<FileBean>();

			for (ThriftFileBean bean : param1){
				FileBean fileBean = new FileBean();

				fileBean.setFile(ByteBuffer.wrap(bean.getFile()));
				fileBean.setExtension(bean.getExtension());
				fileBean.setFileKey(bean.getFileKey());
				fileBean.setFileName(bean.getFileName());

				fileBeans.add(fileBean);
			}

			List<FileBean> fileList = serviceEngine.getUserSvc().fileUpload(fileBeans);
			List<ThriftFileBean> ret = ThriftConvertUtil.convert(ThriftFileBean.class, mp, fileList);
			return ret;
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public List<ThriftFileBean> imgFileUpload(List<ThriftFileBean> param1) throws ThriftServiceException, TException{
		try{
			List<FileBean> fileBeans = new ArrayList<FileBean>();

			for (ThriftFileBean bean : param1){
				FileBean fileBean = new FileBean();

				fileBean.setFile(ByteBuffer.wrap(bean.getFile()));
				fileBean.setExtension(bean.getExtension());
				fileBean.setFileKey(bean.getFileKey());
				fileBean.setFileName(bean.getFileName());

				fileBeans.add(fileBean);
			}

			List<FileBean> fileList = serviceEngine.getUserSvc().imgFileUpload(fileBeans);
			List<ThriftFileBean> ret = ThriftConvertUtil.convert(ThriftFileBean.class, mp, fileList);
			return ret;
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	
	
	@Override
	public ThriftUserResult loginUser(String userID, String userPWD) throws ThriftServiceException, TException {
		try{
			ThriftUserResult bean = mp.map(serviceEngine.getUserSvc().loginUser(userID, userPWD), ThriftUserResult.class);
			System.out.println(bean);
			return bean;
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}
	
	@Override
	public void signupUser(String userId, String userPw, String name) throws TException {
		try{
			serviceEngine.getUserSvc().signupUser(userId, userPw, name);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public void writeBoard(int uid, String title, String content, List<ThriftFileBean> fileList) throws TException {
		try{
			List<FileBean> fileBeans = new ArrayList<FileBean>();

			for (ThriftFileBean bean : fileList){
				FileBean fileBean = new FileBean();

				fileBean.setFile(ByteBuffer.wrap(bean.getFile()));
				fileBean.setExtension(bean.getExtension());
				fileBean.setFileKey(bean.getFileKey());
				fileBean.setFileName(bean.getFileName());

				fileBeans.add(fileBean);
			}
			
			serviceEngine.getUserSvc().writeBoard(uid, title, content, fileBeans);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public void updateUser(String userId, String userPw, String name) throws TException {
		try{
			serviceEngine.getUserSvc().updateUser(userId, userPw, name);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public ThriftBoardBean getBoardDetail(int id) throws TException {
		try{
			ThriftBoardBean bean = mp.map(serviceEngine.getUserSvc().getBoardDetail(id), ThriftBoardBean.class);
			System.out.println(bean);
			return bean;
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public int isLike(int id) throws ThriftServiceException, TException {
		try{
			return serviceEngine.getUserSvc().isLike(id);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public void toggleLike(int bid, int uid) throws ThriftServiceException, TException {
		try{
			serviceEngine.getUserSvc().toggleLike(bid, uid);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public List<ThriftBoardBean> getBoardAll(int pageNum, int count) throws ThriftServiceException, TException {
		List<BoardBean> lists = null;
		List<ThriftBoardBean> ret = null;
		try{
			lists = serviceEngine.getUserSvc().getBoardAll(pageNum, count);
			ret = ThriftConvertUtil.convert(ThriftBoardBean.class, mp, lists);
			return ret;
		}catch(ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

	@Override
	public int countPage() throws ThriftServiceException, TException {
		try{
			return serviceEngine.getUserSvc().countPage();
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
	}

}
