package br.com.payment_system.transaction;

import br.com.payment_system.authorization.AuthorizerService;
import br.com.payment_system.notification.NotificationService;
import br.com.payment_system.wallet.TypeOfWallet;
import br.com.payment_system.wallet.Wallet;
import br.com.payment_system.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;
    private AuthorizerService authorizerService;
    private NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletRepository walletRepository,
                              AuthorizerService authorizerService,
                              NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {

        validate(transaction);

        var newTransaction = transactionRepository.save(transaction);

        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var wallerPayee = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayer.credit(transaction.value()));

        authorizerService.authorize(transaction);
        notificationService.notify(transaction);

        return newTransaction;
    }

    private void validate(Transaction transaction) {
        //TODO: criar anotação que realiza a validação para criar regra de negócio

        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - " + transaction)))
                .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - " + transaction));


        var payer = transactionRepository.findById(transaction.payer()).get();
        var payee = transactionRepository.findById(transaction.payee()).get();


    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private Boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type().equals(TypeOfWallet.COMUM.getValue()) &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }
}
