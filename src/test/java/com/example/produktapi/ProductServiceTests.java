package com.example.produktapi;

import com.example.produktapi.exception.BadRequestException;
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
    void givenExistingCategory_whenGetProductsByCategory_then(){
        //when
       underTest.getProductsByCategory("electronics");
        // then
        verify(repository, times(1)).findByCategory("electronics");
        verifyNoMoreInteractions(repository);
    }
    @Test
    void whenAddingAProduct_thenSaveMethodShouldBeCalled(){
        //given
        Product product = new Product("Barbie",899.99,"","","");
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

    }

}
