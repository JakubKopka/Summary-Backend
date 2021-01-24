package pl.kopka.summary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.exception.exceptions.CategoryExistException;
import pl.kopka.summary.exception.exceptions.CategoryNotExistsException;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.CategoryRepo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepo categoryRepo;
    @Mock private BillingRepo billingRepo;
    private UserService userService;

    @InjectMocks private CategoryService categoryService;

    private List<Category> userCategories;

    @BeforeEach
    private void init(){
        userService = mock(UserService.class, Mockito.RETURNS_DEEP_STUBS);
        categoryRepo = mock(CategoryRepo.class, Mockito.RETURNS_DEEP_STUBS);

        MockitoAnnotations.openMocks(this);

        userCategories = new ArrayList<>();
        Category cat = new Category();
        cat.setCategoryId("1234567890");
        cat.setName("Cat");
        cat.setDescription("Test Description");
        userCategories.add(cat);
    }


    @Test
    void shouldAddNewCategory() throws CategoryExistException {
        //given
        Category newCat = new Category();
        newCat.setName("New Category");
        newCat.setDescription("New Category Description");

        when(userService.getCurrentLoginUser().getBilling().getCategories()).thenReturn(userCategories);
        doAnswer(invocation -> {
            userCategories.add(newCat);
            return null;
        }).when(categoryRepo).save(any(Category.class));
        //when
        List<Category> categories = categoryService.addNewCategory(newCat);
        //then
        Assertions.assertNotNull(categories);
        Assertions.assertEquals(userCategories.size(), categories.size());
    }

    @Test
    void shouldNotAddNewCategory(){
        //given
        when(userService.getCurrentLoginUser().getBilling().getCategories()).thenReturn(userCategories);
        Category newCat = new Category();
        newCat.setName("Cat");
        newCat.setDescription("New Category Description");
        //when
        //then
        Assertions.assertThrows(CategoryExistException.class, ()->{
            categoryService.addNewCategory(newCat);
        });
    }

    @Test
    void shouldReturnAllUserCategories() {
        //given
        Category newCat = new Category();
        newCat.setName("Cat");
        newCat.setDescription("New Category Description");

        when(userService.getCurrentLoginUser().getBilling().getCategories()).thenReturn(userCategories);
        doAnswer(invocation -> {
            userCategories.add(newCat);
            return null;
        }).when(categoryRepo).save(any(Category.class));

        //when
        List<Category> categories = categoryService.getAllUserCategories();
        //then
        Assertions.assertNotNull(categories);
        Assertions.assertEquals(userCategories.size(), categories.size());
    }

    @Test
    void shouldUpdateCategory() throws CategoryNotExistsException, CategoryExistException {
        //given
        String newCategoryId = "1234567890";

        Category updateCategory = new Category();
        updateCategory.setCategoryId(newCategoryId);
        updateCategory.setName("Category");
        updateCategory.setDescription("Description");

        Category dbCategory = new Category();
        dbCategory.setName("Db Category");
        dbCategory.setDescription("DB Category Description");

        when(categoryRepo.findCategoryByCategoryId(any(String.class))).thenReturn(dbCategory);
        when(categoryRepo.findCategoryByName(any(String.class))).thenReturn(null);
        when(userService.getCurrentLoginUser().getBilling().getCategories()).thenReturn(userCategories);
        when(categoryRepo.save(any(Category.class))).then(i -> {
            userCategories.add(updateCategory);
            return updateCategory;
        });

        //when
        List<Category> categories = categoryService.updateCategory(newCategoryId, updateCategory);

        //then
        Assertions.assertNotNull(categories);
        Assertions.assertEquals(userCategories.size(), categories.size());
    }

    @Test
    void shouldNotUpdateCategoryThisCategoryAlreadyExistsException(){
        //given
        String categoryId = "1234567890";
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName("Cat");
        category.setDescription("Description");
        //when
        //then
        Assertions.assertThrows(CategoryExistException.class, ()->{
            categoryService.updateCategory(categoryId, category);
        });
    }

    @Test
    void shouldNotUpdateCategoryCategoryNotExistsException(){
        //given
        when(categoryRepo.findCategoryByCategoryId(any(String.class))).thenReturn(null);

        String categoryId = "0000000000";
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName("Cat");
        category.setDescription("Description");
        //when
        //then
        Assertions.assertThrows(CategoryNotExistsException.class, ()->{
            categoryService.updateCategory(categoryId, category);
        });
    }
}