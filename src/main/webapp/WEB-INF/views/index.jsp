<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="includes/header.jsp" />

<spring:url value="/login" var="urlLogin" />
<spring:url value="/contacts" var="urlContacts" />
<spring:url value="/register" var="urlRegister" />

<div class="jumbotron">
	<div class="container">
  		<!-- flash box from registerOrUpdateUser start -->
		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>
  		<!-- flash box from registerOrUpdateUser end -->
  
		<h1>Address Book</h1>
		<p><img src="resources/images/Address_Book.png" alt="Logo" style="width:20%;height:20%;"></p>
    	<p><a class="btn btn-primary btn-lg" href="${urlContacts}" role="button">Show my contacts!</a></p>
	</div>
</div>


<div class="container">
	<div class="row">
		<div class="col-md-4">
			<h3>New User?</h3>
			<p><img src="resources/images/register-icon.png" alt="RegisterPic" style="width:70px;height:70px;"></p>
			<p><a class="btn btn-default" href="${urlRegister}" role="button">Register</a></p>
		</div>
		
		<div class="col-md-4">
			<h3>Returning User?</h3>
			<p><img src="resources/images/login-icon.gif" alt="LoginPic" style="width:70px;height:70px;"></p>
			<p><a class="btn btn-default" href=${urlLogin} role="button">Login</a></p>
		</div>
		
		<div class="col-md-4">
			<h3>View Address Book?</h3>
			<p><img src="resources/images/contacts-icon.png" alt="BookPic" style="width:70px;height:70px;"></p>
			<p><a class="btn btn-default" href=${urlContacts} role="button">Contacts</a></p>
		</div>
	</div>
</div>

<!-- 
	<sec:authorize access="hasRole('ROLE_USER')">
		<p>aaa</p>
	</sec:authorize>
 -->
 
<jsp:include page="includes/footer.jsp" />