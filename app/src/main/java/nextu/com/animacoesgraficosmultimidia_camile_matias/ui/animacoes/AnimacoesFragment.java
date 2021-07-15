package nextu.com.animacoesgraficosmultimidia_camile_matias.ui.animacoes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import nextu.com.animacoesgraficosmultimidia_camile_matias.R;

public class AnimacoesFragment extends Fragment {
    ImageView ic_star;
    ImageButton imageButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animacoes, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ic_star = getActivity().findViewById(R.id.ic_star);
        imageButton = getActivity().findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlay();
            }
        });
    }

    public void onClickPlay() {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_star);
        ic_star.startAnimation(animation);
    }
}