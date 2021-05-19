package com.combat.diagnostics

class Upvote {
    static belongsTo = Issue
    Issue issue
    String upvotedBy
}
