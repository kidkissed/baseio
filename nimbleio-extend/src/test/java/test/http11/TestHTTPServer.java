package test.http11;

import com.generallycloud.nio.acceptor.TCPAcceptor;
import com.generallycloud.nio.acceptor.UDPAcceptor;
import com.generallycloud.nio.common.LifeCycleUtil;
import com.generallycloud.nio.common.LoggerFactory;
import com.generallycloud.nio.component.DefaultNIOContext;
import com.generallycloud.nio.component.LoggerSEListener;
import com.generallycloud.nio.component.NIOContext;
import com.generallycloud.nio.component.protocol.http11.ServerHTTPProtocolFactory;
import com.generallycloud.nio.extend.ApplicationContext;
import com.generallycloud.nio.extend.FixedIOEventHandle;
import com.generallycloud.nio.extend.configuration.FileSystemACLoader;
import com.generallycloud.nio.extend.service.FutureAcceptorHttpFilter;


public class TestHTTPServer {

	public void launch() throws Exception {
		
		ApplicationContext applicationContext = new ApplicationContext();
		
		NIOContext context = new DefaultNIOContext();
		
		TCPAcceptor acceptor = new TCPAcceptor();
		
		UDPAcceptor udpAcceptor = new UDPAcceptor();
		
		try {
			
			FileSystemACLoader fileSystemACLoader = new FileSystemACLoader();
			
			applicationContext.setLastServiceFilter(new FutureAcceptorHttpFilter(applicationContext.getClassLoader()));
			applicationContext.setConfigurationLoader(fileSystemACLoader);
			applicationContext.setContext(context);
			
			context.setIOEventHandleAdaptor(new FixedIOEventHandle(applicationContext));
			
			context.addSessionEventListener(new LoggerSEListener());
			
			context.setProtocolFactory(new ServerHTTPProtocolFactory());
			
			acceptor.setContext(context);
			
			acceptor.bind();
			
			udpAcceptor.setContext(context);
			
			udpAcceptor.bind();

		} catch (Throwable e) {
			
			LoggerFactory.getLogger(TestHTTPServer.class).error(e.getMessage(), e);
			
			LifeCycleUtil.stop(applicationContext);
			
			acceptor.unbind();
			
			udpAcceptor.unbind();
		}
	}

	public static void main(String[] args) throws Exception {
		TestHTTPServer launcher = new TestHTTPServer();

		launcher.launch();
	}
}
