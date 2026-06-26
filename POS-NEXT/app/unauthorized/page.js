"use client";

import { useRouter } from "next/navigation";

export default function Unauthorized() {
  const router = useRouter();

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f5f5",
      }}
    >
      <div
        style={{
          textAlign: "center",
          background: "#fff",
          padding: "40px",
          borderRadius: "10px",
          boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
          maxWidth: "450px",
        }}
      >
        <h1
          style={{
            fontSize: "60px",
            color: "#d32f2f",
            marginBottom: "10px",
          }}
        >
          403
        </h1>

        <h2 style={{ marginBottom: "15px" }}>
          Access Denied
        </h2>

        <p
          style={{
            color: "#666",
            marginBottom: "30px",
          }}
        >
          You do not have permission to access this page.
        </p>

        <button
          onClick={() => router.push("/dashboard1")}
          style={{
            padding: "10px 20px",
            backgroundColor: "#1976d2",
            color: "#fff",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          Go to Dashboard
        </button>
      </div>
    </div>
  );
}