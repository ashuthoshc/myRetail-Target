package com.myretail.service;

import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.business.PriceInfo;
import com.myretail.model.business.Product;
import com.myretail.model.repository.ProductPrice;
import com.myretail.model.service.ProductName;
import com.myretail.repository.ProductPriceRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ashuthosh c 11-10-2021
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration( exclude = {
        org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration.class
})
public class ProductServiceTest extends TestCase {

    @InjectMocks
    ProductService productService;

    @Mock // -- Spring Boot
    ProductPriceRepository productrepositoryMock;

    @Mock
    private ProductNameService productInfoClient;

    /**
     * Setup for Mockito before any test run.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Positive test.
     */
    @Test
    public void getProductByIdTest() throws Exception {

        ProductPrice mockProductPrice = new ProductPrice();
        Mockito.when(productrepositoryMock.getProductPrice(Mockito.anyLong())).thenReturn(mockProductPrice);
        Mockito.when(productInfoClient.getProductName(13860428L))
                .thenReturn(new ProductName());

        // Actual Result
        Product actualProduct = productService.getProductInfo(13860428L);

        Product expectedProduct = new Product(13860428L, "The Big Lebowski (Blu-ray)", new PriceInfo("USD", 13.95f));

        assertEquals(expectedProduct.getId(), actualProduct.getId());
    }

    /**
     * @throws Exception Check for null pointer exception when wrong product id passed to Remote service.
     */
    @Test(expected = NullPointerException.class)
    public void throwNullException() throws Exception {

        ProductPrice mockProductPrice = new ProductPrice();
        Mockito.when(productrepositoryMock.getProductPrice(Mockito.anyLong())).thenReturn(mockProductPrice);

        productService.getProductInfo(13860428L);
    }

    /**
     * @throws Exception Check for null pointer exception when wrong product id passed to Remote service.
     */
    @Test(expected = ProductNotFoundException.class)
    public void throwProductNotFoundException() throws Exception {

        ProductPrice mockProductPrice = new ProductPrice();
        Mockito.when(productInfoClient.getProductName(123l)).thenThrow(ProductNotFoundException.class);

        productService.getProductInfo(123l);
    }
}
