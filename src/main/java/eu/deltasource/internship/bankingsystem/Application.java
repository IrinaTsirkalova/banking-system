package eu.deltasource.internship.bankingsystem;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.service.*;

/**
 * Represents the program starting point
 */
public class Application {

    public static void printCustomer(CustomerService service, Customer customer) {
        System.out.println(service.printCustomerInfo(customer));
    }

    public static void printBank(BankInstitutionService service, String name) {
        System.out.println(service.printBankInfo(name));
    }

    public static void printBankAccounts(BankInstitutionService service, String name) {
        System.out.println(service.printBankAccountInfo(name));
    }

    public static void printBankFees(BankInstitutionService service, String name) {
        System.out.println(service.printBankFeeInfo(name));
    }

    public static void printBankExchangeRate(BankInstitutionService service, String name) {
        System.out.println(service.printBankExchangeRateInfo(name));
    }

    public static void printCustomerBankAccount(BankAccountService service, String iban) {
        System.out.println(service.printBankAccount(iban));
    }

    public static void printBankCustomerNumbers(BankInstitutionService service, String name) {
        System.out.println(service.printBankCustomersNumberInfo(name));
    }

    public static void main(String[] args) {

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 11, 2001);
        Customer sam = customerFactory.createNewCustomer("Sam", "Thomas", 1, 11, 2001);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        customerService.addNewCustomer(sam);
        System.out.println("Print customer information-----------------------------------------------");
        printCustomer(customerService, lily);
        printCustomer(customerService, sam);

        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution dsk = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitution rfb = bankInstitutionFactory.createBankInstitution("RFB", "Vasil Naidenov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(dsk);
        bankInstitutionService.addNewBankInstitution(rfb);
        System.out.println("Print bank information-----------------------------------------------");
        printBank(bankInstitutionService, "DSK");
        printBank(bankInstitutionService, "RFB");
        System.out.println("Print bank accounts information-----------------------------------------------");
        printBankAccounts(bankInstitutionService, "DSK");
        printBankAccounts(bankInstitutionService, "RFB");

        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.55);
        bankInstitutionService.addNewFee("RFB", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.55);
        System.out.println("Print bank fees information-----------------------------------------------");
        printBankFees(bankInstitutionService, "DSK");
        printBankFees(bankInstitutionService, "RFB");

        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 1.55);
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.80);
        System.out.println("Print bank exchange rate information-----------------------------------------------");
        printBankExchangeRate(bankInstitutionService, "DSK");
        printBankExchangeRate(bankInstitutionService, "RFB");

        BankAccountService bankAccountService = new BankAccountService();
        bankAccountService.addNewBankAccount("DSK", lily, "111111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sam, "222", Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);

        System.out.println("Print customer bank account information-----------------------------------------------");
        printCustomerBankAccount(bankAccountService, "111111");
        System.out.println("Print bank accounts information-----------------------------------------------");
        printBankAccounts(bankInstitutionService, "DSK");
        System.out.println("Print bank customer number information-----------------------------------------------");
        printBankCustomerNumbers(bankInstitutionService, "DSK");

        TransactionService transactionService = new TransactionService();
        transactionService.withdraw("123", "111111", 14);
        System.out.println("Print customer bank account information after withdraw----------------------------------------------");
        printCustomerBankAccount(bankAccountService, "111111");
        transactionService.deposit("1234", "111111", 15);
        System.out.println("Print customer bank account information after deposit-----------------------------------------------");
        printCustomerBankAccount(bankAccountService, "111111");

        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("3", "DSK", "RFB", "111111", "222", 4);
        System.out.println("Print source account-----------------------------------------------");
        printCustomerBankAccount(bankAccountService, "111111");
        System.out.println("Print target account-----------------------------------------------");
        printCustomerBankAccount(bankAccountService, "222");
        System.out.println("Print bank transaction history-----------------------------------------------");
        System.out.println(transactionService.printBankTransactionListInfo("RFB"));
        System.out.println("Statement source account: " + transactionService.printBankStatementForAPeriod("111111", 2, 2, 2023, 0, 0, 5, 2, 2023, 22, 30));
        System.out.println("Statement target account: " + transactionService.printTransactionForAccount("222"));
    }
}
