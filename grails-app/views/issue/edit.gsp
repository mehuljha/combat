<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-issue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-issue" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.issue}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.issue}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.issue}" method="PUT">
                <g:hiddenField name="version" value="${this.issue?.version}" />
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
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
