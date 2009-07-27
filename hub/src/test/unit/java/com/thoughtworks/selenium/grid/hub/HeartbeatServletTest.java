package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class HeartbeatServletTest extends UsingClassMock {

    @Test
    public void replySetContentTypeAsPlainText() throws IOException {
        final Mock servletResponse;

        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("setContentType").with("text/plain");
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(new StringWriter(100))));

        new HeartbeatServlet().reply((HttpServletResponse) servletResponse);
        verifyMocks();
    }

    @Test
    public void replyWriteOKOnServletResponseAsPlainText() throws IOException {
        final StringWriter writer = new StringWriter(100);
        final Mock servletResponse;

        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(writer)));

        new HeartbeatServlet().reply((HttpServletResponse) servletResponse);
        assertEquals("Hub : OK", writer.getBuffer().toString());

        verifyMocks();
    }

}
