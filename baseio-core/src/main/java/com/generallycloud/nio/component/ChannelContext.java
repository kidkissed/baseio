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
package com.generallycloud.nio.component;

import java.nio.charset.Charset;

import com.generallycloud.nio.Attributes;
import com.generallycloud.nio.LifeCycle;
import com.generallycloud.nio.buffer.MCByteBufAllocator;
import com.generallycloud.nio.configuration.ServerConfiguration;

public interface ChannelContext extends Attributes, LifeCycle {

	public abstract SessionManager getSessionManager();

	public abstract Charset getEncoding();

	public abstract ServerConfiguration getServerConfiguration();

	public abstract ChannelService getChannelService();

	public abstract void setChannelService(ChannelService service);

	public abstract Sequence getSequence();

	public abstract long getSessionIdleTime();

	public abstract long getStartupTime();

	public abstract MCByteBufAllocator getMcByteBufAllocator();
	
}