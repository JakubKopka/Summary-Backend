package pl.kopka.summary.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.exception.exceptions.CategoryExistException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Mock
    private CategoryService categoryService;

    @Test
    void shouldAddNewCategory(){
        //given
        List<Category> userCategories = new ArrayList<>();
        Category cat = new Category();
        cat.setDescription("Test Description");
        cat.setName("Cat");
        userCategories.add(cat);
        //when
        when(categoryService.getAllUserCategories()).thenReturn(userCategories);
        //then
        try {
            System.out.println(categoryService.addNewCategory(cat));
        } catch (CategoryExistException e) {
            System.out.println("blad");
        }
    }

    @Test
    void getAllUserCategories() {
    }

    @Test
    void updateCategory() {
    }
}