package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class FavoriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Neighbour> mNeighboursFav;

    /**
     * Create and return a new instance
     * @return @{@link FavoriteFragment}
     */
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of favorite neighbours
     */

    private void initListFavorite() {
        mNeighboursFav =  ListNeighbourActivity.mNeighbours.stream().filter(neighbour -> neighbour.getFavorite() == true).collect(Collectors.toList());
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighboursFav, true));
    }

    @Override
    public void onResume() {
        super.onResume();
        initListFavorite();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button in favorite list
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbourFavoris(DeleteFavoriteNeighbourEvent event) {
        int index = ListNeighbourActivity.mNeighbours.indexOf(event.neighbour);
        ListNeighbourActivity.mNeighbours.get(index).setFavorite(false);
        mNeighboursFav.remove(event.neighbour);
        initListFavorite();
    }

}