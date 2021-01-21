package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.exception.exceptions.CategoryExistException;
import pl.kopka.summary.exception.exceptions.CategoryNotExistsException;
import pl.kopka.summary.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Category>> addNewCategory(@RequestBody Category newCategory) throws CategoryExistException {
        return new ResponseEntity<>(categoryService.addNewCategory(newCategory), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll(){
        return new ResponseEntity<>(categoryService.getAllUserCategories(), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<List<Category>> updateCategory(@PathVariable String categoryId, @RequestBody Category category) throws CategoryNotExistsException, CategoryExistException {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
    }
}
