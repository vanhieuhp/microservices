package hieunv.dev.loans.service;

import hieunv.dev.commonlib.dto.MobileNumberUpdate;
import hieunv.dev.loans.dto.LoansDto;
import hieunv.dev.loans.entity.Loans;

public interface ILoansService {

    LoansDto createLoan(Loans loan);

    LoansDto fetchLoan(String mobileNumber);

    LoansDto fetchLoanById(Long loanNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(Long loanNumber);

    boolean updateLoanMobileNumber(MobileNumberUpdate mobileNumberUpdate);
}
