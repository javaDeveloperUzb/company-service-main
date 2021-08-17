package uz.pdp.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {
    @NotNull(message = "The street mustn't be empty")
    private String street;

    @NotNull(message = "The homeNumber mustn't be empty")
    private String homeNumber;
}
