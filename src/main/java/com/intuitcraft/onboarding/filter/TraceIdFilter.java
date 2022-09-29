package com.intuitcraft.onboarding.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter()
public class TraceIdFilter implements Filter {
    @Autowired
    Tracer tracer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Correlation-Id", tracer.currentSpan().context().traceId());
        filterChain.doFilter(servletRequest, httpServletResponse);
    }
}
