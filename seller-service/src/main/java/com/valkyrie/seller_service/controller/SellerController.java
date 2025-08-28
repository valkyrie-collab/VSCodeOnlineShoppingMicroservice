package com.valkyrie.seller_service.controller;

import com.valkyrie.seller_service.model.Seller;
import com.valkyrie.seller_service.model.SellerDTO;
import com.valkyrie.seller_service.model.Store;
import com.valkyrie.seller_service.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService service;
    @Autowired
    private void setService(SellerService service) {this.service = service;}

    @PostMapping("/save-seller")
    public ResponseEntity<String> save(@RequestParam String token,
                                       @RequestPart MultipartFile imageFile,
                                       @RequestPart MultipartFile documentFile,
                                       @RequestParam String sellerJsonString) throws IOException {
        Store<String> store = service.save(token, sellerJsonString, imageFile, documentFile);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping ("/update-seller")
    public ResponseEntity<String> update(@RequestBody Seller seller) {
        Store<String> store = service.update(seller);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-seller-image")
    public ResponseEntity<String> updateImage(@RequestParam String token,
                                              @RequestPart MultipartFile imageFile) throws IOException {
        Store<String> store = service.updateImage(token, imageFile);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-seller-document")
    public ResponseEntity<String> updateDocument(@RequestParam String token,
                                                 @RequestPart MultipartFile documentFile)
            throws IOException {
        Store<String> store = service.updateDocument(token, documentFile);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-seller-by-id")
    public ResponseEntity<SellerDTO> findSellerById(@RequestParam String token) {
        Store<SellerDTO> store = service.findSellerById(token);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-all-seller")
    public ResponseEntity<List<SellerDTO>> findAllSeller() {
        Store<List<SellerDTO>> store = service.findAllSeller();

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-seller-by-id")
    public ResponseEntity<String> deleteSeller(String token) {
        Store<String> store = service.deleteSellerById(token);

        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
