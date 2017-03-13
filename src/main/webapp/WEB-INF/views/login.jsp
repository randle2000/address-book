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

.right {
	border-right: 2px solid #ccc;
}
</style>

<body onload='document.loginForm.email.focus();'>


<div class="jumbotron">
	<div class="container">

		<!-- message flash box begin -->
		<c:if test="${not empty error}">
			<p><div class="error">${error}</div><p>
		</c:if>
		<c:if test="${not empty msg}">
			<p><div class="msg">${msg}</div><p>
		</c:if>
		<!-- message flash box end -->

		<div class="row">
			<div class="col-sm-6 right">
				<h2>
					<img src="<c:url value='/resources/images/login-icon.gif' />" alt="LoginPic" style="width: 70px; height: 70px;">
					Please login
					<!-- if trying to update user details -->
					<c:if test="${not empty loginUpdate}">
		 				again [safety precautions]
					</c:if>
				</h2>
				<p>
				
				<form name="loginForm" action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method="POST">
					<div class="form-group">
						<label for="email">Email:</label> <input type="email" class="form-control" name="email" placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label> <input type="password" class="form-control" name="pwd" placeholder="Enter password">
					</div>
					<c:if test="${empty loginUpdate}">
						<div class="checkbox">
							<label><input type="checkbox" name="remember-me">Remember me</label>
						</div>
					</c:if>
					<button type="submit" class="btn btn-default">Submit</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>

			<div class="col-sm-6 social-buttons">
				<h2>
					<img src="<c:url value='/resources/images/socialuser-icon.png' />" alt="SocialUserPic" style="width: 70px; height: 70px;">
					Or sign in via social account
				</h2>
				<br/>
				<a href="${pageContext.request.contextPath}/auth/twitter?scope=email,user_about_me,user_birthday"
					class="btn btn-block btn-social btn-twitter"> <i
					class="fa fa-twitter"></i> Sign in with Twitter
				</a>
				<a href="${pageContext.request.contextPath}/auth/linkedin"
					class="btn btn-block btn-social btn-linkedin"> <i
					class="fa fa-linkedin"></i> Sign in with LinkedIn
				</a>
				<a href="${pageContext.request.contextPath}/auth/facebook"
					class="btn btn-block btn-social btn-facebook"> <i
					class="fa fa-facebook"></i> Sign in with Facebook
				</a>
				<!-- 
		  		<form name='GoogleSocialloginForm' action="${pageContext.request.contextPath}/auth/google" method='POST'>
                	<button type="submit" class="btn btn-block btn-social btn-google">
                		<i class="fa fa-google"></i> Sign in with Google
                	</button>
                	<input type="hidden" name="scope" value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo#email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/tasks https://www-opensocial.googleusercontent.com/api/people https://www.googleapis.com/auth/plus.login" />
        		</form>
		   		-->
			</div>
		</div>
	</div>
</div>

<jsp:include page="includes/footer.jsp" />