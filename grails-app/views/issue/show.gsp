<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-issue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-issue" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

             <f:display bean="issue" except="comments"/>
              <g:if test="${(hasUpvoted == false || hasDownvoted == true)}">
                  <div class="upvote-section">
                      <sec:ifLoggedIn>

                                <g:form class="form-group form-inline">
                                    <g:hiddenField name="id" value="${params.id}"/>
                                    <fieldset class="buttons">
                                        <g:actionSubmit value="Upvote" action="upvote"/>
                                    </fieldset>
                                </g:form>

                      </sec:ifLoggedIn>
                   </div>
              </g:if>
              <g:if test="${hasUpvoted == true || hasDownvoted == false}">
                 <div class="downvote-section">
                     <sec:ifLoggedIn>
                           <div class="downvote">
                               <g:form class="form-group form-inline">
                                   <g:hiddenField name="id" value="${params.id}"/>
                                   <fieldset class="buttons">
                                       <g:actionSubmit value="Downvote" action="downvote"/>
                                   </fieldset>
                               </g:form>
                           </div>
                     </sec:ifLoggedIn>
                  </div>
              </g:if>

           <g:form class = "form-group" resource="${this.issue}" method="DELETE">
                           <fieldset class="buttons">
                               <g:link class="edit" action="edit" resource="${this.issue}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                               <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                           </fieldset>
           </g:form>

            <div class="container">
            <div class="row bootstrap snippets bootdeys">
                <div class="col-md-20 col-sm-12">
                    <div class="comment-wrapper">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3>Comment Section </h3>
                            </div>
                            <div class="panel-body">
                                <sec:ifLoggedIn>
                                    <g:form>
                                        <textarea name="body" class="form-control" placeholder="Write a comment..." rows="5"></textarea>
                                        <g:hiddenField name="id" value="${params.id}"/>
                                        <br>
                                        <g:actionSubmit class="btn btn-info pull-right" value="Post" action="postReply"/>

                                    </g:form>
                                </sec:ifLoggedIn>
                                <div class="clearfix"></div>
                                <hr>

                                <g:if test="${numberOfComments == 0}">
                                    <p>No Comments yet</p>
                                </g:if>
                                <ul class="media-list">
                                    <g:each in="${comments}" var="comment">
                                        <li class="media">
                                            <div class="media-body">
                                                <strong class="text-success">@${comment.commentBy}</strong>
                                                <span class="topicDesc">
                                                    <g:formatDate date="${comment.createDate}" format="dd MMM yyyy hh:mma"/>
                                                </span>
                                                <p>${comment.body}</p>
                                            </div>


                                        </li>
                                    </g:each>
                                    <g:if test="${numberOfComments > 10}">
                                        <div class="pagination" bean="issue">
                                            <g:paginate total="${numberOfComments}" params="${[id:params.id]}"/>
                                        </div>
                                    </g:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </body>
</html>
