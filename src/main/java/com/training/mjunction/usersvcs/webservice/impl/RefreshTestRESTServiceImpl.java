package com.training.mjunction.usersvcs.webservice.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.training.mjunction.usersvcs.webservice.RefreshTestRESTService;

@Service
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class RefreshTestRESTServiceImpl implements RefreshTestRESTService {

	@Value("${test.refresh.msg:Hi!}")
	private String mgs;

	@Override
	public Response refreshTest() {
		return Response.ok().entity("{\"refreshed\":\"" + mgs + "\"}").build();
	}

}
