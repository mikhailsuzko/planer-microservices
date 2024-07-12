package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.sma.micro.planner.todo.application.use_case.category.FindAllCategoriesUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
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
