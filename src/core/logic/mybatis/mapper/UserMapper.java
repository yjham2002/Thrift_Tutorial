package core.logic.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import core.logic.bean.result.UserResult;

public interface UserMapper{
	public UserResult getUserInfo(@Param("id") String id); // Retrieving user info
	public Integer Login(Map<String, String> params); // Signing In Process
	public void signupUser(Map<String, String> params); // Signing In Process
	public void writeBoard(Map<String, String> params); // Signing In Process
	public Integer duplicateUserId(@Param("userId") String userId);
	public int isLeave(String userId);
}
