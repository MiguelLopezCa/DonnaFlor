package data_base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_helper extends SQLiteOpenHelper {
    public db_helper(@Nullable Context context) {
        //Constructor que genera instancia de la BD
        super(context, Constantes_db.NOMBRE_BASE_DATOS , null, Constantes_db.VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creación de tablas

        sqLiteDatabase.execSQL(Constantes_db.CREAR_TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL(Constantes_db.CREAR_TABLA_VENTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Actualización de la BD, eliminando y creando tablas

        sqLiteDatabase.execSQL(Constantes_db.ELIMINAR_TABLA_VENTAS);
        sqLiteDatabase.execSQL(Constantes_db.ELIMINAR_TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL(Constantes_db.ELIMINAR_TABLA_VENTAS_PRODUCTOS);

        sqLiteDatabase.execSQL(Constantes_db.CREAR_TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL(Constantes_db.CREAR_TABLA_VENTAS);
        sqLiteDatabase.execSQL(Constantes_db.CREAR_TABLA_VENTAS_PRODUCTOS);
    }
}
