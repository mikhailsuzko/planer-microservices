package com.sma.micro.planner.todo.infrastructure.http.category;

import com.sma.micro.planner.todo.application.use_case.category.FindAllCategoriesUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.http.CommonController;
import com.sma.micro.planner.todo.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("category")
public class FindAllCategoriesController extends CommonController {
    private final FindAllCategoriesUseCase findAllCategoriesUseCase;

    public FindAllCategoriesController(UserDetailsService userDetailsService,
                                       UserValidationService validationService,
                                       FindAllCategoriesUseCase findAllCategoriesUseCase) {
        super(userDetailsService, validationService);
        this.findAllCategoriesUseCase = findAllCategoriesUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryPublicData>> findAll() {
        var userId = getUserId();
        return ResponseEntity.ok(findAllCategoriesUseCase.execute(userId));
    }
}
