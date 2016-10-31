package Utilerias;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Deintec on 21/09/2016.
 */

public class Directorio {
    private  static final  String _Ruta =Environment.getExternalStorageDirectory().getAbsolutePath();// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";
    private static final String _CarpetaPrincipal = "/FotosKB";
    private static final String _CarpetaFotos = "/Fotos";
    private static final String _CarpetaVideos = "/Videos";


    public static boolean _ValidaDirectorio(){
        Log.i("calix","validando");
        _Directorio(_Ruta + _CarpetaPrincipal);
        _Directorio(_Ruta + _CarpetaPrincipal  + _CarpetaFotos);
        _Directorio(_Ruta + _CarpetaPrincipal  + _CarpetaVideos);
        return true;
    }

    public static String _getRutaFotos(){
        _ValidaDirectorio();
        return _Ruta + _CarpetaPrincipal + _CarpetaFotos + "/" ;
    }

    public static String _getRutaVideo(){
        return _Ruta + _CarpetaPrincipal + _CarpetaVideos;
    }

    /**
     60  * Metodo privado que genera un codigo unico segun la hora y fecha del sistema
     61  * @return photoCode
     62  * */
     @SuppressLint("SimpleDateFormat")
     public static String _getNombreImagen(){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
          String date = dateFormat.format(new Date() );
          String photoCode = "pic_" + date + ".jpg";
          return photoCode;
     }

    private static boolean _Directorio(String ruta){
       try {
           /*
           this.mArchivo = new File(this.mRuta + this.mCARPETA_PRINCIPAL);
           if( !this.mArchivo.isDirectory() ){
               File nuevaCarpeta = new File(this.mRuta+this.mCARPETA_PRINCIPAL);
               nuevaCarpeta.mkdir();
           }*/

           File dir = new File(ruta);
           if (dir.isDirectory()) {
               Log.i("calix","existe " + ruta);
               return true;
           } else {
               Log.e("calix", ruta);
               _CreaDirectorio(dir);
           }
       }catch (Exception e){
           Log.e("calix", "creando "+ e.toString());
       }
        return true;
    }


    /**
     * Crear las carpetas necesarias para el sistema
     * @param nuevaCarpeta
     */
    private static void _CreaDirectorio(File nuevaCarpeta){
        nuevaCarpeta.mkdir();
        if (nuevaCarpeta.isDirectory()) {
            Log.i("calix","carpeta creada por fin " );
        }

    }

    public static boolean _validaExistenciaImagen(File foto , Context _context){

        MediaScannerConnection.scanFile( _context ,
                new String[] { foto.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
        return true;
    }

}
