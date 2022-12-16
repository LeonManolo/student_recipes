// middleware.ts
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import jwt_decode, { JwtPayload } from "jwt-decode";
import { getCookies, setCookie, deleteCookie, getCookie } from "cookies-next";

// This function can be marked `async` if using `await` inside
export function middleware(request: NextRequest) {
  if (request.nextUrl.pathname.startsWith("/_next")) return NextResponse.next();
  //console.log("middlware: " + localStorage.getItem("token"));

  let jwtToken = request.cookies.get("token")?.value;
  let isAuthenticated = false;
  //console.log("Hallo");
  if (jwtToken != undefined && jwtToken !== "undefined") {
    console.log(typeof jwtToken);
    console.log("token: " + jwtToken);
    request.cookies.set("token", jwtToken);
    var decoded = jwt_decode<JwtPayload>(jwtToken);
    isAuthenticated = !(Date.now() / 1000 >= (decoded.exp ?? 0));
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
  }

  if (!publicRoutes.includes(request.nextUrl.pathname) && !isAuthenticated) {
    return NextResponse.redirect(new URL("/", request.url));
  }
}

const publicRoutes = ["/"];

export const config = {
  matcher: "/((?!api|static|.*\\..*|_next).*)",
};
