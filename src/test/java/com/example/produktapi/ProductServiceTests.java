package com.example.produktapi;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import com.example.produktapi.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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

    @Test
    void givenGetAllProducts_thenOneInteractionWithRepositoryMethodFindAll(){
        // when
        underTest.getAllProducts();
        // then
        verify(repository, times(1)).findAll();
    }
    @Test
    void givenGetAllCategories_thenOneInteractionWithRepositoryMethodFindAllCategories(){
        //when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);
    }
    @Test
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
    void whenAddingAProduct_thenSaveMethodShouldBeCalled(){
        //given
        String title = "Barbie";
        Product product = new Product(title,899.99,"","","");
        //when
        underTest.addProduct(product);
        //then
        verify(repository).save(productCaptor.capture());
        assertEquals(product,productCaptor.getValue());
    }

    @Test
    void whenAddingExistingProduct_thenThrowError(){
        String title = "banana";
        Product product = new Product(title, 10.99,"","","");
        //given
        given(repository.findByTitle(title)).willReturn(Optional.of(product));
        //then
      assertThrowsExactly(BadRequestException.class,
                //when
                ()-> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenUpdatingExistingProductByID_thenSaveMethodShouldBeCalled(){
        //given
        String title = "A Updated Product";
        Product product = new Product(title,200.00,"","","");
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        underTest.updateProduct(product,product.getId());
        // then
        verify(repository,times(1)).save(productCaptor.capture());
        verify(repository,times(1)).findById(product.getId());
        assertEquals(product, productCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenUpdatingExistingProductsWithInvalidId_thenExceptionShouldBeThrown(){
        // given
        String title = "A Updated Product";
        Product product = new Product(title,200.00,"","","");
        given(repository.findById(product.getId())).willReturn(Optional.empty());
        //when
        assertThrowsExactly(EntityNotFoundException.class,
        //then
        ()-> underTest.updateProduct(product,product.getId()));
        verify(repository,times(1)).findById(product.getId());
        verify(repository,times(0)).save(any());
        verifyNoMoreInteractions(repository);
    }
    @Test
    void whenDeletingExistingProductByID_thenDeleteMethodShouldBeCalled(){
        //given
        Product product = new Product("A Product that need to be deleted",300.00,"","","");
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        underTest.deleteProduct(product.getId());
        //then
        verify(repository,times(1)).deleteById(idCaptor.capture());
        assertEquals(product.getId(),idCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }
    @Test
    void whenDeletingNonExistingProductByID_thenExceptionShouldBeThrown(){
        // given
        Product product = new Product("A Product that need to be deleted",300.00,"","","");
        given(repository.findById(product.getId())).willReturn(Optional.empty());
        //when
        assertThrowsExactly(EntityNotFoundException.class,
                ()-> underTest.deleteProduct(product.getId()));
        verify(repository,times(1)).findById(product.getId());
        verify(repository,times(0)).deleteById(product.getId());
        verifyNoMoreInteractions(repository);
    }



}
