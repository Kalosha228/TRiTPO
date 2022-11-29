package DataStructure;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountsDataBase implements Serializable {

    private ArrayList<CustomerAccount> customerAccountsDataBase;
    private AccountsDataBaseIterator accountsDataBaseIterator;

    public AccountsDataBase() {
        customerAccountsDataBase = new ArrayList<>();
        accountsDataBaseIterator = new AccountsDataBaseIterator(this.customerAccountsDataBase);
    }

    public boolean addNewUser(CustomerAccount user) {
        if (!checkIsAvailableThisLogin(user)) {
            customerAccountsDataBase.add(user);
            return true;
        } else
            return false;
    }

    public ArrayList<CustomerAccount> getCustomerAccountsDataBase() {
        return customerAccountsDataBase;
    }

    public void setCustomerAccountsDataBase(ArrayList<CustomerAccount> customerAccountsDataBase) {
        this.customerAccountsDataBase = customerAccountsDataBase;
    }

    public AccountsDataBaseIterator getIterator() {
        return accountsDataBaseIterator;
    }

    public boolean checkIsAvailableThisLogin(CustomerAccount user) {
        accountsDataBaseIterator.resetIndex();
        while (accountsDataBaseIterator.hasNext()) {
            if (accountsDataBaseIterator.nextObject().getUserLogin().equals(user.getUserLogin()))
                return true;
        }
        return false;
    }

    public boolean checkIsAvailableThisUser(CustomerAccount user) {
        accountsDataBaseIterator.resetIndex();
        while (accountsDataBaseIterator.hasNext()) {
            if (accountsDataBaseIterator.nextObject().getUserLogin().equals(user.getUserLogin()))
                return true;
        }
        return false;
    }

    public CustomerAccount getUserObject(String userLogin) {
        accountsDataBaseIterator.resetIndex();
        while (accountsDataBaseIterator.hasNext()) {
            if (accountsDataBaseIterator.nextObject().getUserLogin().equals(userLogin))
                return accountsDataBaseIterator.currentObject();
        }
        return null;
    }
}
