<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../includes/header.jsp" />

<spring:url value="/resources/images/address_book_logo3.gif" var="logoUrl" />

<div class="container">
  	<!-- flash box start -->
	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
  	<!-- flash box end -->
	
	<img src="${logoUrl}" alt="Logo" style="width:10%;height:10%;">
	<h1>Contact Details</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${contact.contactId}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Name</label>
		<div class="col-sm-10">${contact.name}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Email</label>
		<div class="col-sm-10">${contact.email}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Address</label>
		<div class="col-sm-10">${contact.address}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Favorite</label>
		<div class="col-sm-10">${contact.favorite}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Belongs to</label>
		<div class="col-sm-10">${contact.groups}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Gender</label>
		<div class="col-sm-10">${contact.gender}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Priority</label>
		<div class="col-sm-10">${contact.priority}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Country</label>
		<div class="col-sm-10">${contact.country}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Personality</label>
		<div class="col-sm-10">${contact.personality}</div>
	</div>

	<br />
	<div class="btn-toolbar">
		<spring:url value="/contacts/" var="backUrl" />
		<spring:url value="/contacts/${contact.contactId}/update" var="updateUrl" />

		<button class="btn btn-primary" onclick="location.href='${updateUrl}'"> Update</button>
		<button class="btn btn-success" onclick="location.href='${backUrl}'"> Back to contacts</button>
	</div>
</div>

<jsp:include page="../includes/footer.jsp" />
