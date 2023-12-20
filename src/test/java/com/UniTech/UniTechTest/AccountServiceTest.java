package com.UniTech.UniTechTest;

import com.UniTech.UniTechTest.dto.AccountDTO;
import com.UniTech.UniTechTest.dto.CreateAccountRequest;
import com.UniTech.UniTechTest.dto.TopUpRequest;
import com.UniTech.UniTechTest.enums.CurrencyType;
import com.UniTech.UniTechTest.enums.Role;
import com.UniTech.UniTechTest.exception.AccountNotFoundException;
import com.UniTech.UniTechTest.exception.TransferAmountException;
import com.UniTech.UniTechTest.model.Account;
import com.UniTech.UniTechTest.model.AccountHistory;
import com.UniTech.UniTechTest.model.User;
import com.UniTech.UniTechTest.repository.AccountHistoryRepository;
import com.UniTech.UniTechTest.repository.AccountRepository;
import com.UniTech.UniTechTest.repository.UserRepository;
import com.UniTech.UniTechTest.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountHistoryRepository accountHistoryRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    private Account createMockAccount(Long accountId, Long userId, boolean isActive) {
        Account account = new Account();
        account.setId(accountId);
        account.setActive(isActive);
        // Set other necessary properties
        return account;
    }

    private Account createExpectedAccount(Long accountId) {
        User user = User.builder()
                .pin("az1234")
                .id(1L)
                .password("HashedPass")
                .role(Role.User)
                .build();

        CurrencyType currencyType = CurrencyType.USD;
        BigDecimal amount = BigDecimal.valueOf(100.0);
        boolean isActive = true;

        Account expectedAccount = new Account();
        expectedAccount.setId(accountId);
        expectedAccount.setUser(user);
        expectedAccount.setCurrencyType(currencyType);
        expectedAccount.setAmount(amount);
        expectedAccount.setActive(isActive);

        return expectedAccount;
    }

    @Test
    void shouldGetAndValidateAccountSuccessfully() {
        // Arrange
        Long accountId = 1L;
        Account expectedAccount = createExpectedAccount(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(expectedAccount));

        // Act
        Account resultAccount = accountService.getAndValidateAccount(accountId);

        // Assert
        assertNotNull(resultAccount);
        assertEquals(expectedAccount, resultAccount);
    }

    @Test
    void shouldThrowAccountNotFoundException() {
        // Arrange
        Long accountId = 2L;

        // Mock the behavior of the accountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAndValidateAccount(accountId);
        });
    }

    @Test
    void shouldGetActiveAccountsByUserId() {
        // Arrange
        Long userId = 1L;

        // Mock the behavior of the accountRepository
        List<Account> mockAccounts = Arrays.asList(
                createMockAccount(1L, userId, true),
                createMockAccount(1L, userId, true)
        );
        when(accountRepository.findAllByIsActiveTrueAndUserId(userId)).thenReturn(mockAccounts);

        // Act
        List<AccountDTO> resultDTOs = accountService.getActiveAccountsByUserId(userId);

        // Assert
        assertEquals(2, resultDTOs.size());
        // You can add more assertions based on your requirements
    }


    @Test
    void shouldCreateAccountSuccessfully() {
        // Arrange
        long userId = 1L;
        CreateAccountRequest request = new CreateAccountRequest(CurrencyType.USD, BigDecimal.valueOf(100.0));

        // Mock the behavior of userRepository.getReferenceById(userId)
        User mockUser = mock(User.class);
        when(userRepository.getReferenceById(userId)).thenReturn(mockUser);

        // Mock the behavior of accountRepository.save
        when(accountRepository.save(any(Account.class))).thenReturn(Account.builder()
                .user(mockUser).amount(BigDecimal.valueOf(100.0))
                .currencyType(CurrencyType.USD)
                .build());

        // Act
        accountService.createAccount(request, userId);

        // Assert
        verify(accountRepository, times(1)).save(any(Account.class));
    }



    @Test
    void shouldTopUpSuccessfully() {
        // Arrange
        TopUpRequest request = new TopUpRequest( BigDecimal.valueOf(100.0),1L);

        // Mock the behavior of getAndValidateAccount
        Account mockAccount = Account.builder()
                .currencyType(CurrencyType.USD)
                .amount(BigDecimal.valueOf(100.0))
                .build();

        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.of(mockAccount));

        // Act
        accountService.topUp(request);

        // Verify other interactions based on your requirements
        verify(accountRepository, times(1)).findById(request.getAccountId());
        verify(accountRepository, times(1)).save(mockAccount);
        verify(accountHistoryRepository, times(1)).save(any(AccountHistory.class));
    }

    @Test
    void shouldThrowTransferAmountException() {
        // Arrange
        TopUpRequest request = new TopUpRequest( BigDecimal.valueOf(0.5),1L);

        // Act and Assert
        assertThrows(TransferAmountException.class, () -> {
            accountService.topUp(request);
        });

        // Ensure that accountRepository.findById is not called when an exception is thrown
        verify(accountRepository, never()).findById(request.getAccountId());

    }

    @Test
    void shouldAddAccountBalanceSuccessfully() {
        // Arrange
        Account account =  Account.builder()
                .currencyType(CurrencyType.USD)
                .amount(BigDecimal.valueOf(100.0))
                .build();

        BigDecimal transferAmount = BigDecimal.valueOf(100.0);

        // Mock the behavior of accountRepository and accountHistoryRepository
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountHistoryRepository.save(any(AccountHistory.class))).thenReturn(mock(AccountHistory.class));
        // Act
        accountService.addAccountBalance(account, transferAmount);

        // Assert
        verify(accountRepository, times(1)).save(account);
        verify(accountHistoryRepository, times(1)).save(any(AccountHistory.class));
    }
}
