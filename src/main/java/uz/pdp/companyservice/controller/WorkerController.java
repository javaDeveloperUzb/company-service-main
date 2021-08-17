package uz.pdp.companyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyservice.entity.Worker;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.WorkerDto;
import uz.pdp.companyservice.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getWorkers() {
        List<Worker> getWorkers = workerService.getWorkers();
        return ResponseEntity.ok(getWorkers);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getWorker(@PathVariable Integer id) {
        Worker worker = workerService.getWorker(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping
    public HttpEntity<?> addWorker(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorker(id);
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

