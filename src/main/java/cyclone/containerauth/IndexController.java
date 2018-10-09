package cyclone.containerauth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class IndexController {

    @GetMapping("/public")
    public String pub() {
        return "hello from public";
    }

    @GetMapping("/private")
    public String priv(HttpServletRequest request, Principal principal) {
        String response = "hello from private";
        response += "<br>principal = " + principal;
        if(principal instanceof PreAuthenticatedAuthenticationToken) {
            PreAuthenticatedAuthenticationToken preAuthToken = ((PreAuthenticatedAuthenticationToken) principal);
            response += "<br>preAuthToken = "+preAuthToken;
            response += "<br>preAuthToken.toName() = "+preAuthToken.getName();
            response += "<br>preAuthToken.getCredentials() = "+preAuthToken.getCredentials();
            if(preAuthToken.getPrincipal() instanceof User) {
                User user = (User) preAuthToken.getPrincipal();
                response += "<br>user = "+user;
                response += "<br>user.getUsername() = "+user.getUsername();
                response += "<br>user.getPassword() = "+user.getPassword();
                response += "<br>user.getAuthorities() = "+user.getAuthorities();
            } else {
                response += "<br>preAuthToken.getPrincipal() is NOT instanceof org.springframework.security.core.userdetails.User";
                response += "<br>preAuthToken.getPrincipal() = "+preAuthToken.getPrincipal();
            }

            response += "<br>preAuthToken.getAuthorities() = "+preAuthToken.getAuthorities();
            if(preAuthToken.getDetails() instanceof PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) {
                PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails details = (PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) preAuthToken.getDetails();
                response += "<br>details = "+details;
                response += "<br>details.getGrantedAuthorities() = "+details.getGrantedAuthorities();
            } else {
                response += "<br>preAuthToken.getDetails() is NOT instanceof PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails";
                response += "<br>preAuthToken.getDetails() = "+preAuthToken.getDetails();
            }
        } else {
            response += "<br>principal is NOT instanceof PreAuthenticatedAuthenticationToken";
            response += "<br>principal.getName() = " + principal.getName();
            response += "<br>principal.getClass() = " + principal.getClass();
        }

        response += "<br>httpServletRequest.isUserInRole(\"personal_office\") = " + request.isUserInRole("personal_office");
        response += "<br>httpServletRequest.isUserInRole(\"ROLE_personal_office\") = " + request.isUserInRole("ROLE_personal_office");
        response += "<br>httpServletRequest.isUserInRole(\"PERSONAL_OFFICE\") = " + request.isUserInRole("PERSONAL_OFFICE");
        response += "<br>httpServletRequest.isUserInRole(\"ROLE_PERSONAL_OFFICE\") = " + request.isUserInRole("ROLE_PERSONAL_OFFICE");

        return response;
    }


    @GetMapping("/login")
    public String login() {
        return "\n" +
                "<html><head><title>Login Page</title></head><body onload='document.f.username.focus();'>\n" +
                "<h3>Custom Login with Username and Password</h3><form name='f' action='j_security_check' method='POST'>\n" +
                "<table>\n" +
                "\t<tr><td>User:</td><td><input type='text' name='j_username' value=''></td></tr>\n" +
                "\t<tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>\n" +
                "\t<tr><td colspan='2'><input name=\"submit\" type=\"submit\" value=\"Login\"/></td></tr>\n" +
                "</table>\n" +
                "</form></body></html>";
    }


}
