package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.sma.micro.planner.todo.application.use_case.category.UpdateCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class UpdateCategoryController extends CommonController {
    private final UpdateCategoryUseCase updateCategoryUseCase;

    public UpdateCategoryController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    UpdateCategoryUseCase updateCategoryUseCase) {
        super(userDetailsService, validationService);
        this.updateCategoryUseCase = updateCategoryUseCase;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoryUpdateData rq) {
        var userId = getUserId();
        updateCategoryUseCase.execute(rq, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
