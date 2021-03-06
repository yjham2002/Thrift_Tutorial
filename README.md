# Thrift based server with JAVA and PHP (Thrift 기반 서버 및 PHP 페이지)

### This document is about the order of processes on constructing Apache thrift based server with making simple website with a board that allows files to be uploaded

- 본 문서는 파일 업로드를 지원하는 간단한 게시판을 구현하는 것을 기반으로 Thrift 기반 서버 구축에 대한 내용을 다룹니다.

### *Integral Part* of this project is [on Github](https://github.com/yjham2002/Thrift_tutorial_PHP)

- 본 프로젝트의 뷰로서 작용하는 필수불가결한 요소는 위 주소를 통해 참조하실 수 있습니다.

##### This Project runs with (본 프로젝트의 구성요소)

- Thrift as a multiplexer (RPC를 위한 플랫폼 다중화 모듈로서)
- C styled IDL as a base declaration (자료형 및 메소드 선언을 위한 C 기반 모듈로서)
- mybatis as a modeller (DB 모델링 모듈로서)
- JAVA as a controller (Java 기반 컨트롤러로서)
- Java bean for encapsulations (Bean 자료형 기반 캡슐화 기능으로서)
- PHP as views (PHP 기반의 뷰로서)

#### Defining thrift.idl file

- Define the encapsulating structs, methods and exception.

- Warning : The order of Bean or Structs is very important since the idl compiling mechanism is forward declarations.

```c
namespace java thrift.gen.javacode // Namespace for java and this namespace will be the package
namespace php ThriftService // Namespace for PHP and this namespace will be the directory of PHP

typedef i16 short // Type redefinition for convenience
typedef i32 int // Type redefinition for convenience
typedef i64 long // Type redefinition for convenience
typedef string String // Type redefinition for convenience
typedef binary ThriftByteBuffer // Type redefinition for convenience
typedef bool boolean // Type redefinition for convenience

exception ThriftServiceException { 1 : int ecode, 2 : String emsg } // Exception definition

struct ThriftUserBean{
	1. int id
	2: String userId
	3: String userPw
	4: String NAME
	5: String DATE
} 

service ThriftService { // Handler Method Interfaces for General Service
	String duplicateUserId(1:String id) throws (1:ThriftServiceException se);
	list<ThriftFileBean> imgFileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se); 
	ThriftUserBean getUserInfo(1:String id) throws (1:ThriftServiceException se);
	list<ThriftFileBean> fileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se); 
	ThriftUserBean getTest(1:int userNumber) throws (1:ThriftServiceException se);
}

service ThriftAdminService { // Handler Method Interfaces for Admin Service
	list<ThriftFileBean> imgFileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se)  ; 
	list<ThriftFileBean> fileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se)  ; 
	ThriftUserBean getTest(1:int userNumber) throws (1:ThriftServiceException se)  ; 
} 
```
#### Compiling with Thrift.exe

- Run gen.bat to generate interfaces and required files

    * ThriftService.java and ThriftAdminService.java will be generated.
    * The files that automatically generated are not necessary to check out.
    * In directory named server, there are 2 handlers and it is for processing client's requests and providing responses.

- Example code snippet in ThriftServiceHandler.java
```java
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
```
- As the code shows, Handlers provide just the data as somewhat routes like controller in *Spring Frameworks*. But the *data type* as well as ThriftUserResult must be declared as a Java bean.

#### Implementing Service

- There is a directory named svc in core then it is necessary that Implementation needs to be located in here.

- The Implementing code in svc file must be named exactly the same with method name in *Handler* and *thrift.idl* file. In addition, the detail business logics also need to be implemented in here.

- Example code snippet in SvcUser.java
```java
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
```

#### Defining Databases query and its Mapper with mybatis

- With mybatis, there is no need to implement in java

	* Declare required method in interfaces ~Mapper.java.

	* *Implement* detail query in ~Mapper.xml files with [mybatis syntax](http://www.mybatis.org/mybatis-3/ko/sqlmap-xml.html).

- Example queries
```xml
<mapper namespace="core.logic.mybatis.mapper.UserMapper">
    
    <select id="getUserInfo" parameterType="String" resultType="UserResult">
        SELECT 
        	id
        	, userId
        	, userPw
        	, name
        	, date
        FROM
        	tbl_richware_user
        WHERE
        	userId = #{id}
    </select>
```
	* the account information or DB connection settings would be asserted in config.xml.

	* *IMPORTANT* : User-defined Data type or bean must be aliased in config.xml.

- Example code for showing Aliases and db settings
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  
<configuration>
    
    <settings>  
        <setting name="cacheEnabled"              value="true"/>  
        <setting name="lazyLoadingEnabled"        value="false"/>  
        <setting name="multipleResultSetsEnabled" value="true"/>  
        <setting name="useColumnLabel"            value="true"/>  
        <setting name="useGeneratedKeys"          value="false"/>  
        <setting name="defaultExecutorType"       value="SIMPLE"/>  
    </settings>    
	
	<typeAliases>
		<typeAlias alias="UserBean" type="core.logic.bean.persistence.UserBean"/>
		<typeAlias alias="UserResult" type="core.logic.bean.result.UserResult"/>
		<typeAlias alias="BoardBean" type="core.logic.bean.persistence.BoardBean"/>
		<typeAlias alias="ListBean" type="core.logic.bean.persistence.ListBean"/>
	</typeAliases>
	    
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC"/>
			<dataSource type="POOLED">
			    
				<property name="driver" value="net.sf.log4jdbc.DriverSpy"/>  
			    <property name="url" value="jdbc:log4jdbc:mysql://182.161.118.74:3306/test?useUnicode=yes&amp;characterEncoding=UTF-8"/>
			    <property name="username" value="test"/>  
			    <property name="password" value="$#@!test"/>
			     
			</dataSource>
		</environment>
	</environments>
	 
	<mappers>
		<mapper resource="core/logic/mybatis/query/UserMapper.xml"/>
	</mappers>
	
</configuration>
```

#### Getting started for thrift Compiling

- Generating thrift.gen file is integral part for running.

- In your thrift directory, run the command as follow.

```sh
C:\thrift -out C:\Users\a\workspace\thrift_share\src --gen java:beans C:\Users\a\workspace\thrift\src\tool\thrift\idl\thrift.idl
C:\thrift -out C:\Users\a\workspace\thrift_share\src\thrift\gen\php  --gen php:oop C:\Users\a\workspace\thrift\src\tool\thrift\idl\thrift.idl
```

#### Going on to view part

- Click the [Link](https://github.com/yjham2002/Thrift_tutorial_PHP) to go next
