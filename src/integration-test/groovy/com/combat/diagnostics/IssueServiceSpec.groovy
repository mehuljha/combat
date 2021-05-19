package com.combat.diagnostics

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class IssueServiceSpec extends Specification {

    IssueService issueService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        //Issue issue = new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //issue.id
    }

    void "test get"() {
        setupData()

        expect:
        issueService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Issue> issueList = issueService.list(max: 2, offset: 2)

        then:
        issueList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        issueService.count() == 5
    }

    void "test delete"() {
        Long issueId = setupData()

        expect:
        issueService.count() == 5

        when:
        issueService.delete(issueId)
        sessionFactory.currentSession.flush()

        then:
        issueService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Issue issue = new Issue()
        issueService.save(issue)

        then:
        issue.id != null
    }
}
