package core.common.util;

import java.util.HashMap;

/**
 * 내부에서 사용하는 상수 및 메세지 모음
 *
 */
public class VarConst {
	public static HashMap<Integer,HashMap<Integer, String>> ErrorMessageMap;
	
	public static interface ExcuteLevel{
		final int LIVE	 = 1000;
		final int LOCAL = 8000;
		final int TEST = 9000;
    }
	
	public static interface thriftPort{
		final int CONST_THRIFT_APP = 8502;
		final int CONST_THRIFT_ADMIN = 8503;
	}
	
	public static interface thriftThread{
		final int CONST_THRIFT_APP = 512;
		final int CONST_THRIFT_Admin = 8;
	}
	public static interface paging{
		// 1페이지에 보여줄 리스트 수
		final int CONST_ROW_SIZE_5 = 5;
		final int CONST_ROW_SIZE_10 = 10;
		final int CONST_ROW_SIZE_15 = 15;
		final int CONST_ROW_SIZE_20 = 20;
		final int CONST_ROW_SIZE = 20;
		
		// 블록 수
		final int CONST_BLOCK_SIZE_5 = 5;
		final int CONST_BLOCK_SIZE_10 = 10;
		final int CONST_BLOCK_SIZE_15 = 15;
		final int CONST_BLOCK_SIZE_20 = 20;
		final int CONST_BLOCK_SIZE = 5;
		
	}
	
	public static interface MessageType{
		final String JVASCRIPT = "JAVASCRIPT";
    }
	
	public static String getErrorMessage(int errorCode){
		return VarConst.getErrorMessage(VarConst.LangType.Kor,errorCode);
	}
	
	
	public static String getErrorMessage(int errorCode,String lang){
		int langType = VarConst.LangType.Kor;
		return VarConst.getErrorMessage(langType,errorCode);
	}

	public static interface LangType{
		final int Kor = 1;
    }
	
	public static interface CommonCode{
	}
	
	public static interface NoticeMsg{
	}
	
    public static interface ErrorCode{
    	final int QUERY_SUCCESS = 1;
    	final int NOT_DEFINE_ERROR = -99;
    	final int LOGIN_FAIL = -101;
    	final int SERVER_CHECK_ERROR = -307; // Periodical Checking
    }
    
	public static String getErrorMessage(int lang, int errorCode){
		if(VarConst.ErrorMessageMap == null){
			VarConst.ErrorMessageMap = new HashMap<Integer, HashMap<Integer, String>>();
			HashMap<Integer,String> engHash = new HashMap<Integer,String>();
			VarConst.ErrorMessageMap.put(VarConst.LangType.Kor, engHash);			
			{
				engHash.put(Integer.valueOf(VarConst.ErrorCode.QUERY_SUCCESS), "Success request.");
				engHash.put(Integer.valueOf(VarConst.ErrorCode.LOGIN_FAIL), "아이디, 비밀번호를 다시 확인해주세요.");
				engHash.put(Integer.valueOf(VarConst.ErrorCode.SERVER_CHECK_ERROR), "서버 정기 점검");
				engHash.put(Integer.valueOf(VarConst.ErrorCode.NOT_DEFINE_ERROR), "에러");
			}
		}
		if(VarConst.ErrorMessageMap.get(lang).containsKey(Integer.valueOf(errorCode)))
			return VarConst.ErrorMessageMap.get(lang).get(Integer.valueOf(errorCode)).toString();
		else
			return "Not defined errorcode message. Request errorcode message to system manager.";
	}
	
	
	public static interface MailString{
	}
	
	/**
	 * @brief 데이터 소스 
	 */
	public static enum DynamicDSType{
		DB1, //Master DB (Transaction)
		DB1_STATISTICS, //Master DB statistics(Transaction)
		DB2, //Slave DB (Only Select)
		DB2_STATISTICS, //Slave DB statistics(Only Select)
	}
	
	public static interface THUMB_SIZE{
		final String SIZE_140 = "/140x140";
		final String SIZE_190 = "/190x190";
		final String SIZE_230 = "/230x230";
		final String SIZE_480 = "/480x270";
	}
	
	/**
	 * Regular Expression 
	 */
	public static interface Pattern{
		final String MANAGER_ID_PATTERN = "^[a-zA-Z0-9_\\-\\.]{6,20}$";
		final String EMAIL_PATTERN = "[\\w\\-\\.]+@[\\w\\-]+(\\.[\\w\\-]+)+";
		final String APP_NAME_PATTERN="^[\\w~!@#$%^&*();+=\\[\\]\\{\\}\':\\\"\\\\|,./<>? ]{0,50}$";
		final String GEMSTORE_ID_PATTERN = MANAGER_ID_PATTERN;
	}

}
