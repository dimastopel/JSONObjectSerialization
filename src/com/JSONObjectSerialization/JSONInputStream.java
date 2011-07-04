package com.JSONObjectSerialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author dimast
 * 
 * This class can deserialize classes from JSON strings.
 * 
 * This works for classes that contain only primitive fields.
 * 
 */
public class JSONInputStream extends InputStream 
{
	/**
	 * Creates the object.
	 * 
	 * @param is Input stream that contains the JSON content
	 */
	public JSONInputStream(final InputStream is)
	{
		_is = is;
		_json = null;
	}
	
	/**
	 * Creates the object.
	 * 
	 * @param json Input string that contains the JSON content
	 */
	public JSONInputStream(final String json)
	{
		_json = json;
		_is = null;
	}

	/**
	 * Creates the object from the stream content. 
	 * @param <T> The type of the object 
	 * @param objClass The class of the object
	 * @throws JSONStreamException in case parsing error is occurred
	 * @return the instance of the object created from the stream content
	 */
	public final <T> T readObject(final Class<T> objClass) throws JSONStreamException
	{
		return readObject(objClass, null);		
	}

	/**
	 * Creates the object from the stream content. 
	 * @param <T> The type of the object 
	 * @param objClass The class of the object
	 * @param initialObj Initial object version
	 * @throws JSONStreamException in case parsing error is occurred
	 * @return the instance of the object created from the stream content
	 */
	public final <T> T readObject(final Class<T> objClass, final T initialObj) throws JSONStreamException
	{
		if (_is == null && _json == null)
		{
			throw new JSONStreamException("Can't read object, the input is null.");
		}
		
		if (_json == null)
		{
			try 
			{
				// the below uses platform default char set which is UTF-8
				// http://developer.android.com/reference/java/nio/charset/Charset.html
				_json = readStreamTillEnd(_is, null);
			} 
			catch (Exception e) 
			{
				throw new JSONStreamException("Can't read input stream.", e);
			}
		}
		
		JSONObject jsonObj = null;
		try 
		{
			jsonObj = new JSONObject(_json);
		} 
		catch (JSONException e) 
		{
			throw new JSONStreamException("Can't deserialize the object. Failed to create JSON object.", e);
		}

		T object = null;
		if (initialObj != null)
		{
			object = initialObj;
		}
		else
		{
			try 
			{
				object = objClass.newInstance();
			} 
			catch (InstantiationException e) 
			{
				throw new JSONStreamException("Can't deserialize the object. Failed to instantiate object.", e);
			} 
			catch (IllegalAccessException e) 
			{
				throw new JSONStreamException("Can't deserialize the object. Failed to instantiate object.", e);
			}
		}		
		
		Field[] fields = objClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (Modifier.isStatic(field.getModifiers()))
			{
				// skip static
				continue;
			}
			
			try 
			{
				if (jsonObj.has(field.getName()))
				{
					Log.i("JSONInputStream", 
							String.format("Updating field [%s]", field.getName()));
					field.setAccessible(true);
					field.set(object, jsonObj.get(field.getName()));
				}
				else
				{
					Log.i("JSONInputStream", 
							String.format("Field [%s] doesn't exist. Keeping default value.", field.getName()));
				}
			} 
			catch (IllegalArgumentException e) 
			{
				Log.e("JSONInputStream", 
						String.format("Failed to get field [%s] %s", field.getName(), e.toString()));
				continue;
			} 
			catch (JSONException e) 
			{
				Log.e("JSONInputStream", 
						String.format("Failed to get field [%s] %s", field.getName(), e.toString()));
				continue;
			} 
			catch (IllegalAccessException e) 
			{
				Log.e("JSONInputStream", 
						String.format("Failed to get field [%s] %s", field.getName(), e.toString()));
				continue;
			}
		}
		
		return object;
	}

	@Override
	@Deprecated
	public final int read() throws IOException 
	{
		return 0;
	}
	
	/**
	 * Reads the input stream until and and produces String.
	 * @param is the input stream to read from
	 * @param enc the encoding, in case of null default encoding is used
	 * @return the string that represents stream's content
	 * @throws Exception on error
	 */
	private static String readStreamTillEnd(final InputStream is, final String enc) throws Exception
	{
		StringBuilder sb = new StringBuilder();
	    BufferedReader br = null;
		
	    try 
	    {
	    	if (enc != null)
	    	{
	    		br = new BufferedReader(new InputStreamReader(is, enc));
	    	}
	    	else
	    	{
				// the below uses platform default char set which is UTF-8
				// http://developer.android.com/reference/java/nio/charset/Charset.html
	    		br = new BufferedReader(new InputStreamReader(is));
	    	}
		} 
	    catch (Exception e) 
	    {
	    	Log.e("JSONInputStream", 
					String.format("Can't create BufferedReader [%s]", e.toString()));
			throw e;
		}
	    
	    // Read the stream until end
	    String line = "";
	    try 
	    {
			while ((line = br.readLine()) != null) 
			{ 
			    sb.append(line); 
			}
		} 
	    catch (IOException e) 
	    {
	    	Log.e("JSONInputStream", 
					String.format("Can't Can't read input stream [%s]", e.toString()));
			throw e;
		}
	    
	    
	    return sb.toString();
	}

	
	private InputStream _is;
	private String _json;
}
