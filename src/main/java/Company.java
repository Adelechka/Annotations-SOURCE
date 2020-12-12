@HtmlForm(method = "post", action = "/company")
public class Company {
        @HtmlInput(name = "name", placeholder = "Название")
        private String name;
        @HtmlInput(name = "address", placeholder = "Ваш адрес")
        private String address;
}
