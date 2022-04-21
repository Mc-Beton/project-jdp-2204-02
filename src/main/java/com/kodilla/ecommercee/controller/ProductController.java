package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.Exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.DbGroupService;
import com.kodilla.ecommercee.service.DbProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final DbProductService service;
    private final ProductMapper productMapper;
    private final DbGroupService groupService;

    //all
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(productMapper.mapToProductDtoList(products));
    }

    //all
    @GetMapping(value = "{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) throws ProductNotFoundException {
        return ResponseEntity.ok(productMapper.mapToProductDto(service.getProductWithId(productId)));
    }

    //all
    @GetMapping(value = "/group={groupId}")
    public ResponseEntity<List<ProductDto>> getAllProductsFromGroup(@PathVariable Long groupId) {
        List<Product> products = service.getAllProductsByGroup(groupService.getGroup(groupId).get());
        return ResponseEntity.ok(productMapper.mapToProductDtoList(products));
    }

    //admin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addNewProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        service.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    //admin
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        Product updateProduct = service.saveProduct(product);
        return ResponseEntity.ok(productMapper.mapToProductDto(updateProduct));
    }

    //admin
    @DeleteMapping(path = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}