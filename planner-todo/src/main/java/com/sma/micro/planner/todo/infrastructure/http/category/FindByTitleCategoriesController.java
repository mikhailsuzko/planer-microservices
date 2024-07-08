package com.sma.micro.planner.todo.infrastructure.http.category;

import com.sma.micro.planner.todo.application.use_case.category.FindByTitleCategoriesUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.http.CommonController;
import com.sma.micro.planner.todo.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("category")
public class FindByTitleCategoriesController extends CommonController {
    private final FindByTitleCategoriesUseCase findByTitleCategoriesUseCase;

    public FindByTitleCategoriesController(UserDetailsService userDetailsService,
                                           UserValidationService validationService,
                                           FindByTitleCategoriesUseCase findByTitleCategoriesUseCase) {
        super(userDetailsService, validationService);
        this.findByTitleCategoriesUseCase = findByTitleCategoriesUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryPublicData>> search(@RequestBody String title) {
        var userId = getUserId();
        var categories = findByTitleCategoriesUseCase.execute(title, userId);
        return ResponseEntity.ok(categories);
    }

}
