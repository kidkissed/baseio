package com.gifisan.nio.plugin.rtp.server;

import java.io.IOException;

import com.gifisan.nio.common.Logger;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.component.UDPEndPoint;
import com.gifisan.nio.component.protocol.DatagramPacket;
import com.gifisan.nio.server.IOSession;
import com.gifisan.nio.server.ServerDPAcceptor;

public class RTPServerDPAcceptor extends ServerDPAcceptor {
	
	public static final String SERVICE_NAME = RTPServerDPAcceptor.class.getSimpleName();
	
	private Logger logger = LoggerFactory.getLogger(RTPServerDPAcceptor.class);
	
	private RTPContext context = null;
	
	protected RTPServerDPAcceptor(RTPContext context) {
		this.context = context;
	}

	public void doAccept(UDPEndPoint endPoint, DatagramPacket packet,IOSession session) throws IOException {

		RTPSessionAttachment attachment = (RTPSessionAttachment)session.getAttachment(context);
		
		RTPRoom room = attachment.getRtpRoom();
		
		if (room != null) {
			room.broadcast(endPoint, packet);
		}else{
			logger.debug("___________________null room,packet:{}",packet);
		}
	}

	protected String getSERVICE_NAME() {
		return SERVICE_NAME;
	}
}