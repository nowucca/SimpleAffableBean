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
package business.order;

import java.util.Date;


/**
 *
 */
public class CustomerOrder {

    private long customerOrderId;
    private long customerId;
    private int amount;
    private Date dateCreated;
    private int confirmationNumber;


    public CustomerOrder() {
    }


    public CustomerOrder(long customerOrderId, long customerId, int amount, Date dateCreated, int confirmationNumber) {
        this.customerOrderId = customerOrderId;
        this.customerId = customerId;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.confirmationNumber = confirmationNumber;
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }


    @Override
    public String toString() {
        return "business.order.CustomerOrder[customerOrderId=" + customerOrderId + "]";
    }

}
