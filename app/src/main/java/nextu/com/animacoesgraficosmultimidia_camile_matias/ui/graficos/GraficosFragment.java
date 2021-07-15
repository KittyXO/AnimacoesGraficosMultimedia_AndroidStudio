package nextu.com.animacoesgraficosmultimidia_camile_matias.ui.graficos;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GraficosFragment extends Fragment {

    private GLSurfaceView Tela;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tela = new GLSurfaceView(getActivity());
        Tela.setRenderer(new MyRenderer(this));
        return Tela; // aeee que lindo T_T fica pesado aqui lol auehaueh, boa
    }
}


