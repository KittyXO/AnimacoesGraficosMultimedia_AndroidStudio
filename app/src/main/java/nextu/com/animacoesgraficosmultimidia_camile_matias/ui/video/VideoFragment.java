package nextu.com.animacoesgraficosmultimidia_camile_matias.ui.video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import java.io.File;
import nextu.com.animacoesgraficosmultimidia_camile_matias.R;

import static android.app.Activity.RESULT_OK;

public class VideoFragment extends Fragment {

    private Button btnVideo;
    private VideoView videoView;

    private Uri videoUri;

    private static int REQUEST_CODE = 1;

    private static final String[] PERMISSOES = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int leer = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (leer == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSOES, REQUEST_CODE);
        }
        return inflater.inflate(R.layout.fragment_video, null);

    }
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            btnVideo = (Button) view.findViewById(R.id.btn_capvideo);
            videoView = (VideoView) view.findViewById(R.id.video_view);
            Button botao = (Button) view.findViewById(R.id.btn_abrirvideo);

            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                    File videosFolder = new File(Environment.getExternalStorageDirectory(), "VideosNextU");

                    videosFolder.mkdirs();

                    File video = new File(videosFolder, "video.mp4");

                    videoUri = Uri.fromFile(video);

                    videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

                    startActivityForResult(videoIntent, REQUEST_CODE);
                }
            });

            botao.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 2);

                }
            });

        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Toast.makeText(getActivity(), "O vídeo foi salvo em:\n"+ Environment.getExternalStorageDirectory() + "/VideosNextU/video.mp4", Toast.LENGTH_LONG).show();

            MediaController mediaController = new MediaController(getActivity());

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);
            videoView.start();

            mediaController.setAnchorView(videoView);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.start();
                }
            });

        }else{
            Toast.makeText(getActivity(), "Ocorreu um erro ao salvar o vídeo", Toast.LENGTH_SHORT).show();
        }
    }
}
