package server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFacade;

import org.apache.thrift.TException;

import thrift.gen.javacode.ThriftFileBean;
import thrift.gen.javacode.ThriftService;
import thrift.gen.javacode.ThriftServiceException;
import thrift.gen.javacode.ThriftUserBean;
import thrift.gen.javacode.ThriftUserResult;

import com.appg.gpack.common.exception.ServiceException;
import com.appg.thrift.util.ThriftConvertUtil;

import core.engine.ServiceEngine;
import core.engine.ServiceEngineFactory;
import core.logic.bean.persistence.FileBean;

public class ThriftServiceHandler implements ThriftService.Iface{
	private ServiceEngine	serviceEngine;
	private MapperFacade	mp;

	public ThriftServiceHandler(){
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
	public ThriftUserResult getUserInfo(String id) throws ThriftServiceException, TException{
		
		ThriftUserResult result = null;
		try{
			result = mp.map(serviceEngine.getUserSvc().getUserInfo(id), ThriftUserResult.class);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
		return result;
	}

	@Override
	public String duplicateUserId(String id) throws ThriftServiceException, TException{
		String result = "";
		try{
			result = serviceEngine.getUserSvc().duplicateUserId(id);
		}
		catch (ServiceException e){
			throw new ThriftServiceException(e.getEcode(), e.getEmsg());
		}
		return result;
	}

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

			List<FileBean> fileList = serviceEngine.getUserSvc().imgFileUpload(fileBeans);
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

}
