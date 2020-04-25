package br.com.thiago.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import br.com.thiago.test.entidate.RegisterRequest;
import br.com.thiago.test.entidate.RegisterResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.thiago.servicos.Servicos;
import br.com.thiago.test.entidate.PessoaRequest;
import br.com.thiago.test.entidate.PessoaResponse;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ReqResExample {

    @Before
    public void configuraApi() {
        baseURI = "https://reqres.in/";
    }

    @Test
    public void getUserNotFound() {
        PessoaResponse as =
                given()
                        .when()
                        .get(Servicos.getUsers_ID.getValor(), 23)
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .and().extract().response().as(PessoaResponse.class);

        Assert.assertNotNull(as);
        Assert.assertNull(as.getId());

    }

    @Test
    public void postRegister() {
        RegisterRequest request = new RegisterRequest("eve.holt@reqres.in", "pistol");

        RegisterResponse rr =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(Servicos.postRegister.getValor())
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/registerResponseDocument.json"))
                        .extract().response().as(RegisterResponse.class);

        Assert.assertNotNull(rr);
        Assert.assertNotNull(rr.getId());
        Assert.assertNotNull(rr.getToken());

    }

    @Test
    public void putUser() {
        PessoaRequest request = new PessoaRequest("morpheus", "zion resident");
        PessoaResponse rr =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .put(Servicos.getUsers_ID.getValor(), 2)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/putUserResponseDocument.json"))
                        .extract().response().as(PessoaResponse.class);

        Assert.assertNotNull(rr);
        Assert.assertNotNull(rr.getUpdatedAt());
        Assert.assertEquals(request.getNome(), rr.getNome());
        Assert.assertEquals(request.getJob(), rr.getJob());

    }

    @Test
    public void patchUser() {
        PessoaRequest request = new PessoaRequest("morpheus", "zion resident");
        PessoaResponse rr =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .patch(Servicos.getUsers_ID.getValor(), 2)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/patchUserResponseDocument.json"))
                        .extract().response().as(PessoaResponse.class);

        Assert.assertNotNull(rr);
        Assert.assertNotNull(rr.getUpdatedAt());
        Assert.assertEquals(request.getNome(), rr.getNome());
        Assert.assertEquals(request.getJob(), rr.getJob());

    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete(Servicos.getUsers_ID.getValor(), 23)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }



    @Test
    public void methodGet() {
//		PessoaResponse as =
        given()
                .when()
                .get(Servicos.getUsers_ID.getValor(), 2)
                .then().contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/thiagoExample.json"))
                .log().all();
//			.and().extract().response().as(PessoaResponse.class);


    }

    @Test
    public void methodPost() {
        PessoaRequest pessoaRequest = new PessoaRequest("thiago", "QA");

        basePath = "/api/users";
        PessoaResponse as = given()
                .contentType("application/json")
                .body(pessoaRequest)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response().as(PessoaResponse.class);

        Assert.assertNotNull(as);
        Assert.assertNotNull(as.getId());
        Assert.assertEquals(pessoaRequest.getNome(), as.getNome());
        Assert.assertEquals(pessoaRequest.getJob(), as.getJob());
    }

    @Test
    public void methodPost2() {
        PessoaRequest pessoaRequest = new PessoaRequest("thiago", "QA");

        basePath = "/api/users";
        String as =
                given()
                        .contentType("application/json")
                        .body(pessoaRequest)
                        .when()
                        .post("/")
                        .then()
                        .statusCode(HttpStatus.SC_CREATED).log().all()
                        .and().extract().response().path("nome");

        System.out.println(as);
        Assert.assertNotNull(as);
    }
}
