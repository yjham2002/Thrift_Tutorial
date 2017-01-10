package core.orika;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import com.appg.gpack.common.util.DateUtil;
import com.appg.gpack.common.util.GU;
import com.appg.gpack.db.mongo.anno.MongoCollection;
import com.appg.thrift.tool.annotation.SkipIDL;

/**
 * 데이터 변환
 * @author nukiboy
 *
 */
public class DateToStringConverter extends BidirectionalConverter<Date,String>{


	@Override
	public Date convertFrom(String arg0, Type<Date> arg1) {
		
		Date date = null ;
		
		try {
			
			if( GU.isNull(arg0) ) 
				return null ;
			else		
			{
				if( arg0.length() == 16 )
					date = DateUtil.parse(arg0,DateUtil.YMDHM_DOT) ;
				else 
				{
					date = DateUtil.parse(arg0,DateUtil.YMD_DOT) ;
					System.out.println("convertFrom" + date);
				}
				
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return date ;

	}

	@Override
	public String convertTo(Date arg0, Type<String> arg1) {
		
		String ret ="" ;		
				
		try 
		{
			if( arg0 == null )
				return "" ;
			else
			{				
				ret = DateUtil.getString(arg0,DateUtil.YMDHM_DOT2,Locale.KOREAN) ;			
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret ;				

	}
	
	
	

}
