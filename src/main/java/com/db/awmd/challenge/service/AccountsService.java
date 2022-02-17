package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferMoney;
import com.db.awmd.challenge.exception.AccountNotFound;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  private EmailNotificationService emailNotificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) throws AccountNotFound {
    return this.accountsRepository.getAccount(accountId);
  }

  public void sendMoney(TransferMoney money) throws AccountNotFound {
        Account fromAccount =accountsRepository.getAccount(money.getFromAccount());
       Account toAccount =accountsRepository.getAccount(money.getToAccount());
      if((fromAccount.getBalance().compareTo(money.getAmount()))>0)
      {
        fromAccount.getBalance().subtract(money.getAmount());
        toAccount.getBalance().add(money.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);
        emailNotificationService.notifyAboutTransfer(fromAccount,"Amount :"+ money.getAmount() +" has been transferred to sucessfully to"+ money.getToAccount());
        emailNotificationService.notifyAboutTransfer(toAccount,"Amount :"+ money.getAmount() +" has been recieved from to sucessfully to"+ money.getFromAccount());

      }
  }
  }
