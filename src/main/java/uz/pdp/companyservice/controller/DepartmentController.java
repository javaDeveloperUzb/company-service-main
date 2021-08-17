package uz.pdp.companyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyservice.entity.Department;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.DepartmentDto;
import uz.pdp.companyservice.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public HttpEntity<?> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getDepartment(@PathVariable Integer id) {
        Department department = departmentService.getDepartment(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public HttpEntity<?> addDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDepartment(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    //DTODAGI NOTNULL ICHIDAGI MESSAGENI QAYTARADI
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

