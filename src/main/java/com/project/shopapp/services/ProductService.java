package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Data not found with category id + " + productDTO.getCategoryId()));

        Product newProduct = Product
                .builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .url(productDTO.getUrl())
                .category(category)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws DataNotFoundException {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found with product id: " + id));
        return product;
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(product -> {
            ProductResponse productResponse = ProductResponse
                    .builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .url(product.getUrl())
                    .categoryId(product.getCategory().getId())
                    .build();
            productResponse.setUpdatedAt(product.getUpdatedAt());
            productResponse.setCreatedAt(product.getCreatedAt());
            return productResponse;
        });
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Data not found with category id + " + productDTO.getCategoryId()));

        Product product = getProductById(id);
        if(product != null) {
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setUrl(productDTO.getUrl());
            product.setCategory(category);

            return productRepository.save(product);
        }
        else {
            throw new DataNotFoundException("Data not found with category id + " + productDTO.getCategoryId());
        }
    }

    @Override
    public void deleteProduct(long id) {
//        productRepository.deleteById(id);
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsProductByName(String name) {
//        Product product = productRepository
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Data not found with product id: " + productImageDTO.getProductId()));


        ProductImage productImage = ProductImage
                .builder()
                .imageUrl(productImageDTO.getImageUrl())
                .product(product)
                .build();


        return productImageRepository.save(productImage);
    }

}
