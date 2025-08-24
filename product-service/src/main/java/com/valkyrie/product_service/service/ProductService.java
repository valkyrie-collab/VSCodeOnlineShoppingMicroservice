package com.valkyrie.product_service.service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.product_service.config.TokenConfig;
import com.valkyrie.product_service.model.Image;
import com.valkyrie.product_service.model.ImageDTO;
import com.valkyrie.product_service.model.Product;
import com.valkyrie.product_service.model.ProductDTO;
import com.valkyrie.product_service.model.Store;
import com.valkyrie.product_service.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository repo;
    @Autowired
    private void setRepo(ProductRepository repo) {this.repo = repo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private ProductDTO getProduct(Product product) {
        List<Image> images = product.getImages();
        List<ImageDTO> imagesDTO = new LinkedList<>();
        
        for (Image image : images) {
            imagesDTO.add(
                new ImageDTO().setId(image.getId()).setName(image.getName())
                            .setType(image.getType()).setData(image.getData())
            );
        }

        double discountedPrice = product.getPrice() - ((product.getDiscount() / 100.0) * product.getPrice());

        double rating = (5 * product.getFiveStar() + 
                        4 * product.getFourStar() + 
                        3 * product.getThreeStar() + 
                        2 * product.getTwoStar() +
                        1 * product.getOneStar()) / 
                        (product.getFiveStar() +
                        product.getFourStar() +
                        product.getThreeStar() + 
                        product.getTwoStar() +
                        product.getOneStar());

        return new ProductDTO().setBrand(product.getBrand()).setCategory(product.getCategory())
                            .setColor(product.getColor()).setDescription(product.getDescription())
                            .setDiscount(product.getDiscount()).setDiscountedPrice(discountedPrice)
                            .setId(product.getId()).setImages(imagesDTO).setName(product.getName())
                            .setPrice(product.getPrice()).setQuantity(product.getQuantity()).setRating(rating);

    }

    public Store<String> save(String token, Product product) {
        String uuid = UUID.randomUUID().toString();
        String username = config.getUsername(token);
        repo.save(product.setId(uuid).setSellerId(username));

        return Store.initialize(HttpStatus.ACCEPTED, "The Product saved successfully.....");
    }

    @Transactional
    public Store<String> update(String token, Product product) {
        String username = config.getUsername(token);
        product = product.setSellerId(username);
        Product presentProduct = repo.findById(product.getId()).orElse(product);

        if (product.getImages().isEmpty() || product.getImages() == null) {
            List<Image> images = presentProduct.getImages();

            try {
                product = product.setImages(images);
            } catch (Exception e) {
                Store.initialize(HttpStatus.BAD_REQUEST, "Err while inserting image....");
            }

        }
        
        if (!presentProduct.toString().equals(product.toString())) {
            repo.save(product);
            Store.initialize(HttpStatus.ACCEPTED, "The Product Updated successfully....");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Err while Updating product....");
    }

    @Transactional
    public Store<String> updateRating(String id, boolean oneStar, boolean twoStar, 
                                    boolean threeStar, boolean fourStar, boolean fiveStar) {
        Integer[] fiveStars = repo.findStarsFromProduct(id);

        if (fiveStars == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "There is an issue with rating....");
        }

        if (fiveStar) {
            fiveStars[4] += 1;
        } else if (fourStar) {
            fiveStars[3] += 1;
        } else if (threeStar) {
            fiveStars[2] += 1;
        } else if (twoStar) {
            fiveStars[1] += 1;
        } else if (oneStar) {
            fiveStars[0] += 1;
        } else {
            return Store.initialize(HttpStatus.BAD_REQUEST, "The star initialization is not ok...");
        }

        repo.updateStarRatings(id, fiveStars[0], fiveStars[1], fiveStars[2], fiveStars[3], fiveStars[4]);
            
        return null;
    }

    @Transactional
    public Store<String> updateImage(String id, List<Image> images) {
        repo.deleteAllImageById(id);
        
        if (!repo.findAllImageById(id).isEmpty()) {
            Store.initialize(HttpStatus.BAD_REQUEST, "There is a error while deleting image....");
        }

        Product product = repo.findById(id).orElse(null);
        repo.save(product.setImages(images));

        return Store.initialize(HttpStatus.ACCEPTED, "The images saved successfully....");
    }

    @Transactional
    public Store<List<ProductDTO>> findBySearchKeyword(String searchKeyword) {
        List<Product> products = repo.findAllProductBySearchKeyword(searchKeyword);

        if (products.isEmpty()) {return Store.initialize(HttpStatus.BAD_REQUEST, List.of());}

        return Store.initialize(HttpStatus.OK, products.stream().map(
            product -> getProduct(product)).toList()
        );
    }

    @Transactional
    public Store<List<ProductDTO>> findBySellerId(String sellerId) {
        List<Product> products = repo.findAllBySellerId(sellerId);

        if (products.isEmpty()) {return Store.initialize(HttpStatus.BAD_REQUEST, List.of());}

        return Store.initialize(HttpStatus.OK, products.stream().map(product -> getProduct(product)).toList());
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
