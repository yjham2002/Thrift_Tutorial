package core.common.util.push;

import java.util.HashMap;

public class DataBean
{

	private String					message		= "";
	private int						pushTypeID	= 0;
	private boolean					isAlim		= true;
	private HashMap<String, String>	customData	= new HashMap<String, String>();

	public DataBean(int pushTypeID, String message)
	{
		this.message = message;
		this.pushTypeID = pushTypeID;
	}

	public DataBean(int pushTypeID, String message, boolean isAlim)
	{
		this.message = message;
		this.isAlim = isAlim;
		this.pushTypeID = pushTypeID;
	}

	public DataBean(int pushTypeID, String message, boolean isAlim, HashMap<String, String> customData)
	{
		this.message = message;
		this.isAlim = isAlim;
		this.pushTypeID = pushTypeID;
		this.customData = customData;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean isAlim()
	{
		return isAlim;
	}

	public void setAlim(boolean isAlim)
	{
		this.isAlim = isAlim;
	}

	public int getPushTypeID()
	{
		return pushTypeID;
	}

	public void setPushTypeID(int pushTypeID)
	{
		this.pushTypeID = pushTypeID;
	}

	public HashMap<String, String> getCustomData()
	{
		return customData;
	}

	public void setCustomData(HashMap<String, String> customData)
	{
		this.customData = customData;
	}

	public void addCusomData(String key, String value)
	{
		this.customData.put(key, value);
	}

}
