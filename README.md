# Thrift based server with JAVA and PHP

### This document is about the order of processes on constructing Apache thrift based server

##### This Project runs with
- Thrift as a multiplexer
- C styled IDL as a base declaration
- mybatis as a modeller
- JAVA as a controller
- Java bean for encapsulations
- PHP as views

1. Defining thrift.idl file
```
namespace java thrift.gen.javacode
namespace php ThriftService

typedef i16 short
typedef i32 int
typedef i64 long
typedef string String
typedef binary ThriftByteBuffer
typedef bool boolean

exception ThriftServiceException { 1 : int ecode, 2 : String emsg }

struct ThriftUserBean{
	1. int id
	2: String userId
	3: String userPw
	4: String NAME
	5: String DATE
} 

service ThriftService {  
	String duplicateUserId(1:String id) throws (1:ThriftServiceException se);
	list<ThriftFileBean> imgFileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se); 
	ThriftUserBean getUserInfo(1:String id) throws (1:ThriftServiceException se);
	list<ThriftFileBean> fileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se); 
	ThriftUserBean getTest(1:int userNumber) throws (1:ThriftServiceException se);
}

service ThriftAdminService {  
	list<ThriftFileBean> imgFileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se)  ; 
	list<ThriftFileBean> fileUpload(1:list<ThriftFileBean> fileBeans) throws (1:ThriftServiceException se)  ; 
	ThriftUserBean getTest(1:int userNumber) throws (1:ThriftServiceException se)  ; 
} 
```
