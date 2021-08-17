package uz.pdp.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    @NotNull(message = "The name mustn't be empty")
    private String name;

    @NotNull(message = "The phone number  mustn't be empty")
    private String phoneNumber;

    @NotNull(message = "The street mustn't be empty")
    private String street;

    @NotNull(message = "The homeNumber mustn't be empty")
    private String homeNumber;

    @NotNull(message = "The department Id mustn't be empty")
    private Integer departmentId;
}
