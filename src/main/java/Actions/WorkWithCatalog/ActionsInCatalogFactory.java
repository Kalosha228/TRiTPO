package Actions.WorkWithCatalog;

import DataStructure.CustomerAccount;

import java.util.ArrayList;

public interface ActionsInCatalogFactory {

    public void actionInCatalog(CustomerAccount customerAccount, int newMovieId);

    public boolean isAvailableThisMovie(ArrayList<Integer> movieList, int newMovieId);
}
