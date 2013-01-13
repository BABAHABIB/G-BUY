/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.filter;

import com.gbuy.entities.Utilisateur;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Anas
 */
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect("../login.xhtml");
        } else {
            Utilisateur usr = (Utilisateur) req.getSession().getAttribute("user");
            if (usr.getType() == null || !usr.getType().equals("ADMIN")) {
                res.sendRedirect("../accessdenied.xhtml");
            } else {

                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
