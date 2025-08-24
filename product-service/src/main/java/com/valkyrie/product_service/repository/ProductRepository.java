package com.valkyrie.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.valkyrie.product_service.model.Image;
import com.valkyrie.product_service.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Query("DELETE FROM Image i WHERE i.product.id = :productId")
    void deleteAllImageById(@Param("productId") String productId);

    @Query("SELECT i FROM Image i WHERE i.product.id = :productId")
    List<Image> findAllImageById(@Param("productId") String productId);

    @Query("SELECT p FROM Product p WHERE p.searchKeyword LIKE %:searchKeyword%")
    List<Product> findAllProductBySearchKeyword(@Param("searchKeyword") String searchKeyword);

    @Query("SELECT p.oneStar, p.twoStar, p.threeStar, p.fourStar, p.fiveStar FROM Product p WHERE p.id = :productId")
    Integer[] findStarsFromProduct(@Param("productId") String productId);

    @Modifying
    @Query("UPDATE Product p SET p.oneStar = :oneStar, p.twoStar = :twoStar, p.threeStar = :threeStar, p.fourStar = :fourStar, p.fiveStar = :fiveStar WHERE p.id = :productId")
    int updateStarRatings(@Param("productId") String productId, @Param("oneStar") int oneStar,
                            @Param("twoStar") int twoStar, @Param("threeStar") int threeStar,
                            @Param("fourStar") int fourStar, @Param("fiveStar") int fiveStar);

    List<Product> findAllBySellerId(String sellerId);

    void deleteAllBySellerId(String sellerId);
}
