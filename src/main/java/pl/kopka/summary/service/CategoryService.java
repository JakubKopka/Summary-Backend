package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.CategoryRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    BillingRepo billingRepo;

    @Autowired
    UserService userService;

    public List<Category> addNewCategory(Category category){
        category.setCategoryId(RandomStringUtils.randomNumeric(20));
        category = categoryRepo.save(category);
        Billing billing = userService.getCurrentLoginUser().getBilling();
        billing.addCategory(category);
        billingRepo.save(billing);
        return getAll();
    }

    public List<Category> getAll() {
        List<Category> categories = userService.getCurrentLoginUser().getBilling().getCategories();
        categories.forEach(obj -> obj.setTotal(obj.getOperationList().stream().mapToDouble(Operation::getAmount).sum()));
        return categories;
    }

    @Transactional
    public void delete(String categoryId) {
        categoryRepo.deleteCategoryByCategoryId(categoryId);
    }
}
