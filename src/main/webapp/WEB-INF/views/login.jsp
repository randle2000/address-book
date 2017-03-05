<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<jsp:include page="includes/header.jsp" />

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
</style>

<body onload='document.loginForm.email.focus();'>

<div class="jumbotron">
  <div class="container">
	<h2>
	<img src="<c:url value='/resources/images/login-icon.gif' />" alt="LoginPic" style="width:70px;height:70px;">
	Please login 
	<!-- if trying to update user details -->
	<c:if test="${not empty loginUpdate}">
		 again [safety precautions]
	</c:if>
	</h2>
	<p>
		<!-- message flash box begin -->
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<!-- message flash box end -->
		
		<div class="container">
		  <form name="loginForm" action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method="POST">
		    <div class="form-group">
		      <label for="email">Email:</label>
		      <input type="email" class="form-control" name="email" placeholder="Enter email">
		    </div>
		    <div class="form-group">
		      <label for="pwd">Password:</label>
		      <input type="password" class="form-control" name="pwd" placeholder="Enter password">
		    </div>
		    <c:if test="${empty loginUpdate}">
		      <div class="checkbox">
		        <label><input type="checkbox" name="remember-me"> Remember me</label>
		      </div>
		    </c:if>
		    <button type="submit" class="btn btn-default">Submit</button>
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		  </form>
		</div>
	</p>
  </div>
</div>

<jsp:include page="includes/footer.jsp" />