package istad.co.mobilebankingapi.controller;

import istad.co.mobilebankingapi.dto.customer.CreateCustomerRequest;
import istad.co.mobilebankingapi.dto.customer.UpdateCustomer;
import istad.co.mobilebankingapi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(Map.of(
                "customers", customerService.getAllCustomers()), HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        return new ResponseEntity<>(customerService.getCustomerByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer (@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        return new ResponseEntity<>(customerService.createCustomer(createCustomerRequest)
        , HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateCustomerById (@PathVariable String uuid, @Valid @RequestBody UpdateCustomer updateCustomerRequest) {
        return new ResponseEntity<>(customerService.updateCustomerById(uuid, updateCustomerRequest), HttpStatus.OK);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteCustomerByUuid (@PathVariable String uuid) {
        customerService.deleteCustomerByUuid(uuid);
        return new ResponseEntity<>(Map.of(
                "message", "Customer deleted successfully"
        ),HttpStatus.OK);
    }

    @DeleteMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> deleteCustomerByPhoneNumber (@PathVariable String phoneNumber) {
        customerService.deleteCustomerByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(Map.of(
                "message", "Customer deleted successfully"
        ),HttpStatus.OK);
    }
}
