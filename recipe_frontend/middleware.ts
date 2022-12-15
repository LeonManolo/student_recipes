// middleware.ts
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import jwt_decode, { JwtPayload } from "jwt-decode";
import { getCookies, setCookie, deleteCookie, getCookie } from "cookies-next";

// This function can be marked `async` if using `await` inside
export function middleware(request: NextRequest) {
  console.log(request.nextUrl.searchParams.get("token"));
  console.log(Date.now());
  if (request.nextUrl.pathname.startsWith("/_next")) return NextResponse.next();
  //console.log("middlware: " + localStorage.getItem("token"));

  console.log("middleware: " + request.cookies.get("token")?.value);
  console.log("middle2 " + getCookie("token"));
  const token = request.cookies.get("token")?.value ?? null;
  let isAuthenticated = false;
  console.log(token?.length);
  //console.log("Hallo");
  if (token !== null) {
    request.cookies.set("token", token);
    console.log("bin drinne");
    var decoded = jwt_decode<JwtPayload>(token!!);
    isAuthenticated = !(Date.now() / 1000 >= (decoded.exp ?? 0));
    console.log(Date.now());
    console.log(decoded.exp);
    console.log(isAuthenticated);
    /*
    if (Date.now() >= (decoded.exp ?? 0)) {
      isAuthenticated = false;
    } else {
      isAuthenticated = true;
    }
    */

    if (!isAuthenticated) {
      request.cookies.delete("token");
    }

    if (isAuthenticated) {
      console.log("isAuthenticated!");
    }
  }

  if (!publicRoutes.includes(request.nextUrl.pathname) && !isAuthenticated) {
    return NextResponse.redirect(new URL("/", request.url));
  }
}

const publicRoutes = ["/"];

export const config = {
  matcher: "/((?!api|static|.*\\..*|_next).*)",
};
