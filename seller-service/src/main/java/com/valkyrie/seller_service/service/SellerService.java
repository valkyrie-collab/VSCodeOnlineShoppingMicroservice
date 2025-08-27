package com.valkyrie.seller_service.service;

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
import org.springframework.web.multipart.MultipartFile;

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

    public Store<String> save(String token, String sellerJsonString, List<MultipartFile> imageFiles) {


        seller = seller.setId(config.getUsername(token));
        sellerRepo.save(seller);

        return Store.initialize(HttpStatus.ACCEPTED, "The Seller saved successfully......");
    }

    public Store<String> update(Seller seller) {
        Seller presentSeller = sellerRepo.findById(seller.getId()).orElse(null);

        if (presentSeller == null) {return Store.initialize(HttpStatus.BAD_REQUEST, "Seller not present...");}

        if (!seller.toString().equals(presentSeller.toString())) {
            sellerRepo.save(seller);

            return Store.initialize(HttpStatus.ACCEPTED, "The Seller is updated successfully....");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Same data cannot be updated....");
    }

    public Store<String> updateImage(String token, List<Image> images) {
        List<ImageDTO> imageDTOs = images.stream().map(
                image -> new ImageDTO().setName(image.getName())
                        .setType(image.getType()).setData(image.getData()).setId(image.getId())
        ).toList();

    }

    public Store<SellerDTO> findSellerById(String token) {
        String id = config.getUsername(token);
        Seller seller = sellerRepo.findById(id).orElse(null);

        if (seller == null) {return Store.initialize(HttpStatus.BAD_REQUEST, null);}

        return Store.initialize(HttpStatus.OK, getSeller(seller));
    }

    public Store<List<SellerDTO>> findAllSeller() {
        List<Seller> sellers = sellerRepo.findAll();
        List<SellerDTO> sellerDTOs = sellers.stream().map(
                seller -> new SellerDTO().setName(seller.getName())
        ).toList();

        return Store.initialize(HttpStatus.OK, sellerDTOs);
    }
}
