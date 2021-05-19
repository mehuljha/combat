package com.combat.diagnostics

class Downvote {
    static belongsTo = Issue
    Issue issue
    String downvotedBy
}
