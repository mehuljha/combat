package com.combat.diagnostics

class Comment {

    static belongsTo = Issue
    Issue issue
    String commentBy
    String body
    Date createDate = new Date()

    static constraints = {
        body( maxSize: 8000)
    }
}
