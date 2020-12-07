@HtmlForm(method = "post", action = "/users")
public class User {
    @HtmlInput(name = "nickname", placeholder = "Ваш ник")
    private String nickname;
    @HtmlInput(type = "email", name = "email", placeholder = "Ваш email")
    private String email;
    @HtmlInput(type = "password", name = "password", placeholder = "Ваш пароль")
    private String password;
}
