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

import java.io.IOException;
import java.net.SocketAddress;

import com.generallycloud.nio.protocol.DatagramPacket;

public class DatagramSessionImpl extends SessionImpl implements DatagramSession {

	protected DatagramChannel		channel;

	protected DatagramChannelContext	context;

	public DatagramSessionImpl(DatagramChannel channel, Integer sessionID) {
		super(sessionID);
		this.channel = channel;
		this.context = channel.getContext();
	}

	@Override
	public void sendPacket(DatagramPacket packet, SocketAddress socketAddress) throws IOException {
		channel.sendPacket(packet, socketAddress);
	}

	@Override
	public void sendPacket(DatagramPacket packet) throws IOException {
		channel.sendPacket(packet);
	}

	@Override
	public DatagramChannelContext getContext() {
		return context;
	}

	@Override
	protected Channel getChannel() {
		return channel;
	}

}
