package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.dto.customer.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import istad.co.mobilebankingapi.dto.customer.UpdateCustomer;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {
        KycMapper.class, Segment.class
})
public interface CustomerMapper{
    @Mapping(source = "segment.segment", target = "segment")
    CustomerResponse mapFromCustomerToCustomerResponse(Customer customer);
//    Customer customerRequestToCustomer(CreateCustomerRequest createCustomerRequest);
    //update exist object and in same place not new
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(UpdateCustomer updateCustomer, @MappingTarget Customer customer);

}
