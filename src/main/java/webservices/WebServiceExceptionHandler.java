/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
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
package webservices;

import java.io.StringWriter;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Provider
@Priority(Priorities.USER)
public class WebServiceExceptionHandler implements ExceptionMapper<WebServiceException> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(WebServiceException exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof WebServiceException.AuthenticationFailure) {
            status = Response.Status.UNAUTHORIZED;
        } else if (exception instanceof WebServiceException.InvalidParameter) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof WebServiceException.NotFound) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof WebServiceException.NotAllowed) {
            status = Response.Status.FORBIDDEN;
        } else if (exception instanceof WebServiceException.PaymentNeeded) {
            status = Response.Status.PAYMENT_REQUIRED;
        } else if (exception instanceof WebServiceException.MaxQueueSizeExceeded) {
            status = Response.Status.CONFLICT;
        } else {
            logger.warn("Got an internal server error ", exception);
        }
        try {
            StringWriter writer = new StringWriter();
            writer.append(status.getReasonPhrase()).append(" ").append(String.valueOf(status.getStatusCode())).append("\n\n").append(exception.getMessage());
            return Response.status(status).entity(writer.getBuffer().toString()).type(MediaType.TEXT_PLAIN).build();
        } catch (Exception e) {
            logger.error("Problem attempting to map a PortalException to a json response", e);
            logger.error("Original PortalException is - ", exception);
            return Response.serverError().build();
        }
    }
}
