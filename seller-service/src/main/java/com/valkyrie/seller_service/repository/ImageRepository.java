package com.valkyrie.seller_service.repository;

import com.valkyrie.seller_service.model.Image;
import com.valkyrie.seller_service.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
//    void deleteBySeller(Seller seller);
    @Modifying
    @Query("DELETE FROM Image i WHERE i.seller.id = :sellerId")
    void deleteBySellerId(@Param("sellerId") String sellerId);

    Image findBySeller(Seller seller);
}
