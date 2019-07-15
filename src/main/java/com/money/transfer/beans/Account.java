package com.money.transfer.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * This Class is to store account data
 * Created by Vishwanath on 15/07/2019.
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 4738762788882L;

    @JsonProperty
    private String acctNumber;
    @JsonProperty
    private String sortCode;
    @JsonProperty
    private String acctUser;
    @JsonProperty
    private double acctBalance;

    private String createdBy;
    private Date createdDt;
    private String modifiedBy;
    private Date modifiedDt;
}
