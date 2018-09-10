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


/**
 *
 */
public class WebServiceException extends RuntimeException {

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class AuthenticationFailure extends WebServiceException {
        public AuthenticationFailure(String message) {
            super(message);
        }

        public AuthenticationFailure(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class InvalidParameter extends WebServiceException {
        public InvalidParameter(String message) {
            super(message);
        }

        public InvalidParameter(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotFound extends WebServiceException {
        public NotFound(String message) {
            super(message);
        }

        public NotFound(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotAllowed extends WebServiceException {
        public NotAllowed(String message) {
            super(message);
        }

        public NotAllowed(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class PaymentNeeded extends WebServiceException {
        public PaymentNeeded(String message) {
            super(message);
        }

        public PaymentNeeded(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class MaxQueueSizeExceeded extends WebServiceException {
        public MaxQueueSizeExceeded(String message) {
            super(message);
        }

        public MaxQueueSizeExceeded(String message, Throwable t) {
            super(message, t);
        }
    }

}
