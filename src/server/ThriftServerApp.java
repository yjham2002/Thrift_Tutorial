package server;

import com.appg.gpack.common.exception.ServiceException;
import com.appg.gpack.server.IServer;

import core.common.util.VarConst;
import core.engine.ServiceEngineFactory;

public class ThriftServerApp{

	public static void main(String[] args){
			final ServiceEngineFactory factory = ServiceEngineFactory.getInstance() ;
			final int port = VarConst.thriftPort.CONST_THRIFT_APP;
			final int adminPort = VarConst.thriftPort.CONST_THRIFT_ADMIN;
			
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run(){				
					IServer server = null ;
					try{
						server = factory.getServer(port, "Richware Board Started at " + port);
					} catch (ServiceException e){
						e.printStackTrace();
					}
					server.start() ;										
				}
			}) ;
			
			Thread t2 = new Thread(new Runnable() {
				@Override
				public void run(){	
					IServer server = null ;
					
					try{
						server = factory.getAdminServer(adminPort, "Richware Board Admin Started at " + adminPort);
					} catch (ServiceException e){
						e.printStackTrace();
					}
					server.start();
				}
			}) ;
			
			t1.start() ;
			t2.start() ;
		}
		
	}
			

