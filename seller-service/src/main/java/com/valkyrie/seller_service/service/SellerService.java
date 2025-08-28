package com.valkyrie.seller_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valkyrie.seller_service.config.TokenConfig;
import com.valkyrie.seller_service.feign.ProductFeignController;
import com.valkyrie.seller_service.model.*;
import com.valkyrie.seller_service.repository.DocumentRepository;
import com.valkyrie.seller_service.repository.ImageRepository;
import com.valkyrie.seller_service.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SellerService {
    private DocumentRepository documentRepo;
    @Autowired
    private void setDocumentRepo(DocumentRepository documentRepo) {
        this.documentRepo = documentRepo;
    }

    private ImageRepository imageRepo;
    @Autowired
    private void setImageRepo(ImageRepository imageRepo) {this.imageRepo = imageRepo;}

    private SellerRepository sellerRepo;
    @Autowired
    private void setSellerRepo(SellerRepository sellerRepo) {this.sellerRepo = sellerRepo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private ProductFeignController feign;
    @Autowired
    private void setFeign(ProductFeignController feign) {this.feign = feign;}

    private SellerDTO getSeller(Seller seller) {
        ResponseEntity<List<ProductDTO>> product = feign.findBySellerId(seller.getId());

        if (!product.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return null;
        }

        Image image = seller.getImage();
        ImageDTO imageDTO = new ImageDTO().setType(image.getType())
                .setName(image.getName()).setData(image.getData()).setId(image.getId());

        Document document = seller.getDocument();
        DocumentDTO documentDTO = new DocumentDTO().setName(document.getName())
                .setType(document.getType()).setId(document.getId()).setData(document.getData());

        return new SellerDTO().setAddress(seller.getAddress()).setEmail(seller.getEmail()).setName(seller.getName())
                .setImage(imageDTO).setDocument(documentDTO).setProducts(product.getBody())
                .setBusiness(seller.getBusiness()).setBankAccountDetails(seller.getBankAccountDetails())
                .setId(seller.getId()).setDescription(seller.getDescription()).setStatus(seller.getStatus())
                .setGstNumber(seller.getGstNumber()).setPhoneNumber(seller.getPhoneNumber())
                .setRegistrationDate(seller.getRegistrationDate()).setRating(seller.getRating());
    }

    @Transactional
    public Store<String> save(String token, String sellerJsonString,
                              MultipartFile imageFile, MultipartFile documentFile) throws IOException {
        Seller seller = new ObjectMapper().readValue(sellerJsonString, Seller.class);
        Image image = new Image().setName(imageFile.getOriginalFilename()).setSeller(seller)
                .setData(imageFile.getBytes()).setType(imageFile.getContentType());
        Document document = new Document().setData(documentFile.getBytes()).setSeller(seller)
                .setType(documentFile.getContentType()).setName(documentFile.getOriginalFilename());
        sellerRepo.save(seller.setImage(image).setDocument(document).setId(config.getUsername(token)));

        return Store.initialize(HttpStatus.ACCEPTED, "The Seller saved successfully......");
    }

    @Transactional
    public Store<String> update(Seller seller) {
        Seller presentSeller = sellerRepo.findById(seller.getId()).orElse(null);

        if (presentSeller == null) {return Store.initialize(HttpStatus.BAD_REQUEST, "Seller not present...");}

        if (!seller.toString().equals(presentSeller.toString())) {
            sellerRepo.save(seller);

            return Store.initialize(HttpStatus.ACCEPTED, "The Seller is updated successfully....");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Same data cannot be updated....");
    }

    @Transactional
    public Store<String> updateImage(String token, MultipartFile imageFile) throws IOException {
        Seller seller = sellerRepo.findById(config.getUsername(token)).orElse(null);

        if (seller == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "The seller is not present..");
        }

        imageRepo.deleteBySellerId(seller.getId());

        if (imageRepo.findBySeller(seller) != null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Image not deleted....");
        }

        Image image = new Image().setName(imageFile.getOriginalFilename()).setSeller(seller)
                .setData(imageFile.getBytes()).setType(imageFile.getContentType());

        imageRepo.save(image);

        return Store.initialize(HttpStatus.ACCEPTED, "Update has been successful....");
    }

    @Transactional
    public Store<String> updateDocument(String token, MultipartFile documentFile) throws IOException {
        Seller seller = sellerRepo.findById(config.getUsername(token)).orElse(null);

        if (seller == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Seller not present to update..");
        }

        documentRepo.deleteDocumentBySellerId(seller.getId());

        if (documentRepo.findBySeller(seller) != null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Image not deleted....");
        }

        Document document = new Document().setName(documentFile.getOriginalFilename())
                .setData(documentFile.getBytes()).setSeller(seller)
                .setType(documentFile.getContentType());

        documentRepo.save(document);

        return Store.initialize(HttpStatus.ACCEPTED, "document updated successfully...");
    }

    @Transactional
    public Store<SellerDTO> findSellerById(String token) {
        String id = config.getUsername(token);
        Seller seller = sellerRepo.findById(id).orElse(null);

        if (seller == null) {return Store.initialize(HttpStatus.BAD_REQUEST, null);}

        return Store.initialize(HttpStatus.OK, getSeller(seller));
    }

    @Transactional
    public Store<List<SellerDTO>> findAllSeller() {
        List<Seller> sellers = sellerRepo.findAll();
        List<SellerDTO> sellerDTOs = sellers.stream().map(
                seller -> new SellerDTO().setName(seller.getName())
        ).toList();

        return Store.initialize(HttpStatus.OK, sellerDTOs);
    }

    @Transactional
    public Store<String> deleteSellerById(String token) {
        String username = config.getUsername(token);

        if (sellerRepo.findById(username).orElse(null) == null) {
            return Store.initialize(HttpStatus.OK, "The seller is already been deleted....");
        }

        feign.deleteAllBySellerId(username);
        sellerRepo.deleteById(username);

        return sellerRepo.findById(username).orElse(null) == null?
                Store.initialize(HttpStatus.OK, "The Seller has been delete successfully...") :
                Store.initialize(HttpStatus.BAD_REQUEST, "The seller is not deleted");
    }
}
