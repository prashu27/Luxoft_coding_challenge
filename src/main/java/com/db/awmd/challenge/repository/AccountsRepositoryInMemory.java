package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AccountNotFound;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }

  @Override
  public Account getAccount(String accountId) throws AccountNotFound {
    Optional<Account> account =Optional.ofNullable(accounts.get(accountId));
    return account.orElseThrow(()->new AccountNotFound("Account id: "+accountId+" does not exist"));
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }

  @Override
  public void save(Account account) {
          accounts.put(account.getAccountId(),account);
  }


}
