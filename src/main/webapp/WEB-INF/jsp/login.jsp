<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Login</title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<style>
*{ box-sizing:border-box; }

body{
    margin:0;
    font-family:Inter,sans-serif;
    background:#f4f7fb;
    display:flex;
    justify-content:center;
    align-items:center;
    min-height:100vh;
}

.container{
    width:400px;
    background:#fff;
    padding:32px;
    border-radius:16px;
    box-shadow:0 12px 35px rgba(0,0,0,0.08);
}

h2{
    text-align:center;
    color:#2563eb;
    margin-bottom:22px;
    font-weight:700;
}

form{
    display:flex;
    flex-direction:column;
    gap:16px;
}

.field{ position:relative; }

input{
    width:100%;
    padding:12px;
    border:1px solid #e5e7eb;
    border-radius:10px;
    font-size:14px;
    background:#fafafa;
    outline:none;
    transition:.2s;
}

input:focus{
    background:#fff;
    border-color:#2563eb;
    box-shadow:0 0 0 3px rgba(37,99,235,.12);
}

label{
    position:absolute;
    top:10px;
    left:12px;
    font-size:12px;
    color:#6b7280;
    background:#fff;
    padding:0 4px;
    pointer-events:none;
    transition:.2s;
}

input:focus + label,
input:not(:placeholder-shown) + label{
    top:-8px;
    font-size:11px;
    color:#2563eb;
}

.btn{
    padding:12px;
    background:#2563eb;
    color:#fff;
    border:none;
    border-radius:10px;
    font-weight:600;
    cursor:pointer;
}

.btn:hover{ background:#1e40af; }

.toast{
    position:fixed;
    top:20px;
    right:20px;
    background:#111827;
    color:#fff;
    padding:12px 16px;
    border-radius:10px;
    font-size:13px;
    opacity:0;
    transition:.3s;
}
.toast.show{ opacity:1; }
.toast.success{ background:#16a34a; }
.toast.error{ background:#dc2626; }
</style>
</head>

<body>

<div id="toast" class="toast"></div>

<div class="container">
<h2>Login</h2>

<form action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLogin(event);">

    <div class="field">
        <input type="text" id="username" name="username" placeholder=" " />
        <label>Username</label>
    </div>

    <div class="field">
        <input type="password" id="password" name="password" placeholder=" " />
        <label>Password</label>
    </div>

    <button type="submit" class="btn">Login</button>
</form>

</div>

<script>
function showToast(msg,type="error"){
    const t=document.getElementById("toast");
    t.innerText=msg;
    t.className="toast show "+type;
    setTimeout(()=>t.className="toast",2500);
}

function validateLogin(e){
    const username=document.getElementById("username").value.trim();
    const password=document.getElementById("password").value.trim();

    if(username==="" || password===""){
        e.preventDefault();
        showToast("Please enter username and password","error");
        return false;
    }

    showToast("Logging in...","success");
    return true;
}
</script>

</body>
</html>