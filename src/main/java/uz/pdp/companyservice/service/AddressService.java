package uz.pdp.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyservice.entity.Address;
import uz.pdp.companyservice.payload.AddressDto;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddress(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseGet(Address::new);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByHomeNumberAndStreet(addressDto.getHomeNumber(), addressDto.getStreet());
        if (exists) {
            return new ApiResponse("Bunday manzil avval kiritlgan", false);
        }
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Manzil bazaga qo'shildi", true);
    }

    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday manzil topilmadi", false);
        }
        boolean exists = addressRepository.existsByHomeNumberAndStreetAndIdNot(addressDto.getHomeNumber(), addressDto.getStreet(), id);
        if (exists) {
            return new ApiResponse("Bunday manzil avval ro'yxatdan o'tgan", false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Manzil muvaffaqqiyatli o'zgartirildi", true);
    }

    public ApiResponse deleteAddress(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday manzil topilmadi", false);
        }
        addressRepository.deleteById(id);
        return new ApiResponse("Manzil o'chirildi", true);
    }
}
