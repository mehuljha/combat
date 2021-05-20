<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-issue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-issue" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:form resource="${this.issue}" method="POST">
                <fieldset class="form">
                        <f:field bean="issue" property="name"/>
                        <f:field bean="issue" property="description">
                            <g:textArea name="description" cols="100" rows="20" value="${issue.description}" required id="description"/>
                        </f:field>
                        <f:field bean="issue" property="solution">
                            <g:textArea name="solution" cols="100" rows="20" value="${issue.solution}" required id="solution"/>
                        </f:field>
                        <f:field bean="issue" property="createdBy">
                            <g:textField name="createdBy" value="${issue.createdBy}" required disabled="true"/>
                        </f:field>
                        <f:field bean="issue" property="createdDate"/>
                        <g:hiddenField name="numberOfComments" value="${issue.numberOfComments}" />
                        <g:hiddenField name="numberOfUpvotes" value="${issue.numberOfUpvotes}" />
                        <g:hiddenField name="numberOfDownvotes" value="${issue.numberOfDownvotes}" />
                </fieldset>
                <fieldset class="buttons">
                    <g:actionSubmit class="save" value="Create" action="save" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
