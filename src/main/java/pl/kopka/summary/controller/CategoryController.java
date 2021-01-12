package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addNewCategory(@RequestBody Category newCategory){
        Category category = categoryService.addNewCategory(newCategory);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
