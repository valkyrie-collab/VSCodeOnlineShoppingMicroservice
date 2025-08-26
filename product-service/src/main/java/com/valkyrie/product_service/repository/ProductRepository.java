package com.valkyrie.product_service.repository;

import java.util.List;

import com.valkyrie.product_service.model.Star;
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

//    @Query("SELECT p.oneStar, p.twoStar, p.threeStar, p.fourStar, p.fiveStar FROM Star p WHERE p.id = :productId")
    @Query("SELECT s FROM Star s WHERE s.product.id = :productId")
    List<Star> findStarsFromProduct(@Param("productId") String productId);

    @Query("select s from Star s where s.customerId = :customerId")
    Star findStarFromProductByCustomerId(@Param("customerId") String customerId);

    @Modifying
    @Query("UPDATE Star s SET s.oneStar = :oneStar, s.twoStar = :twoStar, s.threeStar = :threeStar," +
            " s.fourStar = :fourStar, s.fiveStar = :fiveStar WHERE s.id = :id")
    void updateStarRatings(@Param("id") String id, @Param("oneStar") int oneStar,
                            @Param("twoStar") int twoStar, @Param("threeStar") int threeStar,
                            @Param("fourStar") int fourStar, @Param("fiveStar") int fiveStar);
//    @Modifying
//    @Query("UPDATE Star s SET s.oneStar = :oneStar, s.twoStar = :twoStar, s.threeStar = :threeStar," +
//            " s.fourStar = :fourStar, s.fiveStar = :fiveStar, s.customerId = :customerId")
//    void saveStarRatingByCustomerId(@Param("customerId") String customerId, @Param("oneStar") int oneStar,
//                                    @Param("twoStar") int twoStar, @Param("threeStar") int threeStar,
//                                    @Param("fourStar") int fourStar, @Param("fiveStar") int fiveStar);

    @Query("UPDATE Star s SET s.oneStar = :oneStar, s.twoStar = :twoStar, s.threeStar = :threeStar," +
            " s.fourStar = :fourStar, s.fiveStar = :fiveStar WHERE s.customerId = :customerId")
    void updateStarRatingsByCustomerId(@Param("customerId") String customerId, @Param("oneStar") int oneStar,
                           @Param("twoStar") int twoStar, @Param("threeStar") int threeStar,
                           @Param("fourStar") int fourStar, @Param("fiveStar") int fiveStar);

    List<Product> findAllBySellerId(String sellerId);

    void deleteAllBySellerId(String sellerId);
}
