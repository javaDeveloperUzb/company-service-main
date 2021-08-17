package uz.pdp.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "The name mustn't be empty")
    private String corpName;

    @NotNull(message = "The name mustn't be empty")
    private String directorName;

    @NotNull(message = "The street mustn't be empty")
    private String street;

    @NotNull(message = "The homeNumber mustn't be empty")
    private String homeNumber;
}
