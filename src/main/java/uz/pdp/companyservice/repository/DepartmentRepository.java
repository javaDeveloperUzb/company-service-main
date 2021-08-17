package uz.pdp.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyservice.entity.Company;
import uz.pdp.companyservice.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);

    boolean existsByNameAndCompany_IdAndIdNot(String name, Integer company_id, Integer id);
}
