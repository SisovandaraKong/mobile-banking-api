package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.dto.account.AccountUpdate;
import istad.co.mobilebankingapi.dto.account.Withdraw;
import istad.co.mobilebankingapi.dto.customer.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import istad.co.mobilebankingapi.dto.customer.UpdateCustomer;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
    CustomerResponse updateCustomerById(String uuid, UpdateCustomer updateCustomer);
    void deleteCustomerById(Integer id);
    void deleteCustomerByUuid(String uuid);
    void deleteCustomerByPhoneNumber(String phoneNumber);
}
