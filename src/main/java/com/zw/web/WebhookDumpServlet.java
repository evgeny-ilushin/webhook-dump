package com.zw.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class WebhookDumpServlet extends HttpServlet {
    protected static final Logger logger = LoggerFactory.getLogger(WebhookDumpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAnything(req, resp);
    }

    protected void doAnything(HttpServletRequest req, HttpServletResponse resp) {
        String reqData = null;
        try {
            reqData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            String headers =
                    Collections.list(req.getHeaderNames()).stream()
                            .map(headerName -> headerName + ": " + req.getHeader(headerName))
                            .collect(Collectors.joining("\n"));

            String parameters =
                    Collections.list(req.getParameterNames()).stream()
                            .map(p -> p + "=" + Arrays.asList( req.getParameterValues(p)))
                            .collect(Collectors.joining("&"));

            String reqInfo = req.getMethod() + " " + req.getRequestURI() + (req.getQueryString() == null? "" : ("?" + req.getQueryString()));

            logger.info("Request:\n{}", reqInfo);
            logger.info("Headers:\n{}", headers);
            logger.info("Data:\n{}\n", reqData);

            String userResponse = "{ \"user_id\": 123 }";
            resp.setHeader("X-TrackerToken", UUID.randomUUID().toString());
            //userResponse.append("<html><body><pre>");

            /*
            userResponse.append(reqInfo + "\n\n");
            userResponse.append(headers);

            if (reqData != null) {
                userResponse.append("\n\n");
                userResponse.append(reqData);
                userResponse.append("\n");
            }
            */

            //userResponse.append("</pre></body></html>");

            resp.setContentType("application/json");
            resp.getOutputStream().write(userResponse.toString().getBytes("utf-8"));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        catch (Exception e) {
            logger.error("Error accepting request: {} {}", e.getLocalizedMessage(), e);
        }
    }
}
