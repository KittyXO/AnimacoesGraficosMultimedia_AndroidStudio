package nextu.com.animacoesgraficosmultimidia_camile_matias.ui.audio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import nextu.com.animacoesgraficosmultimidia_camile_matias.R;

import static android.app.Activity.RESULT_OK;

public class AudioFragment extends Fragment {

    static final int Pick_song = 1;
    private static final int REQUEST_CODE = 1;

    private static final String[] PERMISSOES = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private Button botaoGravar;

    private static String nomeAudio = null;

    private MediaRecorder mediaRecorder = null;

    private MediaPlayer mediaPlayer = null;

    private Button botaoReproduzir;


    private void pararGravacao() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        Toast.makeText(getActivity(), "\n" +
                "O áudio foi salvo em:\n" + Environment.getExternalStorageDirectory() + "/audio.3gp", Toast.LENGTH_LONG).show();
    }

    private void grabando(boolean comecando) {
        if (comecando) {
            comecarGravacao();
        } else {
            pararGravacao();
        }
    }

    private void comecarReproducao() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(nomeAudio);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "ocorreu um erro na reprodução\n", Toast.LENGTH_SHORT).show();
        }
    }

    private void pareReproducao() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void onPlay(boolean comecarRep) {
        if (comecarRep) {
            comecarReproducao();
        } else {
            pareReproducao();
        }
    }


    private void comecarGravacao() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nomeAudio);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "\n" +
                    "Não será gravado corretamente", Toast.LENGTH_SHORT).show();
        }

        mediaRecorder.start();
    }

    boolean verificacao = true;
    boolean verficacao2 = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int ler = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ler2 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
        int ler1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (ler == PackageManager.PERMISSION_DENIED || ler2 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSOES, REQUEST_CODE);
        }

        return inflater.inflate(R.layout.fragment_audio, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nomeAudio = Environment.getExternalStorageDirectory() + "/audio.3gp";

        botaoGravar = (Button) view.findViewById(R.id.btn_gravar);
        botaoReproduzir = (Button) view.findViewById(R.id.btn_reproduzir);
        Button botaoAbrir = (Button) view.findViewById(R.id.btn_abriraudio);


        botaoGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabando(verificacao);

                if (verificacao) {
                    botaoGravar.setText("Pare a gravação");
                } else {
                    botaoGravar.setText("Iniciar Gravação");
                }

                verificacao = !verificacao;
            }
        });

        botaoReproduzir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(verficacao2);
                if (verficacao2) {
                    botaoReproduzir.setText("pare a reprodução");
                } else {
                    botaoReproduzir.setText("Reproduzir");
                }

                verficacao2 = !verficacao2;
            }
        });


        botaoAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Escolha um audio"), Pick_song);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Pick_song:
                if (resultCode == RESULT_OK) {
                    String patch = data.getDataString();

                    try {
                        MediaPlayer audio = new MediaPlayer();
                        audio.setDataSource(getActivity(), Uri.parse(patch));
                        audio.prepare();
                        audio.start();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "erro ao executar o áudio", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}