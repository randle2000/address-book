<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../includes/header.jsp" />

<div class="container">
	<c:choose>
		<c:when test="${contactForm['new']}">
			<h1>Add New Contact</h1>
		</c:when>
		<c:otherwise>
			<h1>Update Contact</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/contacts" var="contactActionUrl" />
	<spring:url value="/contacts" var="cancelUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="contactForm" action="${contactActionUrl}">

		<form:hidden path="contactId" />
		
		<form:hidden path="userId" />

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<form:input path="name" type="text" class="form-control " id="name" placeholder="Name" />
					<form:errors path="name" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<form:input path="email" class="form-control" id="email" placeholder="Email" />
					<form:errors path="email" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="address">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Address</label>
				<div class="col-sm-10">
					<form:textarea path="address" rows="5" class="form-control" id="address" placeholder="address" />
					<form:errors path="address" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="favorite">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Favorite</label>
				<div class="col-sm-10">
					<div class="checkbox">
						<label> <form:checkbox path="favorite" id="favorite" />
						</label>
						<form:errors path="favorite" class="control-label" />
					</div>
				</div>
			</div>
		</spring:bind>

		<spring:bind path="groups">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Belongs to</label>
				<div class="col-sm-10">
					<form:checkboxes path="groups" items="${groupsList}" element="label class='checkbox-inline'" />
					<br />
					<form:errors path="groups" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="gender">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton path="gender" value="M" /> Male
					</label> <label class="radio-inline"> <form:radiobutton path="gender" value="F" /> Female
					</label> <br />
					<form:errors path="gender" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="priority">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Priority</label>
				<div class="col-sm-10">
					<form:radiobuttons path="priority" items="${priorityList}" element="label class='radio-inline'" />
					<br />
					<form:errors path="priority" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<!-- Custom Script, Spring map to model via 'name' attribute
		<div class="form-group">
			<label class="col-sm-2 control-label">Number</label>
			<div class="col-sm-10">

				<c:forEach items="${numberList}" var="obj">
					<div class="radio">
						<label> 
							<input type="radio" name="number" value="${obj}">${obj}
						</label>
					</div>
				</c:forEach>
			</div>
		</div>
 		-->

		<spring:bind path="country">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Country</label>
				<div class="col-sm-6">
					<form:select path="country" class="form-control">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${countryList}" />
					</form:select>
					<form:errors path="country" class="control-label" />
				</div>
				<div class="col-sm-6"></div>
			</div>
		</spring:bind>
		
		<spring:bind path="personality">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Personality</label>
				<div class="col-sm-6">
					<form:select path="personality" items="${personalityList}" multiple="true" size="6" class="form-control" />
					<form:errors path="personality" class="control-label" />
				</div>
				<div class="col-sm-6"></div>
			</div>
		</spring:bind>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${contactForm['new']}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../includes/footer.jsp" />