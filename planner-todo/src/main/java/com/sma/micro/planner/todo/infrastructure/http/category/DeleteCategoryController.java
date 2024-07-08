package com.sma.micro.planner.todo.infrastructure.http.category;

import com.sma.micro.planner.todo.application.use_case.category.DeleteCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.http.CommonController;
import com.sma.micro.planner.todo.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("category")
public class DeleteCategoryController extends CommonController {
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public DeleteCategoryController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    DeleteCategoryUseCase deleteCategoryUseCase) {
        super(userDetailsService, validationService);
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var userId = getUserId();
        deleteCategoryUseCase.execute(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
