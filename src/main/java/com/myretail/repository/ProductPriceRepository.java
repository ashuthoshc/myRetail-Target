package com.myretail.repository;

import com.myretail.model.repository.ProductPrice;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * This is the DAO Object for productprices table which is used to get and
 * update the price information from Cassandra Database.
 *
 * @author Ashuthosh c 11-09-2021
 */

public interface ProductPriceRepository extends CassandraRepository<ProductPrice, String> {

    /**
     * This method is responsible for retrieving the price information for a
     * given productID
     *
     * @param productID productID of the product
     * @return returns ProductPrice object if productID is present in database
     * otherwise null
     */

    @Query("SELECT productID,currency,price FROM productprices WHERE productID=?0")
    public ProductPrice getProductPrice(Long productID);

    /**
     * This method is responsible for updating the price information of given
     * productID
     *
     * @param productID productID of the product
     * @param price     new price to be updated for the given productID
     * @return true if update is successful and false otherwise
     */

    @Query("UPDATE myretail.productprices SET price=?1 WHERE productID=?0 IF EXISTS")
    public boolean updateProductPrice(Long productID, Float price);
}
