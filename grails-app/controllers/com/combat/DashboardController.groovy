package com.combat

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
class DashboardController {

    def index() {

    }
}
