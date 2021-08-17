package uz.pdp.companyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyservice.entity.Address;
import uz.pdp.companyservice.entity.Company;
import uz.pdp.companyservice.payload.AddressDto;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.CompanyDto;
import uz.pdp.companyservice.service.AddressService;
import uz.pdp.companyservice.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    public HttpEntity<?> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCompany(@PathVariable Integer id) {
        Company company = companyService.getCompany(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public HttpEntity<?> addCompany(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);
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
