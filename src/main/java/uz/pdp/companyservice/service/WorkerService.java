package uz.pdp.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyservice.entity.Address;
import uz.pdp.companyservice.entity.Department;
import uz.pdp.companyservice.entity.Worker;
import uz.pdp.companyservice.payload.ApiResponse;
import uz.pdp.companyservice.payload.WorkerDto;
import uz.pdp.companyservice.repository.AddressRepository;
import uz.pdp.companyservice.repository.DepartmentRepository;
import uz.pdp.companyservice.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorker(Integer id) {
        Optional<Worker> byId = workerRepository.findById(id);
        return byId.orElseGet(Worker::new);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }

        boolean existsByHomeNumberAndStreet = addressRepository.existsByHomeNumberAndStreet(workerDto.getHomeNumber(), workerDto.getStreet());
        if (existsByHomeNumberAndStreet) {
            return new ApiResponse("A address with such a street and home number already exists", false);
        }

        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("An Worker with such a phone number already exists", false);
        }

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        worker.setAddress(saveAddress);
        Department department = optionalDepartment.get();
        worker.setDepartment(department);

        workerRepository.save(worker);
        return new ApiResponse("Worker added", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Worker not found", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }

        Worker saveWorker = optionalWorker.get();
        Integer addressId = saveWorker.getAddress().getId();

        boolean existsByHomeNumberAndStreet = addressRepository.existsByHomeNumberAndStreetAndIdNot(workerDto.getHomeNumber(), workerDto.getStreet(),  addressId);
        if (existsByHomeNumberAndStreet) {
            return new ApiResponse("A address with such a street and home number already exists", false);
        }

        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumber) {
            return new ApiResponse("An Worker with such a phone number already exists", false);
        }

        saveWorker.setName(workerDto.getName());
        saveWorker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address = saveWorker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address saveAddress = addressRepository.save(address);

        saveWorker.setAddress(saveAddress);
        Department department = optionalDepartment.get();
        saveWorker.setDepartment(department);

        workerRepository.save(saveWorker);
        return new ApiResponse("Worker added", true);
    }

    public ApiResponse deleteWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Worker not found", false);
        }
        workerRepository.deleteById(id);
        return new ApiResponse("Worker deleted", true);
    }
}
