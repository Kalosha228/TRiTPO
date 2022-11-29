package Actions.WorkWithCatalog;

import DataStructure.CustomerAccount;

import java.util.ArrayList;

public class MarkMoviesAsWatched implements ActionsInCatalogFactory {
    @Override
    public void actionInCatalog(CustomerAccount customerAccount, int newMovieId) {
        if (!isAvailableThisMovie(customerAccount.getWatchedCustomerMoviesList(), newMovieId)) {
            customerAccount.getWatchedCustomerMoviesList().add(newMovieId);
            System.out.println("yes"+newMovieId);
        }
    }

    @Override
    public boolean isAvailableThisMovie(ArrayList<Integer> movieList, int newMovieId) {
        for (int i = 0; i < movieList.size(); i++)
            if (movieList.get(i).equals(newMovieId))
                return true;
        return false;
    }

    public void resetWachedStatus(CustomerAccount customerAccount, int newMovieId){
        if (isAvailableThisMovie(customerAccount.getWatchedCustomerMoviesList(), newMovieId)) {
            customerAccount.getWatchedCustomerMoviesList().remove(customerAccount.getWatchedIndexMovieInUserCatalog(newMovieId));
            System.out.println("yes"+newMovieId);
        }
    }
}
