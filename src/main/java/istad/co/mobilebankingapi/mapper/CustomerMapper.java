package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper{
    CustomerResponse mapFromCustomerToCustomerResponse(Customer customer);
}
