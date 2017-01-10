# Thrift based server with JAVA and PHP

### This document is about the order of processes on constructing Apache thrift based server with making simple website with a board that allows files to be uploaded

- This cause reason why this project provides multi-language.

##### This Project runs with
- Thrift as a multiplexer
- C styled IDL as a base declaration
- mybatis as a modeller
- JAVA as a controller
- Java bean for encapsulations
- PHP as views

1. Defining thrift.idl file

- Define the encapsulating structs, methods and exception.

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
2. Compiling with Thrift.exe

- Run gen.bat to generate interfaces and required files

    * ThriftService.java and ThriftAdminService.java will be generated.
    * The files that automatically generated are not necessary to check out.
    * In directory named server, there are 2 handlers and it is for processing client's requests and providing responses.

