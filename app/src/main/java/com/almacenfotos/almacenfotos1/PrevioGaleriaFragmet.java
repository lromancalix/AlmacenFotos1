package com.almacenfotos.almacenfotos1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import Utilerias.Comun;
import Utilerias.DatosCaptura;
import Utilerias.EnviaDatos;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrevioGaleriaFragmet extends Fragment {

    View _rootView;
    Context _context;
    ImageButton _BtnEnviar;
    ImageView _Imagen1,_Imagen2, _Imagen3, _Imagen4;
    EditText _EdtKB;
    String _Path1,_Path2, _Path3, _Path4;
    private static  String _CodigoKB ="3";
    private int _SeleccionarImagen;
    int _numElementos =0;


    public PrevioGaleriaFragmet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        this._CodigoKB = b.getString("tipo");
        _context = container.getContext();
        _rootView = inflater.inflate(R.layout.fragment_previo_galeria_fragmet,container,false);

        mInicializaElementos();
        setHasOptionsMenu(true);
      //  Comun.MessageBox(_context,_CodigoKB);
        // Inflate the layout for this fragment
        return _rootView;
    }

    private void mInicializaElementos(){
        this._Imagen1 = (ImageView) _rootView.findViewById(R.id.imgPrevio1);
        this._Imagen2 = (ImageView) _rootView.findViewById(R.id.imgPrevio2);
        this._Imagen3 = (ImageView) _rootView.findViewById(R.id.imgPrevio3);
        this._Imagen4 = (ImageView) _rootView.findViewById(R.id.imgPrevio4);
        this._BtnEnviar = (ImageButton) _rootView.findViewById(R.id.btnGaleriaEnviar);
        this._EdtKB = (EditText) _rootView.findViewById(R.id.EdtKBPrevioGaleria);
switch (_CodigoKB){
    case "1":
        this._EdtKB.setHint("Capture el Arribo");
        this._EdtKB.setInputType(1);
        break;
    case "2":
        this._EdtKB.setHint("Capture el numero de cruce");
        this._EdtKB.setInputType(2);
        break;
    case "3":
        this._EdtKB.setHint("Capture el numero de previo");
        this._EdtKB.setInputType(2);
        break;

}



        this._Path1 = this._Path2 = this._Path3 = this._Path4 = "";

        this._Imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _SeleccionarImagen =1;
                mAbrirGaleria();
            }
        });
        this._Imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _SeleccionarImagen =2;
                mAbrirGaleria();
            }
        });
        this._Imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _SeleccionarImagen =3;
                mAbrirGaleria();
            }
        });
        this._Imagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _SeleccionarImagen =4;
                mAbrirGaleria();
            }
        });

        this._BtnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_numElementos >=1){
                    GuardarDatos();
                }else{
                    Comun.MessageBox(_context,"Debe seleccionar 1 imagen.");
                }

            }
        });

    }

    private void mAbrirGaleria(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Selecciona imagen"), _SeleccionarImagen);
    }

    private void GuardarDatos(){
        try {
            DatosCaptura datosCaptura = new DatosCaptura();
            if(!this._Path1.equals("")) {
                Log.e("FotosKB", "IMAGEN 1");
                datosCaptura.setDescripcion(this._EdtKB.getText().toString().trim());
                datosCaptura.setRuta(this._Path1);
                datosCaptura.setTipo(_CodigoKB);
                Comun.InsertaDatos(_context, datosCaptura);
            }

            if(!this._Path2.equals("")) {
                datosCaptura = new DatosCaptura();
                datosCaptura.setDescripcion(this._EdtKB.getText().toString().trim());
                datosCaptura.setRuta(this._Path2);
                datosCaptura.setTipo(_CodigoKB);
                Comun.InsertaDatos(_context, datosCaptura);
                Log.e("FotosKB", "IMAGEN 2");
            }

            if(!this._Path3.equals("")) {
                datosCaptura = new DatosCaptura();
                datosCaptura.setDescripcion(this._EdtKB.getText().toString().trim());
                datosCaptura.setRuta(this._Path3);
                datosCaptura.setTipo(_CodigoKB);
                Comun.InsertaDatos(_context, datosCaptura);
                Log.e("FotosKB", "IMAGEN 3");
            }

            if(!this._Path4.equals("")) {
                datosCaptura = new DatosCaptura();
                datosCaptura.setDescripcion(this._EdtKB.getText().toString().trim());
                datosCaptura.setRuta(this._Path4);
                datosCaptura.setTipo(_CodigoKB);
                Comun.InsertaDatos(_context, datosCaptura);
                Log.e("FotosKB", "IMAGEN 4");
            }

            //Comun.ListaRegistros(_context);
            EnviaDatos Enviar = new EnviaDatos(_context,null,null);
            Enviar.EnviaDatosImagenes();
        }catch (Exception e){
            Log.e("FotosKB", "Guarda Datos " + e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                _numElementos++;
                switch (requestCode) {
                    case 1:
                        _Imagen1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        _Imagen1.setImageURI(path);
                        _Path1 = getRealPathFromURI(path);
                        break;
                    case 2:
                        _Imagen2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        _Imagen2.setImageURI(path);
                        _Path2 = getRealPathFromURI(path);
                        break;
                    case 3:
                        _Imagen3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        _Imagen3.setImageURI(path);
                        _Path3 = getRealPathFromURI(path);
                        break;
                    case 4:
                        _Imagen4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        _Imagen4.setImageURI(path);
                        _Path4 = getRealPathFromURI(path);
                        break;
                }

            }
        }catch (Exception e){
            Log.e("FotosKB", e.toString());
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = _context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
