package uz.pdp.companyservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;

    private boolean success;

    private Object object;

    public ApiResponse() {
    }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
