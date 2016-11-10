package Utilerias;

import android.provider.BaseColumns;

/**
 * Created by Deintec on 01/10/2016. xD
 */

public class AlmacenBD  {

    public static abstract  class TablaDatos implements BaseColumns {
        /**
         * Nombre de la tabla
         */
        public static final String _nombreTabla ="tblDatos";
        /**
         * _idElemento: Descrpcion ingresada por el usuario.
         * Tipo de datos: Integer.
         * Nombre de la columna: _id.
         * Detalle: Identificador unico autoIncrement.
         */
        public static final String _idElemento = "_idElemento";
        /**
         * _descripcion: Descrpcion ingresada por el usuario.
         * Tipo de datos: Text.
         * Nombre de la columna: descripcion
         */
        public static final String _descripcion = "descripcion";
        /**
         * _estatus: Identifica si los datos ya fueron o no enviados.
         * Tipo de datos: Integer.
         * Nombre de la columna: 0 indica que no se han enviado los datos,
         *                       1 indica que ya se enviaron los datos.
         */
        public static final String _estatus = "estatus";
        /**
         * _tipo: tipo de registro.
         * Tipo de datos: Text.
         * Nombre de la columna: Define si el registro es foto o video.
         */
        public static final String _tipo = "tipo";
        /**
         * _ruta: ruta donde se guardado el archivo.
         * Tipo de datos: Text.
         * Nombre de la columna: Define donde se guardo el video o la fotografia.
         */
        public static final String _ruta = "ruta";
        /**
         * _archivoBytes: archivo descompuesto en array de 32 bytes.
         * Tipo de datos: Text.
         * Nombre de la columna: archivoBytes.
         */
        public static final String _archivoBytes = "archivoBytes";
    }
}
