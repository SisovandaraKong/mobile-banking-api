package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.domain.KYC;
import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.dto.customer.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import istad.co.mobilebankingapi.dto.customer.UpdateCustomer;
import istad.co.mobilebankingapi.mapper.CustomerMapper;
import istad.co.mobilebankingapi.repository.CustomerRepository;
import istad.co.mobilebankingapi.repository.KYCRepository;
import istad.co.mobilebankingapi.repository.SegmentRepository;
import istad.co.mobilebankingapi.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SegmentRepository segmentRepository;
    private final KYCRepository kycRepository;

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> customerMapper.mapFromCustomerToCustomerResponse(customer))
                .toList();
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer's email not found"));
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
        Customer customer = new Customer();
        customer.setEmail(createCustomerRequest.email());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setGender(createCustomerRequest.gender());
        customer.setRemark(createCustomerRequest.remark());
        customer.setFullName(createCustomerRequest.fullName());
        Segment segment = segmentRepository.findBySegment(createCustomerRequest.segment()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));
        customer.setSegment(segment);
        KYC kyc = new KYC();
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setIsVerified(false);
        kyc.setCustomer(customer);
        customer.setKyc(kyc);
        customer = customerRepository.save(customer);
        return customerMapper.mapFromCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerById(String uuid, UpdateCustomer updateCustomer) {
        Customer customer = customerRepository.findByUuid(uuid)
                .orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with uuid " + uuid + " does not exist"));
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

    @Override
    @Transactional
    public void deleteCustomerByUuid(String uuid) {
        if (!customerRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Customer with uuid " + uuid + " does not exist");
        }
        customerRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteCustomerByPhoneNumber(String phoneNumber) {
        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with phone number "+ phoneNumber + "does not exist");
        }
        customerRepository.deleteByPhoneNumber(phoneNumber);
    }

}
