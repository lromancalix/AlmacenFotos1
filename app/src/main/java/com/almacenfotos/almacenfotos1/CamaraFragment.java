package com.almacenfotos.almacenfotos1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CamaraFragment extends Fragment {

    View _rootView;
    Context _context;
    ImageView _Imagen;
    Bitmap _ImagenBitmap;
    FloatingActionButton _btnFlonte;
    ImageButton _btnEnviar;

    private static int _CapturarImagen = 1;
    private static int _SeleccionarImagen = 2;

    public CamaraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _context = container.getContext();
        _rootView = inflater.inflate(R.layout.fragment_camara,container,false);
        _mInicializaElementos();
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return _rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_camarafragment,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_Camara:
                _mAbrirCamara();
                return false;
            case R.id.action_Enviar:
                Toast.makeText(getActivity(),"enviar",Toast.LENGTH_LONG).show();
                return false;
            default:
                break;
        }
        return false;
    }


    private void _mInicializaElementos(){
        try{
            _btnEnviar = (ImageButton)
                    _rootView.findViewById(R.id.btnEnviar);

            _btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Toast.makeText(getActivity(),"Enviando...",Toast.LENGTH_LONG).show();
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
            _Imagen = (ImageView)_rootView.findViewById(R.id.imgCamara);
            _Imagen.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            _ImagenBitmap = ((BitmapDrawable) _Imagen.getDrawable()).getBitmap();

        }
    }
}
