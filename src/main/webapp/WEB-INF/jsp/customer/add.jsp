<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">
    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: 'Poppins', sans-serif;
            background: #f4f6f9;
        }

        .topbar {
            height: 56px;
            background-color: #020617;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .top-title {
            color: #e5e7eb;
            font-weight: 600;
        }

        .home-btn {
            padding: 7px 16px;
            background-color: #1e293b;
            color: #e5e7eb;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            border: 1px solid #334155;
        }

        .logout-btn {
            background: #dc2626;
            color: white;
            border: none;
            padding: 7px 16px;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
        }

        .card-container {
            position: relative;
            width: 1100px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
            margin: 40px auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
            color: #333;
        }

        .section {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 22px;
            margin-bottom: 26px;
        }

        .section-title {
            font-size: 17px;
            font-weight: 600;
            margin-bottom: 14px;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 18px;
        }

        .grid-2 {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 13px;
        }

        input, select {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        .submit-btn {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            background: #4a90e2;
            color: white;
            font-weight: 600;
            cursor: pointer;
        }

        .error-message {
            background: #ffe5e5;
            color: #b30000;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 15px;
            font-size: 13px;
        }
    </style>
</head>
<body>

<div class="topbar">
    <div class="topbar-left">
        <div class="top-title">POS Application</div>
        <a class="home-btn" href="${pageContext.request.contextPath}/">Home</a>
    </div>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button class="logout-btn">Logout</button>
    </form>
</div>
<div class="card-container">
    <a href="${pageContext.request.contextPath}/customer/list" class="back-icon">←</a>
    <h2>Add Customer</h2>
    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>
    <form:form method="post"
               action="${pageContext.request.contextPath}/customer/add"
               modelAttribute="customerDto">
        <div class="section">
            <div class="section-title">Customer Details</div>
            <div class="grid">
                <div>
                    <label>Phone Number</label>
                    <form:input path="identifier"/>
                </div>
                <div>
                    <label>Customer Name</label>
                    <form:input path="customerName"/>
                </div>
                <div>
                    <label>Email</label>
                    <form:input path="email"/>
                </div>
                <div>
                    <label>Party Type</label>
                    <form:select path="partyType">
                        <form:option value="">-- Select --</form:option>
                        <form:option value="Customer">Customer</form:option>
                        <form:option value="Dealer">Dealer</form:option>
                        <form:option value="Retailer">Retailer</form:option>
                        <form:option value="Distributor">Distributor</form:option>
                    </form:select>
                </div>
                <div>
                    <label>Credit</label>
                    <form:input path="credit"/>
                </div>
                <div>
                    <label>Credit Type</label>
                    <form:select path="creditType">
                        <form:option value="">-- Select --</form:option>
                        <form:option value="ADVANCE">Advance</form:option>
                        <form:option value="DUE">Due</form:option>
                    </form:select>
                </div>
                <div>
                    <label>Credit Limit</label>
                    <form:input path="creditLimit"/>
                </div>
            </div>
        </div>
        <div class="section">
            <div class="section-title">Billing Address</div>
            <div class="grid-2">
                <form:input path="billingAddress.addressLine" placeholder="Address Line"/>
                <form:input path="billingAddress.phoneNo" placeholder="Phone No"/>
                <form:input path="billingAddress.city" placeholder="City"/>
                <form:input path="billingAddress.state" placeholder="State"/>
                <form:input path="billingAddress.zipCode" placeholder="Zip Code"/>
                <form:input path="billingAddress.country" placeholder="Country"/>
            </div>
        </div>
        <div class="section">
            <div class="section-title">Shipping Address</div>
            <div class="grid-2">
                <form:input path="shippingAddress.addressLine" placeholder="Address Line"/>
                <form:input path="shippingAddress.phoneNo" placeholder="Phone No"/>
                <form:input path="shippingAddress.city" placeholder="City"/>
                <form:input path="shippingAddress.state" placeholder="State"/>
                <form:input path="shippingAddress.zipCode" placeholder="Zip Code"/>
                <form:input path="shippingAddress.country" placeholder="Country"/>
            </div>
        </div>
        <button type="submit" class="submit-btn">Add Customer</button>
    </form:form>
</div>
</body>
</html>