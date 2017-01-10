package core.engine;

import com.appg.gpack.svc.factory.BaseServiceEngine;

import core.logic.svc.SvcUser;

/**
 * 서비스 엔진
 * @author nukiboy
 *
 */
public class ServiceEngine extends BaseServiceEngine {
	private SvcUser userSvc;

	public SvcUser getUserSvc()
	{
		return userSvc;
	}
	public void setUserSvc(SvcUser userSvc)
	{
		this.userSvc = userSvc;
	}
}
