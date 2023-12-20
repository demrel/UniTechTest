package com.UniTech.UniTechTest;

import com.UniTech.UniTechTest.dto.AccountDTO;
import com.UniTech.UniTechTest.dto.CreateAccountRequest;
import com.UniTech.UniTechTest.dto.TopUpRequest;
import com.UniTech.UniTechTest.dto.TransferRequest;
import com.UniTech.UniTechTest.enums.CurrencyType;
import com.UniTech.UniTechTest.enums.Role;
import com.UniTech.UniTechTest.exception.*;
import com.UniTech.UniTechTest.model.Account;
import com.UniTech.UniTechTest.model.AccountHistory;
import com.UniTech.UniTechTest.model.User;
import com.UniTech.UniTechTest.repository.AccountHistoryRepository;
import com.UniTech.UniTechTest.repository.AccountRepository;
import com.UniTech.UniTechTest.repository.UserRepository;
import com.UniTech.UniTechTest.service.AccountService;
import com.UniTech.UniTechTest.service.TransferHistoryService;
import com.UniTech.UniTechTest.serviceimpl.AccountServiceImp;
import com.UniTech.UniTechTest.serviceimpl.TransferServiceImp;
import com.UniTech.UniTechTest.validation.AccountValidation;
import com.UniTech.UniTechTest.validation.TransferRequestValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest

public class TransferServiceAndValidationTest {

    @Mock
    private TransferRequestValidation transferRequestValidation;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountValidation accountValidation;


    @Mock
    private TransferHistoryService transferHistoryService;

    @InjectMocks
    private TransferServiceImp transferServiceImp;

    @Test
    void shouldTransferSuccessfully() {
        // Arrange
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(100.0),1L,2L);
        Account fromAccount = Account.builder()
                .amount(BigDecimal.valueOf(140.0))
                .currencyType(CurrencyType.USD)
                .isActive(true)
                .build();

        Account toAccount = Account.builder()
                .amount(BigDecimal.valueOf(100.0))
                .currencyType(CurrencyType.USD)
                .isActive(true)
                .build();

        // Mock the behavior of dependencies
        when(accountService.getAndValidateAccount(transferRequest.getFrom())).thenReturn(fromAccount);
        when(accountService.getAndValidateAccount(transferRequest.getTo())).thenReturn(toAccount);

        assertDoesNotThrow(() -> transferRequestValidation.validateTransferRequest(transferRequest));
        assertDoesNotThrow(() -> accountValidation.validateActiveAccount(fromAccount));
        assertDoesNotThrow(() -> accountValidation.validateSufficientBalance(fromAccount,transferRequest.getAmount()));
        assertDoesNotThrow(() -> accountValidation.validateActiveAccount(toAccount));


        // Act and Assert

        // Verify
        verify(transferRequestValidation, times(1)).validateTransferRequest(transferRequest);
        verify(accountValidation, times(2)).validateActiveAccount(any(Account.class));
        verify(accountValidation, times(1)).validateSufficientBalance(any(Account.class), any(BigDecimal.class));
    }

    @ParameterizedTest
    @MethodSource("accountScenarios")
    void shouldTransfer(Account fromAccount, Account toAccount,BigDecimal transfer, Class<? extends Exception> expectedException) {
        // Arrange
        TransferRequest transferRequest = new TransferRequest(transfer, fromAccount.getId(), toAccount.getId());

        // Mock the behavior of dependencies
        when(accountService.getAndValidateAccount(fromAccount.getId())).thenReturn(fromAccount);
        when(accountService.getAndValidateAccount(toAccount.getId())).thenReturn(toAccount);

        // Act and Assert
        if (expectedException == null) {
            assertDoesNotThrow(() -> {
                transferRequestValidation.validateTransferRequest(transferRequest);
                accountValidation.validateActiveAccount(fromAccount);
                accountValidation.validateSufficientBalance(fromAccount, transferRequest.getAmount());
                accountValidation.validateActiveAccount(toAccount);
            });
        } else {
            assertDoesNotThrow(() -> transferRequestValidation.validateTransferRequest(transferRequest));
            assertDoesNotThrow(() -> accountValidation.validateActiveAccount(fromAccount));
            assertDoesNotThrow(() -> accountValidation.validateSufficientBalance(fromAccount,transferRequest.getAmount()));
            assertDoesNotThrow(() -> accountValidation.validateActiveAccount(toAccount));
        }

        // Verify
        verify(transferRequestValidation, times(1)).validateTransferRequest(transferRequest);
        verify(accountValidation, times(2)).validateActiveAccount(any(Account.class));
        verify(accountValidation, times(1)).validateSufficientBalance(any(Account.class), any(BigDecimal.class));
    }

    private static Stream<Object[]> accountScenarios() {
        return Stream.of(
                new Object[]{Account.builder().amount(BigDecimal.valueOf(140.0)).isActive(true).id(1L).build(),
                        Account.builder().amount(BigDecimal.valueOf(100.0)).isActive(true).id(2L).build(),
                        BigDecimal.valueOf(100.0),
                        null},  // Successful scenario
                new Object[]{Account.builder().amount(BigDecimal.valueOf(50.0)).isActive(true).id(1L).build(),
                        Account.builder().amount(BigDecimal.valueOf(100.0)).isActive(true).id(2L).build(),
                        BigDecimal.valueOf(100.0),
                        NotEnoughAmountException.class},  // Scenario where NotEnoughAmountException is expected
                new Object[]{Account.builder().amount(BigDecimal.valueOf(140.0)).isActive(false).id(1L).build(),
                        Account.builder().amount(BigDecimal.valueOf(100.0)).isActive(true).id(2L).build(),
                        BigDecimal.valueOf(100.0),
                        InactiveAccountException.class},
                new Object[]{Account.builder().amount(BigDecimal.valueOf(140.0)).isActive(false).id(1L).build(),
                        Account.builder().amount(BigDecimal.valueOf(100.0)).isActive(true).id(1L).build(),
                        BigDecimal.valueOf(100.0),
                        SameAccountTransferException.class}
        );
    }

}
