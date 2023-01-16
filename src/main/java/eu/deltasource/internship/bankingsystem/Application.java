package eu.deltasource.internship.bankingsystem;

public class Application {

    public static void main(String[] args) {
        Customer tomSmith = new Customer("Tom", "Smith","01/10/1991");
        Customer jerrySmith = new Customer("Jerry", "Smith","11/10/1991");
        System.out.println(tomSmith);

        BankInstitution dsk = new BankInstitution("DSK","Vasil Aprilov 22");
        BankInstitution bbb = new BankInstitution("BBB","Vasil Aprilov 23");
        dsk.addNewCustomer(tomSmith);
        bbb.addNewCustomer(jerrySmith);

        System.out.println(dsk);
        dsk.addFee("Deposit fee", 0.80);
        dsk.addFee("Withdraw fee",2.60);
        dsk.addFee("BGN exchange rate: ",2.60);
        System.out.println(dsk);

        BankAccount tomBankAccount = new BankAccount(dsk, tomSmith, "1234567890123456789012", Currency.BGN, 12.50,AccountType.CURRENT_ACCOUNT);
        BankAccount jerrySmithBankAccount = new BankAccount(bbb, jerrySmith,"1234567890123456789013", Currency.BGN, 200.31, AccountType.SAVINGS_ACCOUNT );
        System.out.println(tomBankAccount);

        Transaction transaction = new Transaction("1234567890123456789012", "1234567890123456789013", dsk, bbb, 15.60, Currency.USD, Currency.BGN, 0.5 );
        System.out.println(transaction);

        tomBankAccount.trasfer(tomBankAccount, jerrySmithBankAccount,10.50);
        System.out.println(tomBankAccount);
        System.out.println(jerrySmithBankAccount);

        jerrySmithBankAccount.withdraw(jerrySmithBankAccount, 100);
        System.out.println(jerrySmithBankAccount);



    }

}
