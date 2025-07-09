package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.CustomerResponse;
import istad.co.mobilebankingapi.dto.UpdateCustomer;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
    CustomerResponse updateCustomerById(Integer id, UpdateCustomer updateCustomer);
    void deleteCustomerById(Integer id);
}
