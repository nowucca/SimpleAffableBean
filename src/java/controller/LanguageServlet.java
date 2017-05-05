/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
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
