package uz.pdp.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyservice.entity.Address;
import uz.pdp.companyservice.entity.Company;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.CompanyDto;
import uz.pdp.companyservice.repository.AddressRepository;
import uz.pdp.companyservice.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElseGet(Company::new);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists) {
            return new ApiResponse("A company with such a name already exists", false);
        }

        boolean existsAddress = addressRepository.existsByHomeNumberAndStreet(companyDto.getHomeNumber(), companyDto.getStreet());
        if (existsAddress) {
            return new ApiResponse("A address with such a street and home number already exists", false);
        }

        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(saveAddress);
        companyRepository.save(company);
        return new ApiResponse("Company added", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }

        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (exists) {
            return new ApiResponse("A company with such a name already exists", false);
        }

        Company company = optionalCompany.get();
        boolean existsAddress = addressRepository.existsByHomeNumberAndStreetAndIdNot(companyDto.getHomeNumber(), companyDto.getStreet(), company.getAddress().getId());
        if (existsAddress) {
            return new ApiResponse("A address with such a street and home number already exists", false);
        }

        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(saveAddress);
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }
        companyRepository.deleteById(id);
        return new ApiResponse("Company deleted", true);
    }
}
