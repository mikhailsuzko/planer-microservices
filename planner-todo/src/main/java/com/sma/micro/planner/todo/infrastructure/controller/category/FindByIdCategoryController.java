package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.sma.micro.planner.todo.application.use_case.category.FindByIdCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("category")
public class FindByIdCategoryController extends CommonController {
    private final FindByIdCategoryUseCase findCategoryUseCase;

    public FindByIdCategoryController(UserDetailsService userDetailsService,
                                      UserValidationService validationService,
                                      FindByIdCategoryUseCase findCategoryUseCase) {
        super(userDetailsService, validationService);
        this.findCategoryUseCase = findCategoryUseCase;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryPublicData> findById(@PathVariable Long id) {
        var userId = getUserId();
        return ResponseEntity.ok(findCategoryUseCase.execute(id, userId));
    }
}
