<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update Customer</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body { background-color: #f8fafc; }
        .card { border-radius: 12px; }
        .card-header {
            background: #1e293b;
            color: #fff;
            text-align: center;
            padding: 15px;
        }

        .form-control:invalid ~ .invalid-feedback {
            display: block;
        }

        .invalid-feedback {
            display: none;
            font-size: 12px;
        }
    </style>
</head>

<body>

<div class="container mt-5">
<div class="row justify-content-center">
<div class="col-lg-8">

<div class="card shadow">

<div class="card-header">
    <h3>Update Customer</h3>
</div>

<div class="card-body">

<c:if test="${not empty message}">
    <div class="alert alert-danger text-center">
        ${message}
    </div>
</c:if>

<form:form action="/customer/update" method="post" modelAttribute="customerDto">

    <form:hidden path="shippingAddress.addressType" value="shipping"/>
    <form:hidden path="billingAddress.addressType" value="billing"/>

    <h5>Basic Information</h5>

    <div class="mb-3">
        <label>Name</label>
        <form:input path="name" cssClass="form-control" required="true"/>
        <div class="invalid-feedback">Name is required</div>
    </div>

    <div class="mb-3">
        <label>Phone</label>
        <form:input path="phoneNo" cssClass="form-control" required="true" readonly="true"/>
    </div>

    <div class="mb-3">
        <label>Email</label>
        <form:input path="identifier" cssClass="form-control" required="true" readonly="true"/>
    </div>

    <div class="mb-3">
        <label>Party Type</label>
        <form:select path="partyType" cssClass="form-select" required="true">
            <form:option value="">-- Select --</form:option>
            <form:option value="customer">Customer</form:option>
            <form:option value="dealer">Dealer</form:option>
            <form:option value="wholesaler">Wholesaler</form:option>
        </form:select>
        <div class="invalid-feedback">Please select a party type</div>
    </div>

    <div class="mb-3">
        <label>Balance</label>
        <form:input path="balance" type="number" cssClass="form-control" required="true"/>
        <div class="invalid-feedback">Balance is required</div>
    </div>

    <div class="mb-3">
        <label>Credit Limit</label>
        <form:input path="creditLimit" type="number" cssClass="form-control" required="true"/>
        <div class="invalid-feedback">Credit limit is required</div>
    </div>

    <h5>Shipping Address</h5>

    <form:input path="shippingAddress.addressLine" cssClass="form-control mb-2" placeholder="Address" required="true"/>
    <div class="invalid-feedback">Address is required</div>

    <form:input path="shippingAddress.city" cssClass="form-control mb-2" placeholder="City" required="true"/>
    <div class="invalid-feedback">City is required</div>

    <form:input path="shippingAddress.state" cssClass="form-control mb-2" placeholder="State" required="true"/>
    <div class="invalid-feedback">State is required</div>

    <form:input path="shippingAddress.zipcode"
                cssClass="form-control mb-2"
                placeholder="Zip"
                type="text"
                maxlength="10"
                pattern="[0-9]{1,10}"
                required="true"
                oninput="this.value=this.value.replace(/[^0-9]/g,'')"/>
    <div class="invalid-feedback">
        Please enter a valid zip code (up to 10 digits).
    </div>

    <form:input path="shippingAddress.country" cssClass="form-control mb-2" placeholder="Country" required="true"/>
    <div class="invalid-feedback">Country is required</div>

    <h5>Billing Address</h5>

    <form:input path="billingAddress.addressLine" cssClass="form-control mb-2" placeholder="Address" required="true"/>
    <div class="invalid-feedback">Address is required</div>

    <form:input path="billingAddress.city" cssClass="form-control mb-2" placeholder="City" required="true"/>
    <div class="invalid-feedback">City is required</div>

    <form:input path="billingAddress.state" cssClass="form-control mb-2" placeholder="State" required="true"/>
    <div class="invalid-feedback">State is required</div>

    <form:input path="billingAddress.zipcode"
                cssClass="form-control mb-2"
                placeholder="Zip"
                type="text"
                maxlength="10"
                pattern="[0-9]{1,10}"
                required="true"
                oninput="this.value=this.value.replace(/[^0-9]/g,'')"/>
    <div class="invalid-feedback">
        Please enter a valid zip code (up to 10 digits).
    </div>

    <form:input path="billingAddress.country" cssClass="form-control mb-2" placeholder="Country" required="true"/>
    <div class="invalid-feedback">Country is required</div>

    <div class="d-grid mt-4">
        <button type="submit" class="btn btn-success">Update</button>
    </div>

</form:form>

<div class="text-center mt-3">
    <a href="${pageContext.request.contextPath}/customer/list" class="btn btn-secondary">
        Back
    </a>
</div>

</div>
</div>
</div>
</div>
</div>

</body>
</html>
