package com.thoughtworks.selenium.grid.hub;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Provides feedback that the Hub is still up and running
 * with minimal performance impact on other Hub operations.
 */
public class HeartbeatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reply(response);
    }

    protected void reply(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Hub : OK");
    }

}
