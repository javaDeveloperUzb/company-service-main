package uz.pdp.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyservice.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByCorpName(String corpName);

    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}
