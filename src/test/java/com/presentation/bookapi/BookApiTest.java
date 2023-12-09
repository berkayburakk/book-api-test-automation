package com.presentation.bookapi;

import com.presentation.BaseTest;
import com.presentation.model.error.ErrorModel;
import com.presentation.model.response.BookListResponseModel;
import com.presentation.model.response.BookResponseModel;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.presentation.client.constants.MatcherMessage.*;
import static com.presentation.client.customassertions.CustomAssertion.assertResponseTimeLessThan;
import static com.presentation.data.BookDataFactory.NOT_FOUND_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class BookApiTest extends BaseTest {

    @Test
    @Description("Should create book successfully with valid payload.")
    public void BookController_PostBook_ShouldCreateBookSuccessfully() {
        //Given
        var bookRequestModel = bookDataFactory.generateBookBody();

        //When
        Response response = bookRequests.postBook(bookRequestModel);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        var book = response.as(BookResponseModel.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(bookRequestModel.getBookName(), book.getBookName() , "Book name is not equal");
        softAssert.assertEquals(bookRequestModel.getBarcode(), book.getBarcode(), "Barcode is not equal");
        softAssert.assertEquals(bookRequestModel.getAuthor(), book.getAuthor(), "Author is not equal");
        softAssert.assertEquals(bookRequestModel.getCategory(), book.getCategory(), "Category is not equal");
        softAssert.assertAll();

//        assertEquals(bookRequestModel.getBookName(), book.getBookName(), "Book name is not equal");
//        assertEquals(bookRequestModel.getBarcode(), book.getBarcode(), "Book barcode is not equal");
//        assertEquals(bookRequestModel.getAuthor(), book.getAuthor(), "Book author is not equal");
//        assertEquals(bookRequestModel.getCategory(), book.getCategory(), "Book category is not equal");

    }

    @Test
    @Description("Should not create when Book's barcode exist")
    public void BookController_PostBook_WhenBookBarcodeExists_ShouldReturnBadRequest() {
        //Precondition
        var newBook = bookDataFactory.createNewBook();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBarcode(newBook.getBarcode());

        //When
        var response = bookRequests.postBook(body);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BARCODE_ALREADY_EXIST);
    }

    @Test
    @Description("Should not create when Book's barcode null")
    public void BookController_PostBook_WithoutBarcode_ShouldReturnBadRequest() {
        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBarcode(null);

        //When
        var response = bookRequests.postBook(body);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BARCODE_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not create when Book's category null")
    public void BookController_PostBook_WithoutCategory_ShouldReturnBadRequest() {
        //Given
        var body = bookDataFactory.generateBookBody();
        body.setCategory(null);

        //When
        var response = bookRequests.postBook(body);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), CATEGORY_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not create when Book's author null")
    public void BookController_PostBook_WithoutAuthor_ShouldReturnBadRequest() {
        //Given
        var body = bookDataFactory.generateBookBody();
        body.setAuthor(null);

        //When
        var response = bookRequests.postBook(body);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), AUTHOR_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not create when Book's Book name null")
    public void BookController_PostBook_WithoutBookName_ShouldReturnBadRequest() {
        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBookName(null);

        //When
        var response = bookRequests.postBook(body);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BOOK_NAME_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should update Book successfully")
    public void BookController_PutBook_ShouldUpdateBookSuccessfully() {
        //Precondition
        var newBook = bookDataFactory.createNewBook();
        Integer bookId = newBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();

        //When
        Response response = bookRequests.putBook(body, bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        var book = response.as(BookResponseModel.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(body.getBookName(), book.getBookName(), "Book name is not equal");
        softAssert.assertEquals(body.getBarcode(), book.getBarcode(), "Barcode is not equal");
        softAssert.assertEquals(body.getAuthor(), book.getAuthor(), "Author is not equal");
        softAssert.assertEquals(body.getCategory(), book.getCategory(), "Category is not equal");

        softAssert.assertAll();
    }

    @Test
    @Description("Should not update when Book's barcode exist for other Book")
    public void BookController_PutBook_WhenBooksBarcodeExistsForOtherBook_ShouldReturnBadRequest() {
        //Precondition
        var firstBook = bookDataFactory.createNewBook();
        var secondBook = bookDataFactory.createNewBook();
        var secondBookId = secondBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBarcode(firstBook.getBarcode());

        //When
        var response = bookRequests.putBook(body, secondBookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        var errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BARCODE_ALREADY_EXIST);
    }

    @Test
    @Description("Should update Book successfully with valid payload.")
    public void BookController_PutBook_WhenBookNotExist_ShouldReturnNotFound() {
        //Given
        var body = bookDataFactory.generateBookBody();

        //When
        Response response = bookRequests.putBook(body, NOT_FOUND_ID);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);
    }

    @Test
    @Description("Should not update when Book's author null")
    public void BookController_PutBook_WithoutAuthor_ShouldReturnBadRequest() {
        var newBook = bookDataFactory.createNewBook();
        Integer BookId = newBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setAuthor(null);

        //When
        Response response = bookRequests.putBook(body, BookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), AUTHOR_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not update when Book's Book name null")
    public void BookController_PutBook_WithoutBookName_ShouldReturnBadRequest() {
        var newBook = bookDataFactory.createNewBook();
        Integer bookId = newBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBookName(null);

        //When
        Response response = bookRequests.putBook(body, bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BOOK_NAME_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not update when Book's category null")
    public void BookController_PutBook_WithoutCategory_ShouldReturnBadRequest() {
        var newBook = bookDataFactory.createNewBook();
        Integer bookId = newBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setCategory(null);

        //When
        Response response = bookRequests.putBook(body, bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), CATEGORY_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should not update when Book's barcode null")
    public void BookController_PutBook_WithoutBarcode_ShouldReturnBadRequest() {
        var newBook = bookDataFactory.createNewBook();
        Integer bookId = newBook.getId();

        //Given
        var body = bookDataFactory.generateBookBody();
        body.setBarcode(null);

        //When
        Response response = bookRequests.putBook(body, bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        ErrorModel errorModel = response.as(ErrorModel.class);
        assertEquals(errorModel.getError(), BARCODE_CAN_NOT_BE_EMPTY);
    }

    @Test
    @Description("Should get book by id successfully")
    public void BookController_GetBookById_ShouldGetBookSuccessfully() {
        //Precondition
        var newBook = bookDataFactory.createNewBook();

        //Given
        var bookId = newBook.getId();

        //When
        var response = bookRequests.getBookById(bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 500);

        var book = response.as(BookResponseModel.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(newBook.getBookName(), book.getBookName(), "Book name is not equal");
        softAssert.assertEquals(newBook.getBarcode(), book.getBarcode(), "Book barcode is not equal");
        softAssert.assertEquals(newBook.getAuthor(), book.getAuthor(), "Book author is not equal");
        softAssert.assertEquals(newBook.getCategory(), book.getCategory(), "Book category is not equal");
        softAssert.assertAll();
    }

    @Test
    @Description("Should return not found when book not exist")
    public void BookController_GetBookById_WhenBookNotExist_ShouldReturnNotFound() {
        //When
        var response = bookRequests.getBookById(NOT_FOUND_ID);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);
    }

    @Test
    @Description("Should get all books successfully")
    public void BookController_GetBooks_ShouldGetAllBookSuccessfully() {
        //Precondition
        bookDataFactory.createNewBook();

        //When
        var response = bookRequests.getBooks();

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);

        var books = response.as(BookListResponseModel.class);

        SoftAssert softAssert = new SoftAssert();
        books.getBooks().forEach(data -> {
            softAssert.assertNotNull(data.getBookName(), "Book name should not be null");
            softAssert.assertNotNull(data.getBarcode(), "Book barcode should not be null");
            softAssert.assertNotNull(data.getAuthor(), "Book author should not be null");
            softAssert.assertNotNull(data.getCategory(), "Book category should not be null");
        });

        softAssert.assertAll();
    }

    @Test
    @Description("Should delete given book successfully")
    public void BookController_DeleteBookById_ShouldDeleteBookSuccessfully() {
        //Precondition
        var newBook = bookDataFactory.createNewBook();

        //Given
        var bookId = newBook.getId();

        //When
        var response = bookRequests.deleteBookById(bookId);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);
    }

    @Test
    @Description("Should return not found when book not exist")
    public void BookController_DeleteBookById_WhenBookNotExist_ShouldReturnNotFound() {
        //When
        var response = bookRequests.deleteBookById(NOT_FOUND_ID);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, RESPONSE_CODE_DOES_NOT_MATCH);
        assertResponseTimeLessThan(response, 1000);
    }

    @Test(enabled = false)
    public void RestAssuredExampleTest_1() {
        RestAssured.baseURI = "http://localhost:3000";

        given()
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body("{\n" +
                        "  \"author\": \"Jules Verne\",\n" +
                        "  \"barcode\": \"67865451\",\n" +
                        "  \"bookName\": \"Around the World in Eighty Days\",\n" +
                        "  \"category\": \"Adventure\"\n" +
                        "}")
        .when()
                .post("/books")
        .then()
                .statusCode(201)
                .body("BookName", equalTo("Around the World in Eighty Days"))
                .body("Author", equalTo("Jules Verne"))
                .body("Barcode", equalTo("67865451"))
                .body("Category", equalTo("Adventure"))
                .body("id", notNullValue());

    }

    @Test(enabled = false)
    public void RestAssuredExampleTest_2() {
        RestAssured.baseURI = "http://localhost:3000";

        given()
                .header("accept", "application/json")
                .pathParams("id", 2)
                .when()
        .get("/books/{id}")
        .then()
                .body("Barcode", is("67865451"))
                .statusCode(200);
    }

    @Test(enabled = false)
    public void RestAssuredExampleTest_3() {
        RestAssured.baseURI = "http://localhost:3000";

        given()
                .header("accept", "application/json")
                .pathParams("id", -1)
        .when()
                .get("/books/{id}")
        .then()
                .statusCode(404);
    }

}
