package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.domain.KYC;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import istad.co.mobilebankingapi.dto.kyc.KycResponse;
import istad.co.mobilebankingapi.mapper.CustomerMapper;
import istad.co.mobilebankingapi.mapper.KycMapper;
import istad.co.mobilebankingapi.repository.CustomerRepository;
import istad.co.mobilebankingapi.repository.KYCRepository;
import istad.co.mobilebankingapi.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {
    private final CustomerRepository customerRepository;
    private final KYCRepository kycRepository;
    private final KycMapper kycMapper;


    @Override
    public KycResponse getKycByCustomerPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Phone Number Not Found")
        );
        KYC kyc = kycRepository.findByCustomer(customer).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found")
        );
        return kycMapper.toKycResponse(kyc);
    }

    @Override
    public void verifyCustomerByCustomerNationalCardId(String nationalCardId) {
        KYC kyc = kycRepository.findByNationalCardId(nationalCardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"National Card Not Found")
        );
        kyc.setIsVerified(true);
        kycRepository.save(kyc);
    }
}
