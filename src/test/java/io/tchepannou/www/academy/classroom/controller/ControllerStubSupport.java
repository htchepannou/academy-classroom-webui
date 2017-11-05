package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.support.jetty.HandlerStub;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"stub"})
public class ControllerStubSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerStubSupport.class);

    @Value("${application.endpoint.academy.port}")
    private int academyServerPort;

    @Value("${application.endpoint.user.port}")
    private int userServerPort;


    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected HttpServletRequest request;

    private Server academyServer;
    private Server userServer;


    private Server startServer(final int port, final Handler handler) throws Exception{
        LOGGER.info("Starting HTTP server on port {}", port);

        final Server server = new Server(port);
        server.setHandler(handler);
        server.start();

        request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(new Cookie[]{
                new Cookie("guid", "12345678901234567890123456789012")
        });

        return server;
    }

    @Before
    public void setUp () throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        academyServer = startServer(academyServerPort, new HandlerStub());
        userServer = startServer(userServerPort, new HandlerStub());
    }

    @After
    public void tearDown() throws Exception {
        academyServer.stop();
        userServer.stop();
    }


}
