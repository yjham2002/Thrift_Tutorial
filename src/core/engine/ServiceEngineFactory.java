package core.engine;

import java.util.HashMap;
import java.util.Map;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.apache.thrift.TMultiplexedProcessor;

import server.ThriftAdminServiceHandler;
import server.ThriftServiceHandler;
import thrift.gen.javacode.ThriftAdminService;
import thrift.gen.javacode.ThriftListBean;
import thrift.gen.javacode.ThriftService;
import thrift.gen.javacode.ThriftUserBean;
import thrift.gen.javacode.ThriftUserResult;

import com.appg.gpack.common.bean.ListBean;
import com.appg.gpack.common.exception.ServiceException;
import com.appg.gpack.logic.basic.svc.email.IEmailSVC;
import com.appg.gpack.logic.basic.svc.email.impl.GMailSmtpSVC;
import com.appg.gpack.logic.basic.svc.file.IFile;
import com.appg.gpack.logic.basic.svc.file.impl.LocalFileYMDSVC;
import com.appg.gpack.server.IServer;
import com.appg.gpack.server.thrift.MultiNonBlockWithGGOMCallbackThriftServer;
import com.appg.gpack.svc.factory.AbstractMapperFacedeFactory;
import com.appg.gpack.svc.factory.AbstractServiceEngineFactory2;

import core.logic.bean.persistence.UserBean;
import core.logic.bean.result.UserResult;
import core.logic.svc.SvcUser;
import core.orika.DateToStringConverter;

/**
 * 서비스엔진 팩토리
 * @author nukiboy
 *
 */
public class ServiceEngineFactory extends AbstractServiceEngineFactory2<ServiceEngine> {
		
	private static ServiceEngineFactory instance = null ;
	private static IServer server = null ;
	
	public ServiceEngineFactory(Class clazz)
	{
		super(clazz) ;
	}
	
	public static ServiceEngineFactory getInstance()
	{			
		if( instance == null )
		{
			instance = new ServiceEngineFactory(ServiceEngine.class) ;
			
			Map<String,String> mMemType = new HashMap<String,String>() ;
				
		}
		
		return instance ;
	}
	
	@Override
	protected ServiceEngine defineEngine(ServiceEngine engine) throws ServiceException
	{				
		// THRIFT 맵퍼용 
		engine.setMpFactory(new AbstractMapperFacedeFactory() {
			
			@Override
			public MapperFacade getMapper() {
				
				// 클래스 맵퍼 팩토리 정의
				MapperFactory mf = new DefaultMapperFactory.Builder().build();
				
				// Globally 하게 컨버터 등록
				ConverterFactory converterFactory = mf.getConverterFactory() ;
				converterFactory.registerConverter(new DateToStringConverter()) ;
				
				// entity 추가
				mf.registerClassMap(mf.classMap(ThriftUserBean.class, UserBean.class).byDefault().toClassMap());
				mf.registerClassMap(mf.classMap(ThriftUserResult.class, UserResult.class).byDefault().toClassMap());
				mf.registerClassMap(mf.classMap(ThriftListBean.class, ListBean.class).byDefault().toClassMap());
				
				return mf.getMapperFacade() ;
				
			}
		});
		
				
		int portSMTP = 465	;
		int portDB = 27017		;
		int portPUSH = 9091	;		
				
		/** 공통 서비스 **/
			
		// 꼼 벤더 키
//		int vendorCDForGGOMV1 = 2008 ;
		
		// 실서버
		//IPushSVC push = new GGOMV1SVC(vendorCDForGGOMV1,"ggom1-1.appg.co.kr", portPUSH) ;
		// 로컬서버
//		IPushSVC push = new GGOMV1SVC(vendorCDForGGOMV1,"localhost", portPUSH) ;
		
		
		IFile file = new LocalFileYMDSVC("C:\\apm\\public_html\\files\\", "http://192.168.0.76:8080/files/") ; 		// 끝에 항상 붙여야 함
		
		IEmailSVC email = new GMailSmtpSVC("smtp.gmail.com",portSMTP, "hakmin.kim@richware.co.kr", "rlagkrals2" ) ;
		
				
		engine.setFile(file) ;
//		engine.setPush(push);
		engine.setEmail(email) ;
						
		/** 커스텀 서비스 **/		
		
		SvcUser userSvc = new SvcUser();
		
		//사용자
		engine.setUserSvc(userSvc);
		
		
		// 크론 서비스 등록(0시 1분에 수행하는 크론)
		/*
		int maxThreadForCron = 2 ;
		
		ICron cron = new QuartzCronImpl(maxThreadForCron) ;
		
		String cronExpression = "0 1 0 * * ?" ;		// 0시 1분에 수행
		
		cron.register(cronExpression,new CronJobPerDayHandler(engine),"cron1") ;
		
		
		String cronExpression2 = "12 0 0 * * ?" ;	// 12시 0분에 수행
		
		cron.register(cronExpression2,new CronJobPerDayHandler2(engine),"cron2") ;
		
		cron.start();
		*/
		
		return engine ;				

	}	
	

	@Override
	protected IServer defineServer(int serverPort,String serverTitle) throws ServiceException 
	{
		TMultiplexedProcessor processor = new TMultiplexedProcessor() ;
		
		ThriftService.Processor core = new ThriftService.Processor(new ThriftServiceHandler()) ;		  
		
		processor.registerProcessor("core", core ) ;
		
		int serverThred = 512;
		
		IServer server = new MultiNonBlockWithGGOMCallbackThriftServer
								(serverTitle, serverPort , serverThred, processor, null , false ) ;
		
		return server ;
				
	}

	@Override
	protected IServer defineAdminServer(int serverPort, String serverTitle) throws ServiceException
	{
		int serverThred = 8;
		TMultiplexedProcessor processor = new TMultiplexedProcessor() ;
		
		ThriftAdminService.Processor core = new ThriftAdminService.Processor(new ThriftAdminServiceHandler()) ;		  
		
		processor.registerProcessor("core", core ) ;
			      
		IServer server = new MultiNonBlockWithGGOMCallbackThriftServer
								(serverTitle, serverPort , serverThred, processor, null , false ) ;
		
		return server ;			

	}
}
