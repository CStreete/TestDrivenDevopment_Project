package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService underTest;
    @Captor
    ArgumentCaptor<Product> productCaptor;
    @Captor
    ArgumentCaptor<Integer> idCaptor;

    Product product;
    @BeforeEach
    void setUp(){
         product = new Product("A Test Product",300.00,"","","");
    }
    @Test
    @DisplayName("Get all products test")
    void givenGetAllProducts_thenOneInteractionWithRepositoryMethodFindAll(){
        // when
        underTest.getAllProducts();
        // then
        verify(repository, times(1)).findAll();
    }
    @Test
    @DisplayName("Get all categories test normal flow")
    void givenGetAllCategories_thenOneInteractionWithRepositoryMethodFindAllCategories(){
        //when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);
    }
    @Test
    @DisplayName("Get products by category test normal flow")
    void givenExistingCategory_whenGetProductsByCategory_thenFindByCategoryShouldBeCalled(){
        //given
        String category = "electronics";
        //when
        underTest.getProductsByCategory(category);
        // then
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);
    }
    @Test
    @DisplayName("Get products by category test wrong flow")
    void givenNonExistingCategory_whenGetProductsByCategory_thenExceptionShouldBeThrown(){
        //No exception logic in method
    }
    @Test
    @DisplayName("Get product by id test normal flow")
    void givenExistingProduct_whenGetProductsByID_thenReturnOptionalProduct(){
        //given
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        Product product1 = underTest.getProductById(any());
        //then
        verify(repository,times(1)).findById(any());
        verifyNoMoreInteractions(repository);
        assertEquals(product1,product);

    }
    @Test
    @DisplayName("Get product by id test wrong flow")
    void whenGetProductsByIncorrectID_thenThrowException(){
        //given
        given(repository.findById(product.getId())).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrowsExactly(EntityNotFoundException.class,
                //when
                ()->underTest.getProductById(product.getId()));
        //then
        assertEquals("Produkt med id " + product.getId() + " hittades inte", exception.getMessage());
    }
    @Test
    @DisplayName("Add product test normal flow")
    void whenAddingAProduct_thenSaveMethodShouldBeCalled(){
        //given

        //when
        underTest.addProduct(product);
        //then
        verify(repository,times(1)).findByTitle(product.getTitle());
        verify(repository,times(1)).save(productCaptor.capture());
        assertEquals(product,productCaptor.getValue());
    }
    @Test
    @DisplayName("Add products test wrong flow")
    void whenAddingExistingProductWithSameTitle_thenThrowException(){
        String title = product.getTitle();
        //given
        given(repository.findByTitle(title)).willReturn(Optional.of(product));
        //then
        BadRequestException exception = assertThrowsExactly(BadRequestException.class,
                //when
                ()-> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
        assertEquals("En produkt med titeln: " + title + " finns redan",exception.getMessage());

    }
    @Test
    @DisplayName("Update product test normal flow")
    void whenUpdatingExistingProductByID_thenSaveMethodShouldBeCalled(){
        //given
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        underTest.updateProduct(product,product.getId());
        // then
        verify(repository,times(1)).findById(product.getId());
        verify(repository,times(1)).save(productCaptor.capture());
        assertEquals(product, productCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }
    @Test
    @DisplayName("Update product test wrong flow")
    void whenUpdatingExistingProductsWithInvalidId_thenExceptionShouldBeThrown(){
        // given
        given(repository.findById(product.getId())).willReturn(Optional.empty());
        //when

        EntityNotFoundException exception = assertThrowsExactly(EntityNotFoundException.class,
        //then
        ()-> underTest.updateProduct(product,product.getId()));
        verify(repository,times(1)).findById(product.getId());
        verify(repository,times(0)).save(any());
        verifyNoMoreInteractions(repository);
        assertEquals("Produkt med id "+ product.getId()+ " hittades inte", exception.getMessage());
    }
    @Test
    @DisplayName("Deleting product test normal flow")
    void whenDeletingExistingProductByID_thenDeleteMethodShouldBeCalled(){
        //given
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        underTest.deleteProduct(product.getId());
        //then
        verify(repository,times(1)).findById(idCaptor.capture());
        verify(repository,times(1)).deleteById(idCaptor.capture());
        assertEquals(product.getId(),idCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }
    @Test
    @DisplayName("Deleting product test wrong flow")
    void whenDeletingNonExistingProductByID_thenExceptionShouldBeThrown(){
        // given
        given(repository.findById(product.getId())).willReturn(Optional.empty());
        //when
        EntityNotFoundException exception = assertThrowsExactly(EntityNotFoundException.class,
                ()-> underTest.deleteProduct(product.getId()));
        verify(repository,times(1)).findById(product.getId());
        verify(repository,times(0)).deleteById(product.getId());
        verifyNoMoreInteractions(repository);
        assertEquals("Produkt med id "+ product.getId()+ " hittades inte",exception.getMessage());
    }



}
