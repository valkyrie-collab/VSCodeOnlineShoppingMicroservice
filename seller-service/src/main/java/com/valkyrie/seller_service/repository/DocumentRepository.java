package com.valkyrie.seller_service.repository;

import com.valkyrie.seller_service.model.Document;
import com.valkyrie.seller_service.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Modifying
    @Query("DELETE FROM Document d WHERE d.seller.id = :sellerId")
    void deleteDocumentBySellerId(@Param("sellerId") String sellerId);

    Document findBySeller(Seller seller);
}
