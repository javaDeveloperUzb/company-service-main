package uz.pdp.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyservice.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByHomeNumberAndStreet(String homeNumber, String street);
    boolean existsByHomeNumberAndStreetAndIdNot(String homeNumber, String street, Integer id);

}
