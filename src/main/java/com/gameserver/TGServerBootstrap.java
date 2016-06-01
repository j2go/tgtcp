package com.gameserver;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gameserver.aop.Interceptor;
import com.gameserver.datalayer.protocol.Protocol;
import com.gameserver.net.codec.HeaderDecoder;
import com.gameserver.net.codec.HeaderEncoder;
import com.gameserver.net.codec.decoder.ProtobufDecoder;
import com.gameserver.net.codec.encoder.ProtobufEncoder;
import com.gameserver.util.SpringContainer;
import com.google.protobuf.ExtensionRegistry;

/**
 * 服务器启动程序
 * 
 * @author Administrator
 * 
 */
public class TGServerBootstrap {

	private static final Logger logger = LoggerFactory.getLogger(TGServerBootstrap.class);

	public int port = 8801;

	final ServerHandler serverHandler = new ServerHandler();

	public void registerInterceptor(Interceptor interceptor) {
		serverHandler.interceptors.add(interceptor);
	}

	public void startup() {

		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory());
		final ExtensionRegistry registry = ExtensionRegistry.newInstance();
		Protocol.registerAllExtensions(registry);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			public ChannelPipeline getPipeline() {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new HeaderDecoder());
				pipeline.addLast("pDecoder", new ProtobufDecoder(Protocol.Request.getDefaultInstance(), registry));

				pipeline.addLast("encoder", new HeaderEncoder());
				pipeline.addLast("pEncoder", new ProtobufEncoder());
				pipeline.addLast("handler", serverHandler);
				return pipeline;
			}
		});

		bootstrap.bind(new InetSocketAddress(port));

		SpringContainer.getInstance().loadSpring();

		logger.info("#listen port " + port + "success");
	}

}
