<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>Dashboard</title>
</head>

<body>

<h1 class="mb-3"> Welcome to Combat.</h1>

<blockquote class="blockquote">
<p class="mb-0">An online developer portal for sharing solutions for already seen issues.
    This helps in reducing the time spent on an issue which has been solved previously. You can create an already seen issue and post a solution.
    Other developers can then leverage this. Once you have seen a posted issue and feel it has helped you, you can up vote it.
    You can also down vote it if you feel the solution does not work. You can also post comments on a posted solution.</p>
</blockquote>


<div class="card" style="width: 18rem;">
  <div class="card-body">
    <h5 class="card-title">Posted issues</h5>
    <p class="card-text">List of issues and solutions that have been posted so far</p>
    <g:link class="btn btn-primary stretched-link" controller="Issue" action="index">View Issues</g:link>
  </div>
</div>



</body>
</html>