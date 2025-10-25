package hieunv.dev.loans.service.impl;

import hieunv.dev.commonlib.dto.MobileNumberUpdate;
import hieunv.dev.loans.constants.LoansConstants;
import hieunv.dev.loans.dto.LoansDto;
import hieunv.dev.loans.entity.Loans;
import hieunv.dev.loans.exception.LoanAlreadyExistsException;
import hieunv.dev.loans.exception.ResourceNotFoundException;
import hieunv.dev.loans.mapper.LoansMapper;
import hieunv.dev.loans.repository.LoansRepository;
import hieunv.dev.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;
    private final Random random = new Random();
    private final StreamBridge streamBridge;

    private Long generateLoanNumber() {
        Long loanNumber;
        do {
            // Generate a 10-digit loan number
            loanNumber = 1000000000L + random.nextLong(9000000000L);
        } while (loansRepository.existsById(loanNumber));
        return loanNumber;
    }

    @Override
    public LoansDto createLoan(Loans loan) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumberAndActiveSw(loan.getMobileNumber(),
                LoansConstants.ACTIVE_SW);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + loan.getMobileNumber());
        }
        
        // Generate unique loan number if not provided
        if (loan.getLoanNumber() == null) {
            loan.setLoanNumber(generateLoanNumber());
        }
        
        loan.setActiveSw(LoansConstants.ACTIVE_SW);
        Loans savedLoan = loansRepository.save(loan);
        return LoansMapper.mapToLoansDto(savedLoan, new LoansDto());
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumberAndActiveSw(mobileNumber, LoansConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        LoansDto loansDto = LoansMapper.mapToLoansDto(loan, new LoansDto());
        return loansDto;
    }

    @Override
    public LoansDto fetchLoanById(Long loanNumber) {
        Loans loan = loansRepository.findByLoanNumberAndActiveSw(loanNumber, LoansConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber.toString()));
        LoansDto loansDto = LoansMapper.mapToLoansDto(loan, new LoansDto());
        return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loan = loansRepository.findByLoanNumberAndActiveSw(loansDto.getLoanNumber(), LoansConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber().toString()));
        
        LoansMapper.mapToLoans(loansDto, loan);
        loansRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(Long loanNumber) {
        Loans loan = loansRepository.findById(loanNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber.toString())
        );
        loan.setActiveSw(LoansConstants.IN_ACTIVE_SW);
        loansRepository.save(loan);
        return true;
    }

    @Override
    public boolean updateLoanMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        Loans loan = loansRepository.findByMobileNumberAndActiveSw(mobileNumberUpdate.getCurrentMobileNumber(), LoansConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumberUpdate.getCurrentMobileNumber()));
        loan.setMobileNumber(mobileNumberUpdate.getNewMobileNumber());
        loansRepository.save(loan);
        updateMobileNumberStatus(mobileNumberUpdate);
        return true;
    }

    public void updateMobileNumberStatus(MobileNumberUpdate mobileNumberUpdate) {
        log.info("Sending updateMobileNumberStatus request for the details: {}", mobileNumberUpdate);
        var result = streamBridge.send("updateMobileNumberStatus-out-0", mobileNumberUpdate);
        log.info("Is the updateMobileNumberStatus request sent successfully? : {}", result);
    }
}
