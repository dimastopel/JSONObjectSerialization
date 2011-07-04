// Copyright (c) Microsoft Corporation. All rights reserved.

/**
 * 
 */
package com.JSONObjectSerialization;

/**
 * @author dimast
 *
 *	This class represents exceptions being thrown by JSONInputStream and JSONOutputStream
 */
public class JSONStreamException extends Exception 
{
	/**
	 * Creates the exception with specified message.
	 * 
	 * @param message Exception details message
	 */
	public JSONStreamException(final String message)
	{
		super(message);
	}
	
	/**
	 * Creates the exception with specified message and cause.
	 * 
	 * @param message The detailed exception message 
	 * @param cause The exception cause
	 */
	public JSONStreamException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
	
	private static final long serialVersionUID = 63080950369617319L;

}
