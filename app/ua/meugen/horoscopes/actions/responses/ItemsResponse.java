package ua.meugen.horoscopes.actions.responses;

import java.util.List;

/**
 * Created by admin on 03.06.2015.
 */
public final class ItemsResponse<T> extends BaseResponse {

    private List<T> items;

    /**
     * Getter for items.
     *
     * @return Items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Setter for items.
     *
     * @param items Items
     */
    public void setItems(final List<T> items) {
        this.items = items;
    }
}
