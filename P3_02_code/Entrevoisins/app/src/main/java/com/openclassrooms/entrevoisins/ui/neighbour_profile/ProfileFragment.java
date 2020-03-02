package com.openclassrooms.entrevoisins.ui.neighbour_profile;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String KEY_NEIGHBOUR = "KEY_NEIGHBOUR";
    private Neighbour neighbour;

    @BindView(R.id.textViewName)
    TextView name;
    @BindView(R.id.textViewName1)
    TextView name1;
    @BindView(R.id.avatarProfil)
    ImageView avatarUrl;
    @BindView(R.id.textViewAdresse)
    TextView address;
    @BindView(R.id.textViewPhone)
    TextView phoneNumber;
    @BindView(R.id.textViewEmail)
    TextView email;
    @BindView(R.id.Description)
    TextView aboutMe;
    @BindView(R.id.favoriteActionButton)
    FloatingActionButton favoriteActionButton;

    public ProfileFragment() { }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, result);
        Intent intent = this.getActivity().getIntent();
        if (intent != null){
            if (intent.hasExtra(KEY_NEIGHBOUR)){
                this.neighbour = intent.getParcelableExtra(KEY_NEIGHBOUR);
                int index = ListNeighbourActivity.mNeighbours.indexOf(neighbour);
                name1.setText(neighbour.getName());
                name.setText(neighbour.getName());
                Glide.with(avatarUrl.getContext())
                        .load(neighbour.getAvatarUrl())
                        .into(avatarUrl);
                address.setText(neighbour.getAddress());
                phoneNumber.setText(neighbour.getPhoneNumber());
                email.setText("www.facebook.fr/" + neighbour.getName());
                aboutMe.setText(neighbour.getAboutMe());
                /** Change color of Favorite Neighbour button **/
                if (ListNeighbourActivity.mNeighbours.get(index).getFavorite()){
                    favoriteActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                    favoriteActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_white_24dp));
                }

                /** For Favorite Neighbour button **/
                favoriteActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!ListNeighbourActivity.mNeighbours.get(index).getFavorite()){
                            ListNeighbourActivity.mNeighbours.get(index).setFavorite(true);
                            favoriteActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                            favoriteActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_white_24dp));
                        }else{
                            ListNeighbourActivity.mNeighbours.get(index).setFavorite(false);
                            favoriteActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            favoriteActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_yellow_24dp));
                        }
                    }
                });

            }
        }

        return (result);
    }
}
