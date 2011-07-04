// Copyright (c) Microsoft Corporation. All rights reserved.

/**
 * 
 */
package com.JSONObjectSerialization;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * @author dimast
 * 
 * This class can serialize classes to JSON strings.
 * 
 * This works for classes that contain only primitive fields.
 *
 */
public class JSONOutputStream extends OutputStream
{
	/**
	 * Creates the stream.
	 * 
	 * @param os The output stream that receives the JSON output
	 */
	public JSONOutputStream(final OutputStream os) 
	{
		_os = os;
	}
	
	@Override
	public final void close() 
	{
		if (_os != null) 
		{
			try 
			{
				_os.close();
			} 
			catch (IOException e) 
			{
				Log.e("JSONOutputStream",
						String.format("Can't close output stream: %s", e.toString()));
			}
		}
	}
	
	/**
	 * Writes the JSON object representation to the output stream. 
	 * 
	 * @param object The object to that will be converted to JSON stream.
	 */
	public final void writeObject(final Object object)
	{
		Field[] fields = object.getClass().getDeclaredFields();
		JSONObject jsonObj = new JSONObject();
		for (Field field : fields)
		{
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers))
			{
				// skip statics
				continue;
			}
			
			try 
			{
				field.setAccessible(true);
				jsonObj.put(field.getName(), field.get(object));
			} 
			catch (IllegalArgumentException e) 
			{
				Log.e("JSONOutputStream",
						String.format("Failed serializing field [%s] %s", field.getName(), e.toString()));
				continue;
			} 
			catch (JSONException e) 
			{
				Log.e("JSONOutputStream",
						String.format("Failed serializing field [%s] %s", field.getName(), e.toString()));
				continue;
			} 
			catch (IllegalAccessException e) 
			{
				Log.e("JSONOutputStream",
						String.format("Failed serializing field [%s] %s", field.getName(), e.toString()));
				continue;
			}
		}
		
		if (_os != null)
		{
			try 
			{
				// the below uses platform default char set which is UTF-8
				// http://developer.android.com/reference/java/nio/charset/Charset.html
				_os.write(jsonObj.toString().getBytes());
			} 
			catch (IOException e) 
			{
				Log.e("JSONOutputStream",
						String.format("Failed writing to output stream: %s", e.toString()));
			}
		}
		else
		{
			Log.e("JSONOutputStream", "Can't write to output stream. _os is null.");
		}
	}
	
	@Override
	@Deprecated
	public void write(final int oneByte) throws IOException 
	{
		// do nothing
	} 
	
	private OutputStream _os;
}
