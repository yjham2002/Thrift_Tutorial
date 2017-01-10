package core.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/***
 * Json 유틸
 * 
 * <br>
 * <br>
 * 2015-01-15
 * 
 * @author RICHWARE : SB
 * 
 */
public class JsonUtil
{

	private static boolean empty(String string)
	{
		if(string == null || string.equals(""))
			return false;

		return true;
	}

	public static class Obj
	{

		public static JSONObject create(String string)
		{
			if(!empty(string))
				return null;

			JSONObject json = null;

			try
			{
				json = new JSONObject(string);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return json;
		}

		public static boolean has(JSONObject json, String key)
		{
			if(json == null)
				return false;

			return json.has(key);
		}

		public static JSONObject getJSONObject(String jsonStr, String key)
		{
			return getJSONObject(create(jsonStr), key);
		}

		public static JSONObject getJSONObject(JSONObject json, String key)
		{
			if(json == null)
				return null;

			JSONObject getJson = null;

			try
			{
				getJson = json.getJSONObject(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return getJson;
		}

		public static JSONArray getJSONArray(String jsonStr, String key)
		{
			return getJSONArray(create(jsonStr), key);
		}

		public static JSONArray getJSONArray(JSONObject json, String key)
		{
			if(json == null)
				return null;

			JSONArray getJsonArray = null;

			try
			{
				getJsonArray = json.getJSONArray(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return getJsonArray;
		}

		public static String getString(JSONObject json, String key)
		{
			return getString(json, key, "");
		}

		public static String getString(JSONObject json, String key, String def)
		{
			if(json == null)
				return def;

			String value = def;

			try
			{
				value = json.getString(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static boolean getBoolean(JSONObject json, String key)
		{
			return getBoolean(json, key, false);
		}

		public static boolean getBoolean(JSONObject json, String key, boolean def)
		{
			if(json == null)
				return def;

			boolean value = def;

			try
			{
				value = json.getBoolean(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static int getInt(JSONObject json, String key)
		{
			return getInt(json, key, 0);
		}

		public static int getInt(JSONObject json, String key, int def)
		{
			if(json == null)
				return def;

			int value = def;

			try
			{
				value = json.getInt(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static long getLong(JSONObject json, String key)
		{
			return getLong(json, key, 0);
		}

		public static long getLong(JSONObject json, String key, long def)
		{
			if(json == null)
				return def;

			long value = def;

			try
			{
				value = json.getLong(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static double getDouble(JSONObject json, String key)
		{
			return getDouble(json, key, 0);
		}

		public static double getDouble(JSONObject json, String key, double def)
		{
			if(json == null)
				return def;

			double value = def;

			try
			{
				value = json.getDouble(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static Object get(JSONObject json, String key)
		{
			if(json == null)
				return null;

			Object value = null;

			try
			{
				value = (Object) json.get(key);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static void put(JSONObject json, String key, Object value)
		{
			if(json == null)
				return;
			try
			{
				json.put(key, value);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}
		}

	}

	public static class Arr
	{

		public static JSONArray create(String string)
		{
			if(!empty(string))
				return null;

			JSONArray json = null;

			try
			{
				json = new JSONArray(string);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return json;
		}

		public static JSONObject getJSONObject(JSONArray array, int idx)
		{
			if(array == null || array.length() == 0)
				return null;

			JSONObject value = null;

			try
			{
				value = array.getJSONObject(idx);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static String getString(JSONArray array, int idx)
		{
			if(array == null || array.length() == 0)
				return null;

			String value = null;

			try
			{
				value = array.getString(idx);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static String getString(JSONArray array, int idx, String key)
		{
			return getString(array, idx, key, "");
		}

		public static String getString(JSONArray array, int idx, String key, String def)
		{
			JSONObject json = getJSONObject(array, idx);
			return Obj.getString(json, key, def);
		}

		public static boolean getBoolean(JSONArray array, int idx)
		{
			if(array == null || array.length() == 0)
				return false;

			boolean value = false;

			try
			{
				value = array.getBoolean(idx);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static boolean getBoolean(JSONArray array, int idx, String key)
		{
			return getBoolean(array, idx, key);
		}

		public static boolean getBoolean(JSONArray array, int idx, String key, boolean def)
		{
			JSONObject json = getJSONObject(array, idx);
			return Obj.getBoolean(json, key, def);
		}

		public static int getInt(JSONArray array, int idx)
		{
			if(array == null || array.length() == 0)
				return 0;

			int value = 0;

			try
			{
				value = array.getInt(idx);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static int getInt(JSONArray array, int idx, String key)
		{
			return getInt(array, idx, key, 0);
		}

		public static int getInt(JSONArray array, int idx, String key, int def)
		{
			JSONObject json = getJSONObject(array, idx);
			return Obj.getInt(json, key, def);
		}

		public static double getDouble(JSONArray array, int idx)
		{
			if(array == null || array.length() == 0)
				return 0;

			double value = 0;

			try
			{
				value = array.getDouble(idx);
			}
			catch (JSONException e)
			{
				System.out.println(e.getMessage());
			}

			return value;
		}

		public static double getDouble(JSONArray array, int idx, String key)
		{
			return getDouble(array, idx, key, 0);
		}

		public static double getDouble(JSONArray array, int idx, String key, double def)
		{
			JSONObject json = getJSONObject(array, idx);
			return Obj.getDouble(json, key, def);
		}

	}

}
