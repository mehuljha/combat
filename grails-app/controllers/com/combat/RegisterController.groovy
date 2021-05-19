package com.combat

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import com.combat.*

@Transactional
@Secured('permitAll')
class RegisterController {

    static allowedMethods = [register: "POST"]

    def index() { }

    def register() {
        if(!params.password.equals(params.repassword)) {
            flash.message = "Password and Confirm Password not match"
            redirect action: "index"
            return
        } else {
            try {

                def existingUser = User.findByUsername(params.username)
                def userRole = Role.findWhere(authority: 'ROLE_USER')
                if(existingUser) {
                    flash.message = "User with that username already exists. Please select a new username"
                    render view: "index"
                    return
                }
                else if(!userRole) {
                    flash.message = "Registration failed. Could not find user role from Database."
                    render view: "index"
                    return
                }
                else {
                    def user = new User(username: params.username, password: params.password).save()
                    UserRole.create user, userRole
                    UserRole.withSession {
                        it.flush()
                        it.clear()
                    }
                    flash.message = "You have registered successfully. Please login."
                    redirect controller: "login", action: "auth"
                }

            } catch (ValidationException e) {
                flash.message = "Register Failed"
                redirect action: "index"
                return
            }
        }
    }
}
