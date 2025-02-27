package com.virtualpairprogrammers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepaymentAmountTests {


    @Spy
    LoanApplication loanApplication;
    LoanCalculatorController controller;

    @BeforeEach
    public void setup() {
        loanApplication = spy(new LoanApplication());
        controller = new LoanCalculatorController();

        LoanRepository repository = mock(LoanRepository.class);
        JavaMailSender mailSender = mock(JavaMailSender.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        controller.setData(repository);
        controller.setMailSender(mailSender);
        controller.setRestTemplate(restTemplate);
    }

    @Test
    public void test1YearLoanWholePounds() {
        //given
        loanApplication.setPrincipal(1200);
        loanApplication.setTermInMonths(12);
        doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
        //when
        controller.processNewLoanApplication(loanApplication);
        //then
        assertEquals(new BigDecimal(110), loanApplication.getRepayment());
    }


    @Test
    public void test2YearLoanWholePounds() {
        //given
        loanApplication.setPrincipal(1200);
        loanApplication.setTermInMonths(24);
        doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
        //when
        controller.processNewLoanApplication(loanApplication);
        //then
        assertEquals(new BigDecimal(60), loanApplication.getRepayment());
    }

    @Test
    public void test3YearLoanWholePounds() {
        //given
        loanApplication.setPrincipal(5000);
        loanApplication.setTermInMonths(60);
        doReturn(new BigDecimal(6.5)).when(loanApplication).getInterestRate();
        //when
        controller.processNewLoanApplication(loanApplication);
        //then
        assertEquals(new BigDecimal(111), loanApplication.getRepayment());
    }
}
