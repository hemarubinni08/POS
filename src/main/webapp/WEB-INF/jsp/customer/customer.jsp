<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Customer</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #E9EEF5;
            min-height: 100vh;
        }
        .card {
            border-radius: 16px;
        }
        .form-control, .form-select {
            border-radius: 10px;
        }
        .btn {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Customer Management</span>
        <a href="/customer/list" class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 700px;">

        <h3 class="text-center mb-4 fw-bold">Update Customer</h3>

        <c:if test="${not empty customerDto.message}">
            <div class="alert alert-danger text-center">
                ${customerDto.message}
            </div>
        </c:if>

        <form action="/customer/update" method="post">

            <input type="hidden" name="identifier" value="${customerDto.identifier}" />

            <h5 class="fw-bold mb-3">Customer Details</h5>

            <div class="mb-3">
                <label class="form-label">Phone No</label>
                <input type="text"
                       name="phoneNo"
                       class="form-control"
                       value="${customerDto.phoneNo}"
                       readonly>
            </div>

            <div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text"
                       name="name"
                       class="form-control"
                       value="${customerDto.name}"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email"
                       name="email"
                       class="form-control"
                       value="${customerDto.email}">
            </div>

            <div class="mb-3">
                <label class="form-label">Balance</label>
                <input type="number"
                       step="0.01"
                       name="balance"
                       class="form-control"
                       value="${customerDto.balance}">
            </div>

            <div class="mb-3">
                <label class="form-label">Balance Type</label>
                <select name="balanceType" class="form-select">
                    <option value="CR" <c:if test="${customerDto.balanceType=='CR'}">selected</c:if>>Credit</option>
                    <option value="DR" <c:if test="${customerDto.balanceType=='DR'}">selected</c:if>>Debit</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Party Type</label>
                <input type="text"
                       name="partyType"
                       class="form-control"
                       value="${customerDto.partyType}">
            </div>

            <div class="mb-4">
                <label class="form-label">Credit Limit</label>
                <input type="number"
                       name="creditLimit"
                       class="form-control"
                       value="${customerDto.creditLimit}">
            </div>

            <div class="mb-4">
                <label class="form-label fw-semibold">Status</label>
                <select name="status" class="form-select">
                    <option value="true" <c:if test="${customerDto.status}">selected</c:if>>Active</option>
                    <option value="false" <c:if test="${!customerDto.status}">selected</c:if>>Inactive</option>
                </select>
            </div>

            <h5 class="fw-bold mb-3">Billing Address</h5>

            <input type="text" name="billingAddress.addressLine"
                   class="form-control mb-3"
                   value="${customerDto.billingAddress.addressLine}"
                   required>

            <input type="text" name="billingAddress.city"
                   class="form-control mb-3"
                   value="${customerDto.billingAddress.city}"
                   required>

            <input type="text" name="billingAddress.state"
                   class="form-control mb-3"
                   value="${customerDto.billingAddress.state}"
                   required>

            <!-- PINCODE -->
            <input type="text"
                   name="billingAddress.zip"
                   class="form-control mb-3"
                   value="${customerDto.billingAddress.zip}"
                   maxlength="6"
                   pattern="[0-9]{6}"
                   inputmode="numeric"
                   oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                   required>

            <input type="text" name="billingAddress.country"
                   class="form-control mb-4"
                   value="${customerDto.billingAddress.country}"
                   required>

            <div class="form-check mb-4">
                <input class="form-check-input" type="checkbox" id="sameAsBilling">
                <label class="form-check-label fw-bold">
                    Shipping same as Billing Address
                </label>
            </div>

            <h5 class="fw-bold mb-3">Shipping Address</h5>

            <input type="text" id="shipAddressLine"
                   name="shippingAddress.addressLine"
                   class="form-control mb-3"
                   value="${customerDto.shippingAddress.addressLine}"
                   required>

            <input type="text" id="shipCity"
                   name="shippingAddress.city"
                   class="form-control mb-3"
                   value="${customerDto.shippingAddress.city}"
                   required>

            <input type="text" id="shipState"
                   name="shippingAddress.state"
                   class="form-control mb-3"
                   value="${customerDto.shippingAddress.state}"
                   required>

            <!-- PINCODE -->
            <input type="text"
                   id="shipZip"
                   name="shippingAddress.zip"
                   class="form-control mb-3"
                   value="${customerDto.shippingAddress.zip}"
                   maxlength="6"
                   pattern="[0-9]{6}"
                   inputmode="numeric"
                   oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                   required>

            <input type="text" id="shipCountry"
                   name="shippingAddress.country"
                   class="form-control mb-4"
                   value="${customerDto.shippingAddress.country}"
                   required>

            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary w-100">Update</button>
                <a href="/customer/list" class="btn btn-outline-secondary w-100">Cancel</a>
            </div>

        </form>

    </div>
</div>

<script>
document.getElementById("sameAsBilling").addEventListener("change", function () {
    if (this.checked) {
        document.getElementById("shipAddressLine").value = document.querySelector("[name='billingAddress.addressLine']").value;
        document.getElementById("shipCity").value = document.querySelector("[name='billingAddress.city']").value;
        document.getElementById("shipState").value = document.querySelector("[name='billingAddress.state']").value;
        document.getElementById("shipZip").value = document.querySelector("[name='billingAddress.zip']").value;
        document.getElementById("shipCountry").value = document.querySelector("[name='billingAddress.country']").value;
    } else {
        document.getElementById("shipAddressLine").value = "";
        document.getElementById("shipCity").value = "";
        document.getElementById("shipState").value = "";
        document.getElementById("shipZip").value = "";
        document.getElementById("shipCountry").value = "";
    }
});
</script>

</body>
</html>