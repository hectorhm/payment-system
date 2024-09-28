package br.com.payment_system.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("WALLETS")
public record Wallet(
        @Id Long id,
        String fullName,
        String cpfCnpj,
        String email,
        String password,
        TypeOfWallet type,
        BigDecimal balance,
        @Version Long version
) {

    public Wallet debit(BigDecimal value) {
        return new Wallet(id, fullName, cpfCnpj, email, password, type, balance.subtract(value), version);
    }

    public Wallet credit(BigDecimal value) {
        return new Wallet(id, fullName, cpfCnpj, email, password, type, balance.add(value), version);
    }
}
