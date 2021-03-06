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
package com.generallycloud.nio.container.http11.example;

import com.generallycloud.nio.acceptor.ChannelAcceptor;
import com.generallycloud.nio.codec.http11.HttpSession;
import com.generallycloud.nio.codec.http11.future.HttpReadFuture;
import com.generallycloud.nio.common.CloseUtil;
import com.generallycloud.nio.common.Logger;
import com.generallycloud.nio.common.LoggerFactory;
import com.generallycloud.nio.common.ThreadUtil;
import com.generallycloud.nio.component.SocketChannelContext;
import com.generallycloud.nio.container.http11.service.HttpFutureAcceptorService;

public class TestStopServerServlet extends HttpFutureAcceptorService {

	private Logger				logger		= LoggerFactory.getLogger(TestStopServerServlet.class);

	@Override
	protected void doAccept(HttpSession session, HttpReadFuture future) throws Exception {
		
		SocketChannelContext context = session.getIoSession().getContext();
		
		new Thread(new StopServer(context)).start();
		
		future.write("服务端正在处理停止服务命令...");
		
		session.flush(future);
		
	}

	private class StopServer implements Runnable {
		
		private SocketChannelContext context = null;

		public StopServer(SocketChannelContext context) {
			this.context = context;
		}

		@Override
		public void run() {

			ThreadUtil.sleep(500);

			logger.info("   [NIOServer] 执行命令：<停止服务>");

			String[] words = new String[] { "五", "四", "三", "二", "一" };

			for (int i = 0; i < 5; i++) {

				logger.info("   [NIOServer] 服务将在" + words[i] + "秒后开始停止，请稍等");

				ThreadUtil.sleep(1000);
			}
			
			CloseUtil.unbind((ChannelAcceptor) context.getChannelService());
		}
	}
}
