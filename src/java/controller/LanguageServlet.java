/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
/**
 *
 */
@WebServlet(name = "Language",
            urlPatterns = {"/chooseLanguage"})
public class LanguageServlet extends SimpleAffableBeanServlet {

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("en", "cs");

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // get language choice
        String language = request.getParameter("language");

        // get relative url to redirect back to
        String relativeReturnUrl = request.getParameter("relativeReturnUrl");


        // make a locale out of the language
        Locale locale;
        if (isUnsupported(language)) {
            locale = getFallbackLocale(request);
        } else {
            locale = new Locale(language, "");
        }

        // establish jstl and response locale
        Config.set(session, Config.FMT_LOCALE, locale);
        response.setLocale(locale);

        doTemporaryRedirect(request, response, relativeReturnUrl);
    }


    private boolean isUnsupported(String language) {
        return language == null || language.isEmpty() || !SUPPORTED_LANGUAGES.contains(language);
    }

    private Locale getFallbackLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale;
        locale = (Locale) Config.get(session, Config.FMT_LOCALE);
        if (locale == null) {
            locale = request.getLocale();
            if (locale == null) {
                locale = (Locale) Config.get(session, Config.FMT_FALLBACK_LOCALE);
                if (locale == null) {
                    locale = Locale.getDefault();
                }
            }
        }
        return locale;
    }
}
