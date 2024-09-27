package com.redhat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnonymousAuthFilter extends AnonymousAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(AnonymousAuthFilter.class);

    public AnonymousAuthFilter() {
        super("PROXY_AUTH_FILTER");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(createAuthentication((HttpServletRequest) req));
        log.info("SecurityContextHolder pre-auth user: {}",  SecurityContextHolder.getContext());

        if (log.isDebugEnabled()) {
            log.debug("Populated SecurityContextHolder with authenticated user: {}",
                    SecurityContextHolder.getContext().getAuthentication());
        }

        chain.doFilter(req, res);
    }

    @Override
    protected Authentication createAuthentication(final HttpServletRequest request)
            throws AuthenticationException {

        //log.info("<ANONYMOUS_USER>");
        log.info("Service_Group");

        List<? extends GrantedAuthority> authorities = Collections
                .unmodifiableList(Arrays.asList(new SimpleGrantedAuthority("kie-server"), new SimpleGrantedAuthority("rest-all"), new SimpleGrantedAuthority("hr")
                ));
        return new AnonymousAuthenticationToken("ANONYMOUS", "Service_Group", authorities);
    }

}
