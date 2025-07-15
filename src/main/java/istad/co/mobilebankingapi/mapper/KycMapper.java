package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.KYC;
import istad.co.mobilebankingapi.dto.kyc.KycResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {
        CustomerMapper.class,
})
public interface KycMapper {
    @Mapping(source = "customer", target = "customer")
    KycResponse toKycResponse(KYC kyc);
}
