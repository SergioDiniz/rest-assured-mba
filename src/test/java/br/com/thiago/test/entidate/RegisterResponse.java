package br.com.thiago.test.entidate;

public class RegisterResponse {

    private Long id;
    private String token;

    public RegisterResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public RegisterResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
