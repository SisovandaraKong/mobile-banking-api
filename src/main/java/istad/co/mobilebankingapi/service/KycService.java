package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.domain.KYC;
import istad.co.mobilebankingapi.dto.kyc.KycResponse;
import istad.co.mobilebankingapi.repository.KYCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KycService {
    KycResponse getKycByCustomerPhoneNumber(String phoneNumber);
    void verifyCustomerByCustomerNationalCardId(String nationalCardId);
}
