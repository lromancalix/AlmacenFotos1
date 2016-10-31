package Utilerias;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Deintec on 07/10/2016.
 */

public class Comun {
    private static ProgressDialog _ProgressDialog;
    int _intProgress;

    public static Context context;
    public static void mIniciaTarea(Context context){
        _ProgressDialog = ProgressDialog.show(context,"","Espere un momento",true);
        _ProgressDialog.setCancelable(true);
    }

    //funcion para convertir la imagen
    public static String bitmapToBase64(String ruta){
        File file=new File(ruta);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth=640;
        options.outHeight=480;
        options.inSampleSize = 1;
        final Bitmap bitmap = BitmapFactory.decodeFile(ruta,options);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70, baos);
        byte [] arr=baos.toByteArray();
        //String result=Base64.encodoString
        String result=android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
        return result;
    }

    public static boolean isEmpty(String texto){
        Boolean valor = true;
        if(texto==null) valor = false;
        if(texto.isEmpty()) valor = false;
        return valor;
    }

    public static void MessageBox(Context ctx, String texto){
        Toast.makeText(ctx,texto,Toast.LENGTH_LONG).show();
    }

    public  static void InsertaDatos(Context ctx, DatosCaptura Datos){
        try {
            DatosBDHelper bdHelper = new DatosBDHelper(ctx);
            bdHelper._InsertaDatos(Datos);
        }catch (Exception e){
            Log.e("FotosKB","InsertaDatos " + e.toString());
        }
    }

    public static void ListaRegistros(Context ctx){

        try {
            DatosBDHelper bdHelper = new DatosBDHelper(ctx);
            Cursor cursor = bdHelper._RecuperaDatos();
            if(cursor.getCount()>0){
                Log.e("FotosKB", "calix: "+ String.valueOf(cursor.getCount()) );
                cursor.moveToFirst();
                //Recorremos el cursor hasta que no haya más registros
                while(cursor.moveToNext()) {

                    DatosCaptura datosCaptura = new DatosCaptura();
                    datosCaptura.setTipo(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._tipo)));
                    datosCaptura.setDescripcion(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._kb)));
                    datosCaptura.setRuta(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._ruta)));
                    datosCaptura.setID(cursor.getString(cursor.getColumnIndex(AlmacenBD.TablaDatos._idElemento)));
                   // EnviarDatos(datosCaptura);

                }
            }


        }catch (Exception e){
            Log.e("FotosKB","ListaRegistros " + e.toString());
        }
    }
    public static int EnviosPendientes(Context ctx){
        int cont = 0;
        try {
            DatosBDHelper bdHelper = new DatosBDHelper(ctx);
            Cursor cursor = bdHelper._RecuperaDatos();
            cont= cursor.getCount();
            Log.e("FotosKB","cont " + String.valueOf(cursor.getCount()));
        }catch (Exception e){
            Log.e("FotosKB","ListaRegistros " + e.toString());
        }
        return cont;
    }


    public  static void BorraDatos(Context ctx, DatosCaptura Datos){
        try {
            DatosBDHelper bdHelper = new DatosBDHelper(ctx);
            bdHelper._BorraRegistro(Datos);
        }catch (Exception e){
            Log.e("FotosKB","InsertaDatos " + e.toString());
        }
    }
    public static Boolean isOnlineNet() {

        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            return (urlc.getResponseCode() == 200);
           } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
