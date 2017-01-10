package core.common.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class ThisUtil
{
	
	/**
	 * 레벨을 문자로 변환
	 * @param level
	 * @return
	 */
	public static String getStrLevel(int level) 
	{
		String str = null ;
		
		switch( level )
		{
			case 1 :
				str = "초보" ;
				break ;
			case 2 : 
				str = "중급" ;
				break ;
			default :
				str = "에러" ;
				break ;			
				
		}
		
		return str ;
	}
	// 단방향 암호화
	public static String makeSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}	
	
	public static void paging(int totalCount, int cpage, Map<String, Object> params){
		int pageSize = VarConst.paging.CONST_ROW_SIZE_10;
		cpage = (cpage == 0) ? 1 : cpage;
		
		int maxPage = totalCount / pageSize; 
		if(totalCount % pageSize != 0)
			maxPage++;
		if (cpage > maxPage && maxPage != 0)
			cpage = maxPage;
		int offset = (cpage - 1) * pageSize;
		
		params.put("pageSize", pageSize);
		params.put("offset", offset);		
	}
	
	public static String getAbsoluteBasePath() throws Exception
	{
		File file = new File("..");
		return file.getCanonicalPath();
	}

	/**
	 * 절대경로 구함(파일 패스 포함)
	 * 
	 * @param path
	 * @return
	 */
	public static String getAbsoluteBasePath(String path) throws Exception
	{
		File file = new File(path);
		System.out.println(file.getParentFile().getAbsolutePath());
		return file.getAbsolutePath();
	}	
	
	
	public static String getTimeMsg(String regDate) throws ParseException{
		String dataTime = regDate;
		Calendar cal = Calendar.getInstance();
		long time = System.currentTimeMillis();
		
		Date today = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(cal.getTime()).toString();
		
		int year = Integer.valueOf(regDate.substring(0, 4));
		int diffYear = Integer.valueOf(date.substring(0, 4));
		
		
		Date beforeDate = dateFormat.parse(dataTime);
		//Date nowDate = dateFormat.parse(date);
		long nowDate = today.getTime();
		
		long diff = nowDate - beforeDate.getTime();
		//System.out.println(diff);
		long diffTime = (diff / (1000*60));
		long getTime = 0L;
		long dayTime = 0L;
		String getTimeMsg = "";
		//현재 2016 등록일자는 2015면 yyyy/MM/dd
		
		if(diffYear == year){
			if(diffTime < 60){
				getTime = diffTime;
				if(diffTime < 5){
					getTime = 1;
				}
				else if(diffTime < 10){
					getTime = 5;
				}
				else if(diffTime < 30){
					getTime = 10;
				}
				else{
					getTime = 30;
				}
				getTimeMsg = getTime + "분전";
			}
			if(diffTime > 59 && diffTime < 60 * 24){
				getTime = (diff / (1000*60*60));
				getTimeMsg = getTime + "시간전";
				
			}
			if(diffTime > (60 * 24)-1){
				dayTime = (diff / (1000*60*60*24));
			}
			
			switch(Integer.valueOf(String.valueOf(dayTime))){
				case 1 :
					getTimeMsg = "어제";
					break;
				case 2 :
					getTimeMsg = "이틀전";
					break;
				case 3 :  
				case 4 :
				case 5 :
				case 6 :
					getTimeMsg = dayTime + "일전";
					break;
			}
			if(dayTime > 6){
				SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd HH:mm");
				Date parseDate = dateFormat.parse(dataTime);
				getTimeMsg = dateParser.format(parseDate);
			}
		}
		else{
			SimpleDateFormat dateParser = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date parseDate = dateFormat.parse(dataTime);
			getTimeMsg = dateParser.format(parseDate);			
		}
		return getTimeMsg;
	}
}
