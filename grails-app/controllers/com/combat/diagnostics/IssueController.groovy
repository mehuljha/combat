package com.combat.diagnostics

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional
class IssueController {

    def springSecurityService

    IssueService issueService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond issueService.list(params), model:[issueCount: issueService.count()]
    }

    def show(String id) {
        //respond issueService.get(id)
        Issue issue = issueService.get(id)
        params.max = 10
        params.sort = 'createDate'
        params.order = 'asc'

        def hasUpvoted = hasUpvoted(issue)
        def hasDownvoted = hasDownvoted(issue)

        println 'Has Upvoted: ' + hasUpvoted
        println 'Has Downvoted: ' + hasDownvoted
        [comments:Comment.findAllByIssue(issue, params),
         numberOfComments:Comment.countByIssue(issue), issue:issue, hasUpvoted:hasUpvoted, hasDownvoted:hasDownvoted]
    }

    def create() {
        def username = springSecurityService.authentication.principal.getUsername()
        def issue = new Issue(params)
        issue.createdBy = username
        respond issue
    }

    def save(Issue issue) {
        println issue.dump()
        issue.createdBy = springSecurityService.authentication.principal.getUsername()
        println 'Issue created by::: ' + issue.createdBy
        if (issue == null) {
            notFound()
            return
        }

        if(Issue.findByName(issue.name)) {
            flash.message = 'Issue with same name already exists. Please select a new name'
            forward(controller:"Issue",action:"create", params: [issue: issue])
            return
        }

        try {
            issue.id = UUID.randomUUID().toString()
            println 'Saving issue...'
            issueService.save(issue)
        } catch (ValidationException e) {
            println 'Validation exception'
            respond issue.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'issue.label', default: 'Issue'), issue.id])
                redirect issue
            }
            '*' { respond issue, [status: CREATED] }
        }
    }

    def edit(String id) {
        def username = springSecurityService.authentication.principal.getUsername()
        def issue = issueService.get(id)
        if(!username.equalsIgnoreCase(issue.getCreatedBy())) {
            flash.message = 'Sorry you cannot edit this issue. Not the owner of the issue'
            redirect(action: 'show', controller: 'Issue', namespace: null, id: id)
            return
        }
        respond issue
    }

    def update(Issue issue) {
        if (issue == null) {
            notFound()
            return
        }

        try {
            def username = springSecurityService.authentication.principal.getUsername()

            if(!username.equalsIgnoreCase(issue.getCreatedBy())) {
                flash.message = 'Sorry you cannot update this issue. Not the owner of the issue'
                redirect(action: 'show', controller: 'Issue', namespace: null, id: issue.id)
                return
            }
            issueService.save(issue)
        } catch (ValidationException e) {
            respond issue.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'issue.label', default: 'Issue'), issue.id])
                redirect issue
            }
            '*'{ respond issue, [status: OK] }
        }
    }

    def delete(String id) {
        if (id == null) {
            notFound()
            return
        }

        def username = springSecurityService.authentication.principal.getUsername()
        def issue = issueService.get(id)
        if(!username.equalsIgnoreCase(issue.getCreatedBy())) {
            flash.message = 'Sorry you cannot delete this issue. Not the owner of the issue'
            redirect(action: 'show', controller: 'Issue', namespace: null, id: id)
            return
        }

        issueService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'issue.label', default: 'Issue'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def postReply(String id, String body) {
        def offset = params.offset
        if (body != null && body.trim().length() > 0) {
            Issue issue = issueService.get(id)
            issue.numberOfComments = issue.getNumberOfComments() + 1
            issueService.save(issue)
            def commentBy = springSecurityService.authentication.principal.getUsername()
            new Comment(issue:issue, commentBy:commentBy, body:body).save()

            // go to last page so user can view his comment
            def numberOfComments = Comment.countByIssue(issue)
            def lastPageCount = numberOfComments % 10 == 0 ? 10 : numberOfComments % 10
            offset = numberOfComments - lastPageCount
        }
        //redirect(action:'show', id: id, params:[issueId:id, offset:offset, issue: issue])
        redirect(action: 'show', controller: 'Issue', namespace: null, id: id, params:[offset: offset])
    }

    def upvote(String id) {
        Issue issue = issueService.get(id)
        def upvotedBy = springSecurityService.authentication.principal.getUsername()
        Upvote upvote = Upvote.findByUpvotedByAndIssue(upvotedBy, issue)
        if(!upvote) {
            issue.numberOfUpvotes = issue.getNumberOfUpvotes() + 1
            issueService.save(issue)
            new Upvote(issue:issue, upvotedBy:upvotedBy).save()
        }

        Downvote downvote = Downvote.findByDownvotedByAndIssue(upvotedBy, issue)
        if(downvote) {

            println 'Already downvoted. Deleting'
            issue.numberOfDownvotes = issue.getNumberOfDownvotes() - 1
            issueService.save(issue)
            downvote.delete()
        }

        redirect(action: 'show', controller: 'Issue', namespace: null, id: id, params:[upvoted: true])
    }

    def downvote(String id) {
        Issue issue = issueService.get(id)
        def downvotedBy = springSecurityService.authentication.principal.getUsername()
        Downvote downvote = Downvote.findByDownvotedByAndIssue(downvotedBy, issue)
        if(!downvote) {
            issue.numberOfDownvotes = issue.getNumberOfDownvotes() + 1
            issueService.save(issue)
            new Downvote(issue:issue, downvotedBy:downvotedBy).save()
        }

        Upvote upvote = Upvote.findByUpvotedByAndIssue(downvotedBy, issue)
        if(upvote) {
            println 'Already Upvoted. Deleting'
            issue.numberOfUpvotes = issue.getNumberOfUpvotes() - 1
            issueService.save(issue)
            upvote.delete()
        }

        redirect(action: 'show', controller: 'Issue', namespace: null, id: id)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    protected boolean hasUpvoted(Issue issue) {
        Upvote upvote = Upvote.findByUpvotedByAndIssue(springSecurityService.authentication.principal.getUsername(), issue)
        return (upvote ?  true : false)
    }

    protected boolean hasDownvoted(Issue issue) {
        Downvote downvote = Downvote.findByDownvotedByAndIssue(springSecurityService.authentication.principal.getUsername(), issue)
        return (downvote ?  true : false)
    }
}
