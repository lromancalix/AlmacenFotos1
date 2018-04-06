package com.almacenfotos.almacenfotos1;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import Utilerias.Comun;
import Utilerias.EnviaDatos;
/**
 * A simple {@link Fragment} subclass.
 */
public class EnvioPendienteFragment extends Fragment {

    View _rootView;
    Context _context;
    TextView _edtRegistrosPendientes;

    public EnvioPendienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _context = container.getContext();
        _rootView = inflater.inflate(R.layout.fragment_envio_pendiente,container,false);
        mInicializaElementos();
        return _rootView;
    }

    private void mInicializaElementos(){
     this._edtRegistrosPendientes = (TextView) _rootView.findViewById(R.id.edtPendientes);
     this._edtRegistrosPendientes.setText("Registros Pendientes: "+String.valueOf(Comun.EnviosPendientes(_context)));
     EnviaDatos enviaDatos = new EnviaDatos(_context,null, this._edtRegistrosPendientes);
     enviaDatos.EnviaDatosImagenes();
        this._edtRegistrosPendientes.setText("Registros Pendientes: "+String.valueOf(Comun.EnviosPendientes(_context)));
    }

}
