package com.db.awmd.challenge.domain;

import lombok.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransferMoney {
@NonNull
private String fromAccount;
@NonNull
private String toAccount;
@NonNull
@Min(value = 0, message = "value should be greater than 0")
BigDecimal amount;


}
