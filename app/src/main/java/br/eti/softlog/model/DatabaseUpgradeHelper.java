package br.eti.softlog.model;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Paulo Sérgio Alves on 2018/04/10.
 */

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {

    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        List<Migration> migrations = getMigrations();

        // Only run migrations past the old version
        for (Migration migration : migrations) {
            if (oldVersion < migration.getVersion()) {
                migration.runMigration(db);
            }
        }
    }

    private List<Migration> getMigrations() {
        List<Migration> migrations = new ArrayList<>();
        migrations.add(new MigrationV13());
        migrations.add(new MigrationV14());

        // Sorting just to be safe, in case other people add migrations in the wrong order.
//        Comparator<Migration> migrationComparator = new Comparator<Migration>() {
//            @Override
//            public int compare(Migration m1, Migration m2) {
//                return m1.getVersion().compareTo(m2.getVersion());
//            }
//        };
//        Collections.sort(migrations, migrationComparator);

        return migrations;
    }

    private static class MigrationV13 implements Migration {

        @Override
        public Integer getVersion() {
            return 13;
        }

        @Override
        public void runMigration(Database db) {
            //Adding new table
            //Log.d("Migracao","21");
            db.execSQL("ALTER TABLE " + NotasFiscaisDao.TABLENAME + " ADD COLUMN " + NotasFiscaisDao.Properties.IdOcorrencia.columnName + " INTEGER;");
            OcorrenciasDao.createTable(db,false);
            //UsuarioDao.createTable(db, false);
        }
    }


    private static class MigrationV14 implements Migration {

        @Override
        public Integer getVersion() {
            return 14;
        }

        @Override
        public void runMigration(Database db) {
            // Add new column to user table
            //Log.d("Migracao","22");

            db.execSQL("ALTER TABLE " + UsuariosSistemaDao.TABLENAME + " ADD COLUMN " + UsuariosSistemaDao.Properties.Senha.columnName + " TEXT");
        }
    }



    private interface Migration {
        Integer getVersion();

        void runMigration(Database db);
    }
}