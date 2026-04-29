<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Customer</title>

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: "Segoe UI", Inter, Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
        }

        /* ===== POS TOP BAR (Purple Theme) ===== */
        .topbar {
            height: 56px;
            background-color: #4c1d95;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
            box-shadow: 0 4px 12px rgba(76, 29, 149, 0.25);
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .top-title {
            color: #ede9fe;
            font-weight: 700;
        }

        .home-btn {
            padding: 7px 16px;
            background-color: #7c3aed;
            color: #ffffff;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
        }

        .home-btn:hover {
            background-color: #6d28d9;
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

        /* ===== CARD ===== */
        .card {
            max-width: 1200px;
            background: #ffffff;
            margin: 40px auto;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
            position: relative;
        }

        .back-btn {
            position: absolute;
            top: 20px;
            left: 20px;
            padding: 6px 14px;
            background: #c4b5fd;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 600;
            color: #4c1d95;
            text-decoration: none;
        }

        .back-btn:hover {
            background: #b197fc;
        }

        h2 {
            text-align: center;
            margin-bottom: 28px;
            color: #4c1d95;
            font-weight: 700;
        }

        /* ===== SECTIONS ===== */
        .section {
            border: 1px solid #ddd6fe;
            border-radius: 12px;
            padding: 22px;
            margin-bottom: 26px;
            background: #faf9ff;
        }

        .section-title {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 14px;
            color: #4c1d95;
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
            font-size: 12px;
            font-weight: 600;
            color: #4c1d95;
            margin-bottom: 6px;
        }

        input, select {
            width: 100%;
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #c4b5fd;
            font-size: 14px;
        }

        input[readonly] {
            background-color: #f5f3ff;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #7c3aed;
            box-shadow: 0 0 0 2px rgba(124, 58, 237, 0.2);
        }

        .submit-btn {
            margin-top: 20px;
            width: 100%;
            padding: 12px;
            background: #7c3aed;
            color: white;
            border: none;
            border-radius: 20px;
            font-weight: 700;
            font-size: 15px;
            cursor: pointer;
        }

        .submit-btn:hover {
            background: #6d28d9;
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

<div class="card">

    <a href="${pageContext.request.contextPath}/customer/list" class="back-btn">Back</a>

    <h2>Edit Customer</h2>

    <!-- ✅ FORM & LOGIC UNCHANGED -->
    <form:form method="post"
               action="${pageContext.request.contextPath}/customer/update"
               modelAttribute="customer">

        <form:hidden path="id"/>

        <!-- CUSTOMER DETAILS -->
        <div class="section">
            <div class="section-title">Customer Details</div>
            <div class="grid">

                <div>
                    <label>Phone Number</label>
                    <form:input path="identifier" readonly="true"/>
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
                        <form:option value="NA">NA</form:option>
                    </form:select>
                </div>

                <div>
                    <label>Credit Limit</label>
                    <form:input path="creditLimit"/>
                </div>

            </div>
        </div>

        <!-- BILLING ADDRESS -->
        <div class="section">
            <div class="section-title">Billing Address</div>
            <div class="grid-2">
                <form:input path="billingAddress.addressLine" placeholder="Address Line"/>
                <form:input path="billingAddress.phoneNo" placeholder="Phone No" readonly="true"/>
                <form:input path="billingAddress.city" placeholder="City"/>
                <form:input path="billingAddress.state" placeholder="State"/>
                <form:input path="billingAddress.zipCode" placeholder="Zip Code"/>
                <form:input path="billingAddress.country" placeholder="Country"/>
            </div>
        </div>

        <!-- SHIPPING ADDRESS -->
        <div class="section">
            <div class="section-title">Shipping Address</div>
            <div class="grid-2">
                <form:input path="shippingAddress.addressLine" placeholder="Address Line"/>
                <form:input path="shippingAddress.phoneNo" placeholder="Phone No" readonly="true"/>
                <form:input path="shippingAddress.city" placeholder="City"/>
                <form:input path="shippingAddress.state" placeholder="State"/>
                <form:input path="shippingAddress.zipCode" placeholder="Zip Code"/>
                <form:input path="shippingAddress.country" placeholder="Country"/>
            </div>
        </div>

        <button type="submit" class="submit-btn">Update Customer</button>

    </form:form>

</div>

</body>
</html>