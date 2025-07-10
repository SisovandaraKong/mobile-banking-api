package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.CustomerResponse;
import istad.co.mobilebankingapi.dto.UpdateCustomer;
import istad.co.mobilebankingapi.mapper.CustomerMapper;
import istad.co.mobilebankingapi.repository.CustomerRepository;
import istad.co.mobilebankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> customerMapper.mapFromCustomerToCustomerResponse(customer))
                .toList();
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        if(!customerRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Customer with email " + email + " does not exist");
        }
        Customer customer = customerRepository.findByEmail(email);
        return customerMapper.mapFromCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        if (customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer's email already exists!"
            );
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Customer's phone number already exists!") ;
        }
        Customer customer = customerMapper.customerRequestToCustomer(createCustomerRequest);
        customer = customerRepository.save(customer);
        return customerMapper.mapFromCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerById(Integer id, UpdateCustomer updateCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " does not exist"));
        customerMapper.updateCustomerFromDto(updateCustomer, customer);
        customer = customerRepository.save(customer);
        return customerMapper.mapFromCustomerToCustomerResponse(customer);
    }

    @Override
    public void deleteCustomerById(Integer id) {
    if(!customerRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Customer with id " + id + " does not exist");
    }
    customerRepository.deleteById(id);
    }
}
