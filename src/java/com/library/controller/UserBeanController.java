/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.aspect.Util;
import com.library.model.UserAccount;
import com.library.service.UserAccountService;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author batman
 */
@Named("userBean")
@RequestScoped
public class UserBeanController implements java.io.Serializable {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;
    

    
    @Autowired
    private UserAccountService userService;

    public UserBeanController() {
    }

    public String registerUser() {
        UserAccount user = new UserAccount();
        user.setFirstname(getFirstname());
        user.setLastname(getLastname());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        
        System.out.println("Running method registerUser()");
        String status = userService.registerUser(user);
        if (status.equals("success")) {
            return "login";
        }
        return "registration";
    }

    public String updateUser() {
        UserAccount user = new UserAccount();
        user.setId(getId());
        user.setFirstname(getFirstname());
        user.setLastname(getLastname());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
       
        
        userService.updateUserInfo(user);
        System.out.println("Running method updateUser()");
        return "userhome";
    }
    
    public String printUserDetails(){
        HttpSession session = Util.getSession();
        System.out.println("HSession id: "+session.getId());
        UserAccount user = (UserAccount) session.getAttribute("user");
        setId(user.getId());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
       
        System.out.println("Running method printUserDetails()");
        return "userDetails";
    }
    
    public String changePassword(){
        System.out.println("Running method changePassword()");
        return "userhome";
    }

    public void validateEmail(FacesContext context, UIComponent comp, Object value) throws ValidatorException, NullPointerException {
        String email_ = value.toString();
        Map<String, UserAccount> userStore = userService.getUsers();
        UserAccount user = userStore.get(email_);
        System.out.println("Running method validateEmail()");
        if (user != null) {
            FacesMessage message = new FacesMessage();
            message.setSummary("This email is taken");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

  
   
}
