package sqli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    String loginHtml = "<!DOCTYPE html>" +
            "<html>" +
            "<body>" +
            "<form action=\"/login\">" +
            "<label for=\"fname\">Login:</label><br>" +
            "<input type=\"text\" id=\"login\" name=\"login\" value=\"\"><br>" +
            "<label for=\"lname\">Password:</label><br>" +
            "<input type=\"text\" id=\"password\" name=\"password\" value=\"\"><br><br>" +
            "<input type=\"submit\" value=\"Submit\">" +
            "</form> " +
            "</body>" +
            "</html>";

    String successHtml = "Logged in!";

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String welcome() {
        return loginHtml;
    }

}
