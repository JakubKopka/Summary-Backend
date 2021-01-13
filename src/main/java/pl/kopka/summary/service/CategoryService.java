package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.CategoryRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    BillingRepo billingRepo;

    @Autowired
    UserService userService;

    public Category addNewCategory(Category category){
        category.setCategoryId(RandomStringUtils.randomNumeric(20));
        category = categoryRepo.save(category);
        Billing billing = userService.getCurrentLoginUser().getBilling();
        billing.addCategory(category);
        billingRepo.save(billing);
        return category;
    }

    public List<Category> getAll() {
        return userService.getCurrentLoginUser().getBilling().getCategories();
    }

    @Transactional
    public void delete(String categoryId) {
        categoryRepo.deleteCategoryByCategoryId(categoryId);
    }
}
