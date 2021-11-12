package com.myretail.controller;

import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.business.PriceInfo;
import com.myretail.model.business.Product;
import com.myretail.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


/**
 * This class provides unit test case for ProductController. It tests
 * getProductDetails and update API method.
 *
 * @author Ashuthosh c 11-09-2021
 */
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {
    /**
     * MockObjects are created against which the unit test case is run.
     * MockService of ProductService is created and the unit test case is
     * executed.
     */
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    PriceInfo priceInfo = new PriceInfo("USD", (float) 99.9);
    Long productID = (long) 13860428;
    String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"currency_code\":\"USD\",\"value\":99.9}}";
    Product mockProduct = new Product(productID, "The Big Lebowski (Blu-ray)", priceInfo);

    /**
     * This method tests getProduct REST API call. It performs a Mock HTTP
     * request for a specific productID and the response is evaluated against
     * the specific mock product.
     *
     * @throws Exception
     */
    @Test
    public void getProductDetails() throws Exception {

        Mockito.when(productService.getProductInfo(Mockito.anyLong())).thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/" + productID)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        System.out.println("UnitTestSuccessful");
    }

    @Test
    public void notFoundStatusWhenProductNotFound() throws Exception {
        Mockito.when(productService.getProductInfo(Mockito.anyLong())).thenThrow(new ProductNotFoundException(""));
        String url = "/products/123456";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
