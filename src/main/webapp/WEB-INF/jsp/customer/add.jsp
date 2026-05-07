<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Customer</title>

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

        <h3 class="text-center mb-4 fw-bold">Add Customer</h3>

        <c:if test="${not empty customerDto.message}">
            <div class="alert alert-danger text-center">
                ${customerDto.message}
            </div>
        </c:if>

        <form action="/customer/add" method="post">

            <h5 class="fw-bold mb-3">Customer Details</h5>

            <!-- PHONE -->
            <div class="mb-3">
                <label class="form-label">Phone No (Identifier)</label>
                <input type="text"
                       name="phoneNo"
                       class="form-control"
                       placeholder="10 digit phone number"
                       maxlength="10"
                       pattern="[0-9]{10}"
                       inputmode="numeric"
                       oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                       required>
            </div>

            <!-- NAME -->
            <div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text"
                       name="name"
                       class="form-control"
                       required>
            </div>

            <!-- EMAIL -->
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email"
                       name="email"
                       class="form-control">
            </div>

            <!-- BALANCE -->
            <div class="mb-3">
                <label class="form-label">Balance</label>
                <input type="number"
                       step="0.01"
                       name="balance"
                       class="form-control">
            </div>

            <!-- BALANCE TYPE -->
            <div class="mb-3">
                <label class="form-label">Balance Type</label>
                <select name="balanceType" class="form-select">
                    <option value="CR">Credit</option>
                    <option value="DR">Debit</option>
                </select>
            </div>

            <!-- PARTY TYPE -->
            <div class="mb-3">
                <label class="form-label">Party Type</label>
                <input type="text"
                       name="partyType"
                       class="form-control">
            </div>

            <!-- CREDIT LIMIT -->
            <div class="mb-4">
                <label class="form-label">Credit Limit</label>
                <input type="number"
                       name="creditLimit"
                       class="form-control">
            </div>

            <!-- STATUS -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Status</label>
                <select name="status" class="form-select">
                    <option value="true">Active</option>
                    <option value="false">Inactive</option>
                </select>
            </div>

            <!-- BILLING ADDRESS -->
            <h5 class="fw-bold mb-3">Billing Address</h5>

            <input type="text" name="billingAddress.addressLine"
                   class="form-control mb-3"
                   placeholder="Address Line" required>

            <input type="text" name="billingAddress.city"
                   class="form-control mb-3"
                   placeholder="City" required>

            <input type="text" name="billingAddress.state"
                   class="form-control mb-3"
                   placeholder="State" required>

            <!-- PIN CODE -->
            <input type="text"
                   name="billingAddress.zip"
                   class="form-control mb-3"
                   placeholder="6 digit PIN code"
                   maxlength="6"
                   pattern="[0-9]{6}"
                   inputmode="numeric"
                   oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                   required>

            <input type="text"
                   name="billingAddress.country"
                   class="form-control mb-4"
                   placeholder="Country"
                   required>

            <!-- SHIPPING ADDRESS -->
            <h5 class="fw-bold mb-3">Shipping Address</h5>

            <input type="text" name="shippingAddress.addressLine"
                   class="form-control mb-3"
                   placeholder="Address Line" required>

            <input type="text" name="shippingAddress.city"
                   class="form-control mb-3"
                   placeholder="City" required>

            <input type="text" name="shippingAddress.state"
                   class="form-control mb-3"
                   placeholder="State" required>

            <!-- SHIPPING PIN -->
            <input type="text"
                   name="shippingAddress.zip"
                   class="form-control mb-3"
                   placeholder="6 digit PIN code"
                   maxlength="6"
                   pattern="[0-9]{6}"
                   inputmode="numeric"
                   oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                   required>

            <input type="text"
                   name="shippingAddress.country"
                   class="form-control mb-4"
                   placeholder="Country"
                   required>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary w-100">
                    Save
                </button>

                <a href="/customer/list" class="btn btn-outline-secondary w-100">
                    Cancel
                </a>
            </div>

        </form>

    </div>
</div>

</body>
</html>