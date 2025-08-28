package com.valkyrie.product_service.service;

import java.util.*;

import com.valkyrie.product_service.model.*;
import com.valkyrie.product_service.repository.ImageRepository;
import com.valkyrie.product_service.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.product_service.config.TokenConfig;
import com.valkyrie.product_service.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository repo;
    @Autowired
    private void setRepo(ProductRepository repo) {this.repo = repo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private StarRepository starRepo;
    @Autowired
    private void setStarRepo(StarRepository starRepo) {this.starRepo = starRepo;}

    private ImageRepository imageRepo;
    @Autowired
    private void setImageRepo(ImageRepository imageRepo) {this.imageRepo = imageRepo;}

    private ProductDTO getProduct(Product product) {
        List<Image> images = product.getImages();
        List<ImageDTO> imagesDTO = new LinkedList<>();
        
        for (Image image : images) {
            imagesDTO.add(
                new ImageDTO().setId(image.getId()).setName(image.getName())
                            .setType(image.getType()).setData(image.getData())
            );
        }

//        List<Star> stars = product.getStars();
        List<StarDTO> starDTOs = product.getStars().stream().map(star -> {
            return new StarDTO().setFiveStar(star.getFiveStar()).setCustomerId(star.getCustomerId())
                    .setFourStar(star.getFourStar()).setOneStar(star.getOneStar()).setId(star.getId())
                    .setTwoStar(star.getTwoStar()).setThreeStar(star.getThreeStar());}).toList();

        double discountedPrice = product.getPrice() - ((product.getDiscount() / 100.0) * product.getPrice());

        return new ProductDTO().setBrand(product.getBrand()).setCategory(product.getCategory())
                .setColor(product.getColor()).setDescription(product.getDescription())
                .setDiscount(product.getDiscount()).setDiscountedPrice(discountedPrice)
                .setVariant(product.getVariant()).setStatus(product.getStatus())
                .setName(product.getName()).setShippingInformation(product.getShippingInformation())
                .setPrice(product.getPrice()).setQuantity(product.getQuantity()).setRating(
                        starDTOs.stream().map(StarDTO::getRating).toList()
                ).setSize(product.getSize()).setSearchKeyword(product.getSearchKeyword())
                .setId(product.getId()).setImages(imagesDTO).setCustomerId(
                        starDTOs.stream().map(StarDTO::getCustomerId).toList()
                );
    }

    public Store<String> save(String token, Product product) {
        String uuid = UUID.randomUUID().toString();
        String username = config.getUsername(token);
        repo.save(product.setId(uuid).setSellerId(username)); //username

        return Store.initialize(HttpStatus.ACCEPTED, "The Product saved successfully.....");
    }

    @Transactional
    public Store<String> update(String token, Product product) {
        String username = config.getUsername(token);
        product = product.setSellerId(username);
        Product presentProduct = repo.findById(product.getId()).orElse(product);
        
        if (!presentProduct.toString().equals(product.toString())) {
            repo.save(product);
            return Store.initialize(HttpStatus.ACCEPTED, "The Product Updated successfully....");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Err while Updating product....");
    }

    @Transactional
    public Store<String> updateRating(String id, Star star, String token) {
        String customerId = config.getUsername(token);
        star = star.setCustomerId(customerId);
        Star fiveStars = starRepo.findByCustomerId(customerId);
        Product product = repo.findById(id).orElse(null);

        if (product == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "No product Present....");
        }

        if (fiveStars == null) {
                star.setProduct(product).setCustomerId(customerId);
                starRepo.save(star);

            return Store.initialize(HttpStatus.OK, "rating saved successfully....");
        }

        if (star.getFiveStar() == 1) {
            fiveStars = fiveStars.setFiveStar(fiveStars.getFiveStar() + star.getFiveStar());
        } else if (star.getFourStar() == 1) {
            fiveStars = fiveStars.setFourStar(fiveStars.getFourStar() + star.getFourStar());
        } else if (star.getThreeStar() == 1) {
            fiveStars = fiveStars.setThreeStar(fiveStars.getThreeStar() + star.getThreeStar());
        } else if (star.getTwoStar() == 1) {
            fiveStars = fiveStars.setTwoStar(fiveStars.getTwoStar() + star.getTwoStar());
        } else {
            fiveStars = fiveStars.setOneStar(fiveStars.getOneStar() + star.getOneStar());
        }

        starRepo.save(fiveStars);

        return Store.initialize(HttpStatus.OK, "Rating updated successfully...");
    }

    @Transactional
    public Store<String> updateImage(String id, List<Image> images) {
        Product product = repo.findById(id).orElse(null);

        if (product == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "There is no such product...");
        }

        imageRepo.deleteAllByProduct(product);

        if (!imageRepo.findAllByProduct(product).isEmpty()) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "There is a error while deleting image....");
        }

        images = images.stream().map(image -> image.setProduct(product)).toList();
        imageRepo.saveAll(images);

        return Store.initialize(HttpStatus.ACCEPTED, "The images saved successfully....");
    }

    @Transactional
    public Store<String> updateQuantity(String productId, int quantity) {
        Integer productQuantity = repo.getQuantityFromProduct(productId);

        if (productQuantity < quantity) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "The Stock is empty...");
        }

        productQuantity -= quantity;
        repo.updateQuantityFromProduct(productQuantity, productId);

        return Store.initialize(HttpStatus.ACCEPTED, "quantity has been updated");
    }

    @Transactional
    public Store<List<ProductDTO>> findBySearchKeyword(String searchKeyword) {
        List<Product> products = repo.findAllProductBySearchKeyword(searchKeyword);

        if (products.isEmpty()) {return Store.initialize(HttpStatus.BAD_REQUEST, List.of());}

        return Store.initialize(HttpStatus.OK, products.stream().map(
            this::getProduct).toList()
        );
    }

    @Transactional
    public Store<ProductDTO> findByProductId(String id) {
        Product product = repo.findById(id).orElse(null);

        if (product == null) {return Store.initialize(HttpStatus.BAD_REQUEST, new ProductDTO());}

        return Store.initialize(HttpStatus.OK, getProduct(product));
    }

    @Transactional
    public Store<List<ProductDTO>> findBySellerId(String sellerId) {
        List<Product> products = repo.findAllBySellerId(sellerId);

        if (products.isEmpty()) {return Store.initialize(HttpStatus.BAD_REQUEST, List.of());}

        return Store.initialize(HttpStatus.OK, products.stream().map(this::getProduct).toList());
    }

    @Transactional
    public Store<String> deleteAllProductBySellerId(String sellerId) {
        
        if (repo.findAllBySellerId(sellerId).isEmpty()) {
            return Store.initialize(HttpStatus.OK, "The products are already been deleted....");
        }

        repo.deleteAllBySellerId(sellerId);

        return Store.initialize(HttpStatus.OK, "Deletion successful....");
    }

    @Transactional
    public Store<String> deleteProductById(String id) {

        if (repo.findById(id).orElse(null) == null) {
            return Store.initialize(HttpStatus.OK, "The Product was already been deleted.....");
        }

        repo.deleteById(id);

        return Store.initialize(HttpStatus.OK, "Deletion Is Successful....");
    }
}
