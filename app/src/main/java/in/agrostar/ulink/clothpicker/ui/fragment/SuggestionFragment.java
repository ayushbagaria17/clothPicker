package in.agrostar.ulink.clothpicker.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import in.agrostar.ulink.clothpicker.R;
import in.agrostar.ulink.clothpicker.domain.UploadObject;
import in.agrostar.ulink.clothpicker.domain.UploadType;
import in.agrostar.ulink.clothpicker.utils.Logger;


/**
 * Created by ayush on 23/4/17.
 */

public class SuggestionFragment extends BaseFragment {

    private Context context;

    @InjectView(R.id.iv_shirt_image)
    ImageView ivShirtImage;

    @InjectView(R.id.iv_trouser_image)
    ImageView ivTrouserImage;


    @InjectView(R.id.iv_liked)
    ImageView likedBtn;

    @InjectView(R.id.iv_unliked)
    ImageView unlikedBtn;


    private int position;
    private String shirtPath;
    private String trouserPath;
    private String imageUrl;

    private SuggestionFragmentListener listener;

    public static SuggestionFragment newInstance(int position, UploadObject uploadObject1, UploadObject uploadObject2) {

        Bundle args = new Bundle();
        args.putInt("position",position);
        if (uploadObject1.getType().toString().equalsIgnoreCase(UploadType.TROUSER.toString())) {
            args.putString("shirtPath", uploadObject2.getFilePath());
            args.putString("trouserPath", uploadObject1.getFilePath());
        }
        args.putString("shirtPath", uploadObject1.getFilePath());
        args.putString("trouserPath", uploadObject2.getFilePath());

        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloth_picker, container, false);
        ButterKnife.inject(this,view);
        if (getParentFragment() != null) {
            this.listener = (SuggestionFragmentListener)getParentFragment();
        }
        init();
        return view;
    }

    private void init() {
        initArgs();
        initView();
    }

    private void initArgs() {
        Bundle args = getArguments();
        position = args.getInt("position");
        shirtPath = args.getString("shirtPath");
        trouserPath = args.getString("trouserPath");
    }

    private void initView() {
//        tvDescription.setText(description);
        File file = new File(shirtPath);
        Glide.with(context)
                .load(file)
                .centerCrop().into(ivShirtImage);
         file = new File(trouserPath);
        Glide.with(context)
                .load(file)
                .centerCrop().into(ivTrouserImage);
    }

    @OnClick(R.id.iv_unliked)
    void Unliked() {
        Logger.logError("unliked");
        listener.unLiked(position);
    }

    @OnClick(R.id.iv_liked)
    void Liked() {
        Logger.logError("liked");
        listener.liked(position);
    }

    public interface SuggestionFragmentListener {
        void liked(int position);
        void unLiked(int position);
    }
}

