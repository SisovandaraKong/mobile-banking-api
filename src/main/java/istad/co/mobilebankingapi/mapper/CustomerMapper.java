package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.CustomerResponse;
import istad.co.mobilebankingapi.dto.UpdateCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper{
    CustomerResponse mapFromCustomerToCustomerResponse(Customer customer);
    Customer customerRequestToCustomer(CreateCustomerRequest createCustomerRequest);
    //update exist object and in same place not new
    void updateCustomerFromDto(UpdateCustomer updateCustomer, @MappingTarget Customer customer);

}
