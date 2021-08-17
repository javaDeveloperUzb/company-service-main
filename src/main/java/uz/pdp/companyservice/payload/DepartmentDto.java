package uz.pdp.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "The name mustn't be empty")
    private String name;

    @NotNull(message = "The department Id mustn't be empty")
    private Integer companyId;
}
