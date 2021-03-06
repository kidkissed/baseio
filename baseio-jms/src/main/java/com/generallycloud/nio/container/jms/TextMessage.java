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
package com.generallycloud.nio.container.jms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class TextMessage extends BasicMessage{
	
	private String text = null;

	public TextMessage(String messageID,String queueName,String text) {
		super(messageID,queueName);
		this.text = text;
	}

	public String getReadText() {
		return text;
	}

	@Override
	public int getMsgType() {
		return Message.TYPE_TEXT;
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder(24)
			.append("{\"msgType\":2,\"msgID\":\"")
			.append(getMsgID())
			.append("\",\"queueName\":\"")
			.append(getQueueName())
			.append("\",\"timestamp\":")
			.append(getTimestamp())
			.append(",\"text\":\"")
			.append(getText0())
			.append("\"}")
			.toString();
	}
	
	protected String getText0(){
		if (text == null) {
			return "";
		}
		return text;
	}

	public static void main(String[] args) {
		
		
		
		TextMessage message = new TextMessage("mid","qname",null);
		
		System.out.println(JSON.toJSON(message).toString());
		System.out.println(message.toString());
	}
}
