package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.constant.MonthsAndCategoriesConst;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.exception.exceptions.CategoryExistException;
import pl.kopka.summary.exception.exceptions.CategoryNotExistsException;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.CategoryRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final BillingRepo billingRepo;
    private final UserService userService;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo, BillingRepo billingRepo, UserService userService) {
        this.categoryRepo = categoryRepo;
        this.billingRepo = billingRepo;
        this.userService = userService;
    }

    public List<Category> addNewCategory(Category category) throws CategoryExistException {
        Category finalCategory = category;
        if(getAllUserCategories().stream().filter(c-> c.getName().equals(finalCategory.getName())).collect(Collectors.toList()).stream().findFirst().isPresent()){
            throw new CategoryExistException(MonthsAndCategoriesConst.CATEGORY_EXIST);
        }
        category.setCategoryId(RandomStringUtils.randomNumeric(20));
        category = categoryRepo.save(category);
        Billing billing = userService.getCurrentLoginUser().getBilling();
        billing.addCategory(category);
        billingRepo.save(billing);
        return getAllUserCategories();
    }

    public List<Category> getAllUserCategories() {
        List<Category> categories = userService.getCurrentLoginUser().getBilling().getCategories();
        categories.forEach(obj -> obj.setTotal(obj.getOperationList().stream().mapToDouble(Operation::getAmount).sum()));
        return categories;
    }

    @Transactional
    public void delete(String categoryId) {
        categoryRepo.deleteCategoryByCategoryId(categoryId);
    }

    public List<Category> updateCategory(String categoryId, Category category) throws CategoryNotExistsException, CategoryExistException {
        Category categoryDb = categoryRepo.findCategoryByCategoryId(categoryId);
        if(categoryDb == null){
            throw new CategoryNotExistsException(MonthsAndCategoriesConst.CATEGORY_NOT_EXIST);
        }
        if(categoryRepo.findCategoryByName(category.getName()) != null){
            throw new CategoryExistException(MonthsAndCategoriesConst.CATEGORY_EXIST);
        }
        categoryDb.setDescription(category.getDescription());
        categoryDb.setName(category.getName());
        categoryRepo.save(categoryDb);
        return getAllUserCategories();
    }
}
