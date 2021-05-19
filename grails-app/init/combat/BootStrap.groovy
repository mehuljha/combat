package combat

import com.combat.*

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        def userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')

        def admin = User.findOrSaveWhere(username: 'admin', password: 'admin')
        def user = User.findOrSaveWhere(username: 'mjha', password: 'admin')

    }
    def destroy = {
    }
}
