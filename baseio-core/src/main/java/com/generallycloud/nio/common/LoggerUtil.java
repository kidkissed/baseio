/*
 * Copyright 2015 GenerallyCloud.com
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.generallycloud.nio.common;


public class LoggerUtil {

	private static int	maxNameLength	= "FutureAcceptorServiceLoader".length();

	private static String getSpace(Logger logger) {

		Class<?> clazz = logger.getLoggerClass();

		String name = clazz.getSimpleName();

		int length = name.length();

		int _length = maxNameLength - length;

		if (_length == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for (; _length > 0; _length--) {
			builder.append(" ");
		}

		return builder.toString();
	}

	public static void prettyNIOServerLog(Logger logger, String msg) {

		msg = getSpace(logger) + "[NIOServer] " + msg;

		logger.info(msg);
	}

	public static void prettyNIOServerLog(Logger logger, String msg, Object param1) {

		msg = getSpace(logger) + "[NIOServer] " + msg;

		logger.info(msg, param1);
	}

	public static void prettyNIOServerLog(Logger logger, String msg, Object param1, Object param2) {
		msg = getSpace(logger) + "[NIOServer] " + msg;

		logger.info(msg, param1, param2);
	}

	public static void prettyNIOServerLog(Logger logger, String msg, Object[] param) {
		msg = getSpace(logger) + "[NIOServer] " + msg;

		logger.info(msg, param);
	}
}
