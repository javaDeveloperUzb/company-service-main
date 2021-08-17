package uz.pdp.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyservice.entity.Company;
import uz.pdp.companyservice.entity.Department;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.DepartmentDto;
import uz.pdp.companyservice.repository.CompanyRepository;
import uz.pdp.companyservice.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElseGet(Department::new);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (exists) {
            return new ApiResponse("A department with such a name and company id already exists", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }

        Department department = new Department();
        department.setName(departmentDto.getName());
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department added", true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }

        boolean exists = departmentRepository.existsByNameAndCompany_IdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (exists) {
            return new ApiResponse("A department with such a name and company id already exists", false);
        }

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department edited", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }
        departmentRepository.deleteById(id);
        return new ApiResponse("Department deleted", true);
    }
}
