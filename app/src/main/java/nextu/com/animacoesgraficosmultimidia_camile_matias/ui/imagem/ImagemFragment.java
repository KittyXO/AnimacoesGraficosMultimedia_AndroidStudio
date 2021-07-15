package nextu.com.animacoesgraficosmultimidia_camile_matias.ui.imagem;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import nextu.com.animacoesgraficosmultimidia_camile_matias.R;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import java.io.File;

import static android.app.Activity.RESULT_OK;


public class ImagemFragment extends Fragment {

    ImageView imgCamera;

    private static final int REQUEST_CODE = 1;

    private static final String[] PERMISSAO = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int ler = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ler == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSAO, REQUEST_CODE);

        }

            return inflater.inflate(R.layout.fragment_imagem, null);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button botao = (Button) view.findViewById(R.id.btn_abrir);
        botao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);


            }
        });

        imgCamera = (ImageView) view.findViewById(R.id.img_camera);
        Button botaoCamera = (Button) view.findViewById(R.id.btn_capturar);
        botaoCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File imagemFolder = new File(Environment.getExternalStorageDirectory(), "CameraFolder");

                imagemFolder.mkdirs();

                File imagem = new File(imagemFolder, "foto.jpg");

                Uri uriImagem = Uri.fromFile(imagem);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagem);

                startActivityForResult(cameraIntent, REQUEST_CODE);
            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), "a imagem foi salva:\n" + Environment.getExternalStorageDirectory() + "/CameraFolder/foto.jpg", Toast.LENGTH_LONG).show();

            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/CameraFolder/foto.jpg");

            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            float scaleA = ((float) (width / 2)) / width;
            float scaleB = ((float) (height)) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleA, scaleB);

            Bitmap novaImagem = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

            imgCamera.setImageBitmap(novaImagem);

        } else {
            Toast.makeText(getActivity(), "a imagem não foi salva", Toast.LENGTH_LONG).show();
        }
    }
}





