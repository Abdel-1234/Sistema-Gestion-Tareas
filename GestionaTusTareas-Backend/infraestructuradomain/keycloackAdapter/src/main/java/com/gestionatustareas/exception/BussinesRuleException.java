/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionatustareas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 *
 * @author sotobotero
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BussinesRuleException extends Exception{
  
    private long id;
    private String code;   
    private HttpStatus httpStatus;
    
    public BussinesRuleException(long id, String code, String message,HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BussinesRuleException(String code, String message,HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
    public BussinesRuleException(String message, Throwable cause) {
        super(message, cause);
    }     
    
}
