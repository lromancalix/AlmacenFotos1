package Utilerias;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.lang.Object;

/**
 * Created by JPMB on 24/10/2016.
 */

public class EnviaDatos {
    public static Context _ctx;
    public static ImageView _Imagen;
    private static ProgressDialog _ProgressDialog;
    static boolean  _isconexion;
    public static boolean _datosEnviados;
    public static TextView textViewOptional;
    public  static String res;

    private   static final String _NAMESPACE = "http://www.kalisch.net/";
    //private final String NAMESPACE="www.kalisch.net/";
    //private static   final String _URL = "http://216.150.43.66//WebServiceExterno/service.asmx";
    private static   final String _URL = "http://38.111.129.232//WebServiceExterno/service.asmx";
    static final String _SOAPACTION = "http://www.kalisch.net/registrafoto";
    static final String _METHOD = "registrafoto";

    public EnviaDatos(Context ctx, ImageView img, TextView textViewOptional) {
        this._ctx = ctx;
        this._Imagen=img;
        this.textViewOptional = textViewOptional;
    }

    public  void EnviaDatosImagenes(){
        Log.e("FotosKB","inicia");
        AsyncCallWS enviar = new  AsyncCallWS();
        enviar.execute();
    }

    public static void mIniciaTarea(Context context){
        _ProgressDialog = ProgressDialog.show(context,"","Espere un momento",true);
        //_ProgressDialog.setCancelable(true);
    }

    private static void EnvioFoto (DatosCaptura datosCaptura)
    {
        Log.e("FotosKB","EnvioFoto " + datosCaptura.getDescripcion() );
        String imagen64 = Comun.bitmapToBase64(datosCaptura.getRuta());
        String kb = datosCaptura.getDescripcion();
        int tipo = Integer.parseInt(datosCaptura.getTipo());

        SoapObject request = new SoapObject(_NAMESPACE, _METHOD);
        PropertyInfo base_64 = new PropertyInfo();
        base_64.setName("base64");
        base_64.setValue(imagen64);
        base_64.setType(String.class);

        PropertyInfo kabe =new PropertyInfo();
        kabe.setName("Registro");
        kabe.setValue(kb);
        kabe.setType(String.class);

        PropertyInfo _type =new PropertyInfo();
        _type.setName("tipo");
        _type.setValue(tipo);
        _type.setType(int.class);

        request.addProperty(base_64);
        request.addProperty(kabe);
        request.addProperty(_type);
        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(_URL,10000);

        transporte.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        try
        {
            transporte.debug=true;
            transporte.call(_SOAPACTION, envelope);

            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            res = resultado_xml.toString();
            Log.e("FotosKB","Resultado de envio: " + res);
            if(res.equals("Guardado")){
                Comun.BorraDatos(_ctx,datosCaptura);
                _datosEnviados = true ;
            }else{
                _datosEnviados = false ;
            }

        }
        catch (Exception e)
        {

            Log.e("FotosKB",transporte.requestDump);
            // String mensaje = transporte.requestDump;
            Log.e("FotosKB","Resultado de envio: " + e.toString());
            // tv.setText(e.getMessage());
        }


    }


    //     Tarea de envio de foto//
    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            _isconexion =Comun.isOnlineNet();

            if(_isconexion!=true){return null;}
            Log.e("FotosKB", "doInBackground");
            try {
                DatosBDHelper bdHelper = new DatosBDHelper(_ctx);
                Cursor cursor = bdHelper._RecuperaDatos();
                Log.e("FotosKB", "Cursor " + String.valueOf(cursor.getCount()));
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        DatosCaptura datosCaptura = new DatosCaptura();
                        datosCaptura.setTipo(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._tipo)));
                        datosCaptura.setDescripcion(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._kb)));
                        datosCaptura.setRuta(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._ruta)));
                        datosCaptura.setID(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._idElemento)));
                        Log.e("FotosKB", datosCaptura.getDescripcion());
                        EnvioFoto(datosCaptura);
                    }while(cursor.moveToNext());
                }
            }catch (Exception e){
                Log.e("FotosKB","ListaRegistros " + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.e("FotosKB", "onPostExecute");
            if(_isconexion!=true){
                Comun.MessageBox(_ctx,"Sin Conexion a internet");
            }
            _ProgressDialog.dismiss();
            if(_Imagen != null)  _Imagen.setImageDrawable(null);
            if(_datosEnviados){
                Comun.MessageBox(_ctx,res);
            }else if(res!=null){
                Comun.MessageBox(_ctx,res);
            }
            if(textViewOptional !=null) {
               textViewOptional.setText("Registros Pendientes: "+String.valueOf(Comun.EnviosPendientes(_ctx)));
            }

        }

        @Override
        protected void onPreExecute() {
            Log.e("FotosKB", "onPreExecute");
            _ProgressDialog = ProgressDialog.show(_ctx,"","Espere un momento",true);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.e("s", "onProgressUpdate");
        }
        protected void onProgeesStop()
        {
            Log.e("s","onProgeesStop");

        }
    }


}
