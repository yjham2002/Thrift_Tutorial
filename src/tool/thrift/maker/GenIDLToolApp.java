package tool.thrift.maker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.appg.gpack.common.bean.ListBean;
import com.appg.gpack.common.exception.ServiceException;
import com.appg.gpack.common.util.GU;
import com.appg.gpack.svc.factory.BeanBox;
import com.appg.thrift.tool.ThriftIDLGenerator2;
import com.appg.thrift.tool.bean.ServiceBox;
import com.appg.thrift.tool.bean.ThriftIDLBean2;

import core.logic.bean.persistence.BoardBean;
import core.logic.bean.persistence.FileBean;
import core.logic.bean.persistence.UserBean;
import core.logic.bean.result.UserResult;
import core.logic.svc.SvcUser;



/**
 * 빈 순서도 중요
 * 함수가 오버로딩 지원하지 않음
 * @author nukiboy
 *
 */
public class GenIDLToolApp {

	public static void main(String[] args) throws ServiceException, IOException, InterruptedException {

		String thrift_base_path = "C:\\Users\\a\\workspace\\thrift";
		
		// PREFIX
		StringBuffer buf = new StringBuffer() ;
		
		buf.append("namespace java thrift.gen.javacode\n") ;
		//buf.append("namespace cocoa  ThriftService\n") ;
		buf.append("namespace php ThriftService\n") ;
		
		buf.append("typedef i16 short\n");
		buf.append("typedef i32 int\n");
		buf.append("typedef i64 long\n");
		buf.append("typedef string String\n");
		buf.append("typedef binary ThriftByteBuffer\n");
		buf.append("typedef bool boolean\n\n");
		
		
		buf.append("exception ThriftServiceException { 1 : int ecode, 2 : String emsg }\n") ;
		
		ThriftIDLBean2 bean = new ThriftIDLBean2() ;
		
		bean.setServiceName("ThriftService") ;
		bean.setServiceNameForAdmin("ThriftAdminService") ;
		
		bean.setIdlPath(thrift_base_path + "\\src\\tool\\thrift\\idl\\thrift.idl") ;
//		bean.setGenPath("C:\\work14\\jiini\\src\\core\\jini\\thrift\\gen") ;
	
		//bean.setPreDefineStr(buf.toString()) ;
		//bean.setlStruct(fac.getBeans()) ; 
		
		List<BeanBox> beanBoxs = new ArrayList<BeanBox>();
		beanBoxs.add(new BeanBox(BoardBean.class));
		beanBoxs.add(new BeanBox(UserBean.class));
		beanBoxs.add(new BeanBox(UserResult.class));
		beanBoxs.add(new BeanBox(FileBean.class));
		beanBoxs.add(new BeanBox(ListBean.class));
		
		
		bean.setPreDefineStr(buf.toString());
		bean.setlStruct(beanBoxs);
		
		ServiceBox box1 = new ServiceBox() ;		
		box1.setCls(SvcUser.class) ;
		box1.setPhysicalPaths(GU.getList(thrift_base_path + "\\src\\core\\logic\\svc\\SvcUser.java")) ;				
		
		bean.addServiceBox(box1) ;		
							
		ThriftIDLGenerator2 gen = new ThriftIDLGenerator2(bean) ;
		
		gen.create(); 
		
//		String exeFmt = "c:\\thrift -o \"%s\" --gen java:beans \"%s\"".format(bean.getGenPath(), bean.getIdlPath()) ;
//		
//		try
//		{
////			String cmd[] = { "thrift" , "-o" , bean.getGenPath() , "--gen" , "java:beans" , bean.getIdlPath() } ;
//			String cmd[] = { "cmd" , "/C" , "start c:\\thrift.exe" , "-o" , bean.getGenPath() , "--gen" , "java:beans" , bean.getIdlPath()  } ;
////			Process pro = Runtime.getRuntime().exec(cmd) ;
////			pro.waitFor() ;
//			
//			ProcessBuilder b = new ProcessBuilder("cmd","/C","start c:\\thrift","--help") ;			
//			Process pro = b.start() ;
//			
//			
//			InputStream in = pro.getInputStream() ;			
//			InputStreamReader isr = new InputStreamReader(in) ;
//			BufferedReader br = new BufferedReader(isr) ; 
//			
//			String szLine = "" ;
//			while(( szLine = br.readLine()) != null )
//			{
//				System.out.println(szLine); 
//			}
//			
//			// System.out.println(IOUtils.toString(in) ) ;
//					
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace(); 
//		}
		
		System.out.println("gen done") ;


	}

}
