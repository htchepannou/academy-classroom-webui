package io.tchepannou.www.academy.support.jetty;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class HandlerStub extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerStub.class);

    private int status = 200;

    @Override
    public void handle(
            final String s,
            final Request request,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse
    ) throws IOException, ServletException {
        LOGGER.info(request.getMethod() + " " + request.getRequestURI());

        final String name = "/stub" + s + "/data.json";
        final InputStream in = getClass().getResourceAsStream(name);
        if (in == null){
            httpServletResponse.setStatus(404);
            return;
        }

        final String content = IOUtils.toString(in, "utf-8");
        httpServletResponse.getWriter().println(content);
        httpServletResponse.addHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(status);
        request.setHandled(true);

//        LOGGER.info(content);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }
}
