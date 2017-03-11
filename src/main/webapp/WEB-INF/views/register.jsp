<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page session="false"%>

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

<div class="jumbotron">
  <div class="container">
	<h2>
	<img src="<c:url value='/resources/images/register-icon.png' />" alt="RegisterPic" style="width:70px;height:70px;">
	<c:choose>
		<c:when test="${registerForm['new']}">
			Please enter your registration details
		</c:when>
		<c:otherwise>
			Please update your info
		</c:otherwise>
	</c:choose>
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

		<spring:url value="/register" var="registerActionUrl" />
	
		<form:form class="form-horizontal" method="post" modelAttribute="registerForm" action="${registerActionUrl}">
	
			<form:hidden path="userId" />
			
			<form:hidden path="enabled" value="1" />
	
			<spring:bind path="realName">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Name</label>
					<div class="col-sm-10">
						<form:input path="realName" type="text" class="form-control " id="name" placeholder="Name" />
						<form:errors path="realName" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="email">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<form:input path="email" class="form-control" id="email" placeholder="Email" readonly="${empty activeRegisterUpdate ? 'false' : 'true'}" />
						<form:errors path="email" class="control-label"  />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="password">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Password</label>
					<div class="col-sm-10">
						<form:password path="password" class="form-control" id="password" placeholder="password" />
						<form:errors path="password" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="confirmPassword">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Confirm Password</label>
					<div class="col-sm-10">
						<form:password path="confirmPassword" class="form-control" id="password" placeholder="password" />
						<form:errors path="confirmPassword" class="control-label" />
					</div>
				</div>
			</spring:bind>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${registerForm['new']}">
							<button type="submit" class="btn-lg btn-primary pull-right">Register</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
	
		</form:form>

	</p>
  </div>
</div>

<jsp:include page="includes/footer.jsp" />