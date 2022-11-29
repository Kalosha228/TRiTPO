package Actions;

import Actions.WorkWithCatalog.AddingMoviesInCatalog;
import Actions.WorkWithCatalog.DeletingMoviesFromCatalog;
import DataStructure.CustomerAccount;

public class WorkWithUserCatalog {
    private AddingMoviesInCatalog addingMoviesInCatalog;
    private DeletingMoviesFromCatalog deletingMoviesFromCatalog;

    private CustomerAccount currentCustomerAccount;

    public void setCurrentCustomerAccount(CustomerAccount currentCustomerAccount) {
        this.currentCustomerAccount = currentCustomerAccount;
    }

    public CustomerAccount getCurrentCustomerAccount() {
        return this.currentCustomerAccount;
    }

    public WorkWithUserCatalog() {
        addingMoviesInCatalog = new AddingMoviesInCatalog();
        deletingMoviesFromCatalog = new DeletingMoviesFromCatalog();
    }

    public AddingMoviesInCatalog getAddingMoviesInCatalog() {
        return this.addingMoviesInCatalog;
    }

    public DeletingMoviesFromCatalog getDeletingMoviesFromCatalog() {
        return this.deletingMoviesFromCatalog;
    }

    public void showUserCatalog() {
       /* for (int i = 0; i < currentCustomerAccount.getCustomerMoviesList().size(); i++) {

        }*/
    }
}
