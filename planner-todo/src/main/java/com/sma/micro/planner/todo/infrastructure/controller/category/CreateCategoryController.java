package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.sma.micro.planner.todo.application.use_case.category.CreateCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("category")
public class CreateCategoryController extends CommonController {
    private final CreateCategoryUseCase createCategoryService;

    public CreateCategoryController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    CreateCategoryUseCase createCategoryService) {
        super(userDetailsService, validationService);
        this.createCategoryService = createCategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryPublicData> add(@RequestBody @Valid CategoryRegistrationData rq) {
        var userId = getUserId();
        return ResponseEntity.ok(createCategoryService.execute(rq, userId));
    }
}
