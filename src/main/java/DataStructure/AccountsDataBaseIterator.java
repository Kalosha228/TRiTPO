package DataStructure;

import java.util.ArrayList;

public class AccountsDataBaseIterator implements MyIteratore {

    private int index;
    private ArrayList<CustomerAccount> database;

    public AccountsDataBaseIterator(ArrayList<CustomerAccount> database) {
        this.database = database;
        index = -1;
    }

    @Override
    public CustomerAccount nextObject() {
        index++;
        return database.get(index);
    }

    @Override
    public CustomerAccount currentObject() {
        return database.get(index);
    }

    @Override
    public boolean hasNext() {
        if (index + 1 < database.size())
            return true;
        else {
            index = -1;
            return false;
        }
    }

    public void resetIndex() {
        index = -1;
    }

    public void setList(ArrayList<CustomerAccount> database) {
        this.database = database;
    }
}
