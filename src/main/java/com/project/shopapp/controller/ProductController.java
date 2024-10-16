package com.project.shopapp.controller;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.status(200).body(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.status(200).body(product);
        }
        catch (Exception err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage(), err);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult
    ){
        try {
            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
            }
            Product product = productService.createProduct(productDTO);
            return ResponseEntity.status(200).body(product);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            Product newProduct = productService.getProductById(productId);
            files = (files == null) ? new ArrayList<MultipartFile>() : files;
            ArrayList<ProductImage> productImages = new ArrayList<ProductImage>();
            for(MultipartFile file : files){
                if(file != null && file.getSize() > 0) {
                    if(file.getSize() > 10 * 1024 * 1024){
                        throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large!");
                    }
                    String contentType = file.getContentType();
                    if(contentType == null || !contentType.startsWith("image")){
                        return ResponseEntity.badRequest().body("File is not an image!");
                    }
                    String fileName = storeFile(file);
                    ProductImage productImage = productService.createProductImage(productId,
                            ProductImageDTO
                                    .builder()
                                    .productId(productId)
                                    .imageUrl(fileName)
                                    .build());
                    productImages.add(productImage);
                }
            }
            return ResponseEntity.status(200).body("OK");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productDTO.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(200).body("Deleted product with id " + id);
    }

    @PostMapping("/fakedata")
    public void fakedata() {
        Faker faker = new Faker();
        for(int i = 0; i < 100; i++) {
            ProductDTO productDTO = ProductDTO
                    .builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence())
                    .price(Float.parseFloat(faker.commerce().price()))
                    .categoryId((long)faker.number().numberBetween(1,4))
                    .url("/demo")
                    .build();
            System.out.println(productDTO);
            try {
                productService.createProduct(productDTO);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }
}
