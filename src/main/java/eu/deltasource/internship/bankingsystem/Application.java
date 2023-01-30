package eu.deltasource.internship.bankingsystem;

public class Application {

    public static void main(String[] args) {

        CustomerService lilySmith = new CustomerService();
        CustomerService samSmith = new CustomerService();
        samSmith.createNewCustomer("Sam", "Smith", 18,9,2001);
        lilySmith.createNewCustomer("Lily", "Smith", 15,6,1993);

        System.out.println(lilySmith.printCustomer());
        System.out.println(samSmith.printCustomer());

        BankInstitutionService bnp = new BankInstitutionService();
        bnp.createBankInstitution("BNP", "Vasil Aprilov №15");
        bnp.addNewCustomer(lilySmith.getCustomer());
        bnp.addFee(Fee.BETWEEN_TWO_BANKS, 0.80);
        bnp.addFee(Fee.TRANSFER_BETWEEN_TWO_ACCOUNTS, 2.60);
        bnp.addExchangeRate(ExchangeRate.BGN_EUR, 0.556951);
        bnp.addExchangeRate(ExchangeRate.EUR_BGN, 0.511292);

        BankInstitutionService ddd = new BankInstitutionService();
        ddd.createBankInstitution("DDD", "bul. Ruski");
        ddd.addNewCustomer(samSmith.getCustomer());
        ddd.addFee(Fee.TRANSFER_BETWEEN_TWO_ACCOUNTS, 1.55);
        ddd.addFee(Fee.BETWEEN_TWO_BANKS, 2.10);
        ddd.addExchangeRate(ExchangeRate.BGN_EUR,1.6895);
        ddd.addExchangeRate(ExchangeRate.EUR_BGN,0.99898);

        System.out.println(bnp.printBankInstitution());

        BankAccountService lilyAccount = new BankAccountService();
        BankAccountService samAccount = new BankAccountService();
        lilyAccount.createBankAccount(bnp.getBankInstitution(), lilySmith.getCustomer(), "1234567890123456789012", Currency.BGN, 12.50, AccountType.CURRENT_ACCOUNT);
        samAccount.createBankAccount(bnp.getBankInstitution(), samSmith.getCustomer(), "1234567890123456789012", Currency.EUR, 12.50, AccountType.CURRENT_ACCOUNT);

        lilyAccount.deposit(100);
        lilyAccount.withdraw(50);
        System.out.println(lilyAccount.printBankAccount());
        lilyAccount.transfer(lilyAccount.getBankAccount(), samAccount.getBankAccount(),10);

        System.out.println("Lily's account: " + lilyAccount.printBankAccount());
        System.out.println("Sam's account: " + samAccount.printBankAccount());
    }

}
