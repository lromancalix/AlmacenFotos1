package Utilerias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Deintec on 01/10/2016.
 */

public class DatosBDHelper extends SQLiteOpenHelper {

    public static final int _Version = 1;
    public static final String _NombreBD = "Almacen.db";

    public DatosBDHelper(Context context) {
        super(context, _NombreBD, null, _Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + AlmacenBD.TablaDatos._nombreTabla
                + " ( "
                + AlmacenBD.TablaDatos._idElemento + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlmacenBD.TablaDatos._descripcion + " TEXT NOT NULL,"
                + AlmacenBD.TablaDatos._estatus + " INTEGER NOT NULL,"
                + AlmacenBD.TablaDatos._tipo + " TEXT NOT NULL,"
                + AlmacenBD.TablaDatos._ruta + " TEXT NOT NULL,"
                + AlmacenBD.TablaDatos._archivoBytes + " TEXT NOT NULL"
               // + "UNIQUE(" + AlmacenBD.TablaDatos._idElemento + ")"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "CREATE TABLE IF NOT EXISTS " + AlmacenBD.TablaDatos._nombreTabla;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean _InsertaDatos(DatosCaptura datos){
        boolean regresa = true;
        try{
            ContentValues valor = new ContentValues();
            valor.put(AlmacenBD.TablaDatos._archivoBytes, datos.getArray());
            valor.put(AlmacenBD.TablaDatos._descripcion, datos.getDescripcion());
            valor.put(AlmacenBD.TablaDatos._estatus, datos.getEstatus());
            valor.put(AlmacenBD.TablaDatos._ruta, datos.getRuta());
            valor.put(AlmacenBD.TablaDatos._tipo, datos.getTipo());

            this.getWritableDatabase().insert(AlmacenBD.TablaDatos._nombreTabla, null, valor);
            this.close();

        }catch (Exception e){
            regresa = false;
            Log.e("FotosKB",e.toString());
        }
        return  regresa;
    }

    public Cursor _SelectDatos(){
        Cursor cursor = null;
        this.getReadableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + AlmacenBD.TablaDatos._nombreTabla;
        try{
            cursor = db.rawQuery(sql,null);
        }catch (Exception e){
            Log.e("FotosKB",e.toString());
        }

        return  cursor;
    }
}
