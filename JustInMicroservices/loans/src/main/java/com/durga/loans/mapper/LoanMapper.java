package com.durga.loans.mapper;

import com.durga.loans.dto.LoanDto;
import com.durga.loans.entity.Loan;

public class LoanMapper {

    public static Loan mapToLoan(Loan loan, LoanDto loanDto){
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loan.getOutstandingAmount());
        return loan;
    }

    public static LoanDto mapToLoanDto(LoanDto loanDto, Loan loan){
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDto;
    }
}
