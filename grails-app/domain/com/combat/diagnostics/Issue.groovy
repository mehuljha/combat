package com.combat.diagnostics

class Issue {

    String id
    String name
    String description
    String solution
    String createdBy
    Date createdDate
    Long numberOfComments
    Long numberOfUpvotes
    Long numberOfDownvotes
    static hasMany = [comments:Comment]

    static constraints = {
        name()
        description blank:false, maxSize: 5000
        solution blank:false, maxSize: 5000
        createdBy()
        createdDate()
    }

    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        name
    }
}
