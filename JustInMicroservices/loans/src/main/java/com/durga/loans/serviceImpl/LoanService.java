package com.durga.loans.serviceImpl;

import com.durga.loans.constants.LoanConstants;
import com.durga.loans.dto.LoanDto;
import com.durga.loans.entity.Loan;
import com.durga.loans.exception.LoanAlreadyExistsException;
import com.durga.loans.exception.ResourceNotFoundException;
import com.durga.loans.mapper.LoanMapper;
import com.durga.loans.repository.LoanRepository;
import com.durga.loans.serviceInt.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanService implements ILoanService {

    private LoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> loan=loanRepository.findByMobileNumber(mobileNumber);
        if(loan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan loan=new Loan();
        long randNumber=100000000000L+new Random().nextInt(900000000);
        loan.setLoanNumber(Long.toString(randNumber));
        loan.setMobileNumber(mobileNumber);
        loan.setLoanType(LoanConstants.HOME_LOAN);
        loan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return loan;
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan=loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "MobileNumber", mobileNumber)
        );
        return LoanMapper.mapToLoanDto(new LoanDto(), loan);
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan= loanRepository.findByLoanNumber(loanDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber())
        );
        LoanMapper.mapToLoan(loan, loanDto);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan=loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "MobileNumber", mobileNumber)
        );
        loanRepository.deleteById(loan.getLoadId());
        return true;
    }
}
