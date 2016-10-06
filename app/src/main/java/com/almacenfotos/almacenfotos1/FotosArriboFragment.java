package com.almacenfotos.almacenfotos1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import Utilerias.DatosBDHelper;
import Utilerias.DatosCaptura;
import Utilerias.Directorio;

/**
 * A simple {@link Fragment} subclass.
 */
public class FotosArriboFragment extends Fragment {

    View _rootView;
    Button _btnCamara;
    Button _btnGuardaImagen;
    ImageView _Imagen;
    Bitmap _ImagenBitmap;
    Context _context;
    LinearLayout _llFoto;

    DatosBDHelper BDHelper;

    private static int _CapturarImagen = 1;
    private static int _SeleccionarImagen = 2;
    private String name = "";

    public FotosArriboFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _context = container.getContext();
        _rootView = inflater.inflate(R.layout.fragment_fotos_arribo, container, false);

        this._mInicializaElementos();

        return _rootView;

    }

    private void _mInicializaElementos(){
        try{
            Button _btnCamara = (Button) _rootView.findViewById(R.id.button1);
            Button _btnGuardaImagen = (Button) _rootView.findViewById(R.id.btnGuardarFoto);

            LinearLayout _llFoto = (LinearLayout) _rootView.findViewById(R.id.llFoto);

            _btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(),"Click en el boton foto", Toast.LENGTH_LONG).show();
                    _mAbrirCamara();
                }
            });

            _btnGuardaImagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGuardaImagen();
                }
            });

        }catch (Exception e){
            Log.e("calix", e.toString());
        }
    }

    private void _mAbrirCamara(){
        int code = _CapturarImagen;
        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Uri output = Uri.fromFile(new File(name));
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent,code);
    }

    /**
     * Metodo para recibir la imagen despues de la captura con la camara
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(),String.valueOf(requestCode) , Toast.LENGTH_LONG).show();
        if(requestCode == _CapturarImagen){
            _Imagen = (ImageView)_rootView.findViewById(R.id.imagenArribo);
            _Imagen.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            _ImagenBitmap = ((BitmapDrawable) _Imagen.getDrawable()).getBitmap();

            _llFoto.getBackground();
        }
    }

    /**
     * Metodo para guardar imagen en la memoria externa
     */
    private void mGuardaImagen(){
        try {



            File dirFoto = new File(Directorio._getRutaFotos());

            try{
                File archivoFoto = new File(dirFoto , Directorio.getNombreImagen());
                FileOutputStream fOutput = new FileOutputStream(archivoFoto);
                _ImagenBitmap.compress(Bitmap.CompressFormat.JPEG , 100 , fOutput);


                fOutput.flush();
                Log.i("FotosKB","Foto guardada");

                this.mGuardarDatos("ruta");

            }catch (Exception e){
                Log.e("FotosKB",e.toString());
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void mGuardarDatos(String ruta){

        BDHelper = new DatosBDHelper(_context);
        DatosCaptura datos = new DatosCaptura();

        datos.setArray("array");
        datos.setDescripcion("descripcion");
        datos.setEstatus("0");
        datos.setRuta(ruta);
        datos.setTipo("Foto");

        if(BDHelper._InsertaDatos(datos)){
            Toast.makeText(getContext(),"Datos guardados!!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"Ocurrio un error al guardar los datos", Toast.LENGTH_LONG).show();
        }
    }


}
