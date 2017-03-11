<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>Address Book App</title>
  <!-- 
  <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
  <spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
  <link href="${bootstrapCss}" rel="stylesheet" />
  <script src="${bootstrapJs}"></script>
   -->
  <!-- The following 2 CSSs are used by social buttons -->
  <spring:url value="/resources/css/bootstrap-social.css" var="bootstrapsocialCss" />
  <spring:url value="/resources/css/font-awesome.css" var="fontawesomeCss" />
  <link href="${bootstrapsocialCss}" rel="stylesheet" />
  <link href="${fontawesomeCss}" rel="stylesheet" />
   
  <spring:url value="/resources/js/my.js" var="myJs" />
  <script src="${myJs}"></script>
   
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/contacts" var="urlContacts" />
<spring:url value="/register" var="urlRegister" />
<spring:url value="/register/update" var="urlRegisterUpdate" />
<spring:url value="/login" var="urlLogin" />
<spring:url value="/j_spring_security_logout" var="urlLogout" />

<!-- hidden logout form -->
<form action="${urlLogout}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>

<body>

<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="${urlHome}">Address Book</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="${activeHome ? 'active' : ''}"><a href="${urlHome}"><span class="glyphicon glyphicon-home"></span> Home</a></li>
      <li class="${activeContacts ? 'active' : ''}"><a href="${urlContacts}"><span class="glyphicon glyphicon-book"></span> Contacts</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <!--  is user logged in? -->    	
	  <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name == null}">
          <li class="${activeRegister ? 'active' : ''}"><a href="${urlRegister}"><span class="glyphicon glyphicon-user"></span> Register</a></li>
          <li class="${activeLogin ? 'active' : ''}"><a href="${urlLogin}"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </c:when>
        <c:otherwise>
          <a class="navbar-brand" ><span class="glyphicon glyphicon-ok-sign"></span> <%= ((com.sln.abook.service.MyUser)(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser().getRealName() %> </a>
          <li class="${activeRegisterUpdate ? 'active' : ''}"><a href="${urlRegisterUpdate}"><span class="glyphicon glyphicon-user"></span> Update info</a></li>
          <li class="${activeLogout ? 'active' : ''}"><a href="javascript:formSubmit()"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>


