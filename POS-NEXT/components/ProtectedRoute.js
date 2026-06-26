"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import PropTypes from "prop-types";

const ProtectedRoute = ({ children }) => {
  const router = useRouter();
  const pathname = usePathname();

  const [isAuthorized, setIsAuthorized] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      router.replace("/login");
      return;
    }

    const menu = JSON.parse(localStorage.getItem("menu") || "[]");

    // Dashboard can always be accessed after login
    if (pathname === "/dashboard1") {
      setIsAuthorized(true);
      setLoading(false);
      return;
    }

    const hasAccess = menu.some((item) => item.path === pathname);

    if (!hasAccess) {
      router.replace("/unauthorized");
      return;
    }

    setIsAuthorized(true);
    setLoading(false);
  }, [pathname, router]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!isAuthorized) {
    return null;
  }

  return <>{children}</>;
};

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;