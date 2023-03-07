package com.example.produktapi.repository;


import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTests {
    @Autowired
    private ProductRepository underTest;




    // Find By Title Tests
    @Test
    @DisplayName("Return product by title normal flow")
    void whenSearchingForExistingTitle_thenReturnThatProduct(){
        //given
        Product product =  new Product("En Mac",25000.0,"electronics","sämsta datorn","url to pic");
        underTest.save(product);
        //when
        Optional<Product> optionalProduct = underTest.findByTitle("En Mac");
        //then
        assertTrue(optionalProduct.isPresent());
        assertEquals(product.getTitle(), optionalProduct.get().getTitle());
    }

    @Test
    @DisplayName("Return product by title test wrong flow")
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){
        //given
        String title = "A title that does not exist";
        //when
        Optional<Product> optionalProduct = underTest.findByTitle(title);
        //then
        assertAll(
                ()-> assertFalse(optionalProduct.isPresent()),
                ()-> assertTrue(optionalProduct.isEmpty()),
                ()-> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );
    }
    // Find By Category Tests
    @Test
    @DisplayName("Return products by category test normal flow")
    void whenSearchingForExistingCategory_thenReturnProductsInCategory(){
        //given
        String existingCategory = "electronics";
        Product testProduct = new Product("Test Product",20.00,existingCategory,"","");
        underTest.save(testProduct);
        //when
        List<Product> productList = underTest.findByCategory(existingCategory);
        //then
        assertAll(
                ()->assertFalse(productList.isEmpty()),
                ()->assertTrue(productList.contains(testProduct))
        );
    }
    @Test
    @DisplayName("Return products by category wrong flow")
    void whenSearchingForNonExistingCategory_thenReturnEmptyList(){
        //given
        String nonExistingCategory = "beds";
        //when
        List<Product> productList = underTest.findByCategory(nonExistingCategory);
        //then
        assertAll(
                ()-> assertTrue(productList.isEmpty()),
                ()-> assertEquals(0, productList.size()),
                ()-> assertThrows(IndexOutOfBoundsException.class, ()-> productList.get(0))
        );

    }
    // Find All Categories Tests
    @Test
    @DisplayName("Return all existing categories")
    void whenSearchingForAllExistingCategories_thenReturnCategories(){
        //given
        List<String> categoriesList = underTest.findAllCategories();
        //then
        assertAll(
                ()->assertFalse(categoriesList.isEmpty()),
                ()->assertEquals(4, categoriesList.size())
        );
    }



}
