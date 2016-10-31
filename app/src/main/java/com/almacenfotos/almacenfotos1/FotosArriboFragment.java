package com.almacenfotos.almacenfotos1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import Utilerias.Comun;
import Utilerias.DatosCaptura;
import Utilerias.Directorio;
import Utilerias.EnviaDatos;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FotosArriboFragment extends Fragment {

    View _rootView;
    Context _context;
    ImageView _Imagen;
    ImageButton _btnEnviar;
    ImageButton _btnCamara;
    EditText _txtKB;
    String _Directorio;

    private final int _CapturarImagen = 1;
    private final int _SeleccionarImagen = 2;
    File _newFile;

    //private PhotoUtils photoUtils;

    public FotosArriboFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _context = container.getContext();
        _rootView = inflater.inflate(R.layout.fragment_fotos_arribo,container,false);
        _mInicializaElementos();
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return _rootView;
    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_camaraarribo,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_Galeriaarribo:
                mAbrirGaleria();
                return false;
            default:
                break;
        }
        return false;
    }*/

    private void _mInicializaElementos(){
        try{
            //Inicializa boton Enviar
            _btnEnviar = (ImageButton)
                    _rootView.findViewById(R.id.btnEnviararribo);

            _btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (Comun.isEmpty(_txtKB.getText().toString())) {
                            if(_newFile !=null) {
                                if (_newFile.exists()) {
                                    EnviaDatos Enviar = new EnviaDatos(_context,_Imagen,null);
                                    Enviar.EnviaDatosImagenes();

                                } else {
                                    Comun.MessageBox(_context, "No existe la fotografia");
                                }
                            }else{
                                Comun.MessageBox(_context, "No existe la fotografia");
                            }
                        } else {
                            Comun.MessageBox(_context, "Ingrese el KB");
                        }
                    }catch (Exception e){
                        Log.e("FotosKB", e.toString());
                    }
                }
            });

            //Inicializa el boton de la camara
            _btnCamara = (ImageButton) _rootView.findViewById(R.id.btnCamaraarribo);
            _btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Comun.isEmpty(_txtKB.getText().toString())){
                        mAbrirCamara();
                    }else{
                        Comun.MessageBox(_context,"Ingrese el KB");
                    }
                }
            });

            _Imagen = (ImageView) _rootView.findViewById(R.id.imgCamaraarribo);
            _txtKB = (EditText) _rootView.findViewById(R.id.txtKBarribo);
        }catch (Exception e){
            Log.e("FotosKB", e.toString());
        }
    }

    private void mAbrirCamara(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        _newFile = null;
        try{
            _Directorio = Directorio._getRutaFotos() + Directorio._getNombreImagen();
            _newFile = new File( Directorio._getRutaFotos(), Directorio._getNombreImagen());
        }catch (Exception e){

        }
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(_newFile));
        startActivityForResult(intent,
                _CapturarImagen);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void mAbrirGaleria(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Selecciona imagen"), _SeleccionarImagen);
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

        try {
            switch (requestCode) {
                case _CapturarImagen:
                    if (resultCode == RESULT_OK) {
                        if (data!=null) {
                            Uri uriImagen = Uri.fromFile(_newFile);
                            _Imagen.setImageURI(uriImagen);
                        }else{
                            Uri uriImagen = Uri.fromFile(_newFile);
                            _Imagen.setImageURI(uriImagen);
                        }

                        GuardarDatos();
                    }
                    break;
                case _SeleccionarImagen:
                    if (resultCode == RESULT_OK) {

                        Uri path = data.getData();
                        _Imagen.setImageURI(path);
                        _newFile = new File(path.toString());
                    }
                    break;
            }
        }catch (Exception e){
            Log.i("FotosKB", "onActivityResult "+e.toString() );
        }
    }

    private void GuardarDatos(){
        try {
            DatosCaptura datosCaptura = new DatosCaptura();
            datosCaptura.setDescripcion(_txtKB.getText().toString().trim());
            datosCaptura.setRuta(_newFile.getAbsolutePath().toString());
            datosCaptura.setTipo("1");
            Comun.InsertaDatos(_context,datosCaptura);
        }catch (Exception e){
            Log.e("FotosKB", "Guarda Datos " + e.toString());
        }
    }
}
