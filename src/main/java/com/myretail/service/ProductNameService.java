package com.myretail.service;

import com.myretail.constant.APIConstants;
import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.service.ProductName;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for constructing the ProductName object by parsing
 * the JSON object returned by Target end point
 *
 * @author Ashuthosh c 11-09-2021
 */
@Service
@PropertySource("classpath:application.properties")

public class ProductNameService {

    @Value("${restservice.product.baseurl:https://redsky.target.com/v3/pdp/tcin/}")
    private String baseUrl;

    @Value("${restservice.product.excludeparams:?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate}")
    private String excludeParams;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * This method handles the call to Target RestService
     *
     * @param productID productID of the product given as input
     * @return ProductName
     * @throws Exception
     */
    public ProductName getProductName(Long productID) throws Exception {

        String endPointURL = baseUrl + productID;
        String endPointResponse = null;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(endPointURL, String.class);
            endPointResponse = response.getBody();

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new ProductNotFoundException("No matching product found for the given ProductID");
        }
        if (endPointResponse == null) {
            throw new ProductNotFoundException("No matching product found for the given ProductID");
        }
        JSONObject requestJsonObject = new JSONObject(endPointResponse);
        ProductName productName = parseJson(requestJsonObject);
        return productName;
    }

    /**
     * This method parses the JSON object and constructs ProductName Object
     *
     * @param requestJsonObject This is the JSON object returned by Target end point.
     * @return ProductName
     * @throws JSONException
     */
    public ProductName parseJson(JSONObject requestJsonObject) throws JSONException {
        ProductName productName = new ProductName();
        try {
            JSONObject data = requestJsonObject.getJSONObject(APIConstants.DATA);
            JSONObject product = data.getJSONObject(APIConstants.PRODUCT);
            JSONObject item = product.getJSONObject(APIConstants.ITEM);
            JSONObject productDescription = item.getJSONObject(APIConstants.PRODUCT_DESCRIPTION);
            productName.setProductName(productDescription.getString(APIConstants.TITLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productName;
    }

}
