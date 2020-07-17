package sqli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private static String loginHtml(String sql) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<form method=\"POST\" action=\"/login\">" +
                "<label for=\"fname\">Login:</label><br>" +
                "<input type=\"text\" id=\"login\" name=\"login\" value=\"\"><br>" +
                "<label for=\"lname\">Password:</label><br>" +
                "<input type=\"text\" id=\"password\" name=\"password\" value=\"\"><br><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form> " +
                (!sql.equals("") ? "<br>SQL: " + sql + "<br>" : "") +
                "</body>" +
                "</html>";
    }

    private static String successHtml(String sql) {
        return "Logged in!" +
                (!sql.equals("") ? "<br>SQL: " + sql + "<br>" : "");
    }


    private final static LoginHandler handler = new LoginHandler();

    @Controller
    static class LoginController {

        @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
        @ResponseBody
        public String welcome() {
            return loginHtml("");
        }

        @PostMapping(value = "/login")
        @ResponseBody
        public String login(String login, String password) {

            LoginHandler.Result result = handler.login(login, password);

            if (result.success) {
                return successHtml(result.sql);
            } else {
                return loginHtml(result.sql);
            }

        }

    }

}
