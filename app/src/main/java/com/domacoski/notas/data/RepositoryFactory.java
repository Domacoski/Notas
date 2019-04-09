package com.domacoski.notas.data;

import android.content.Context;

import com.domacoski.notas.data.sharedpreferences.SharedPreferencesRepositoryImpl;
import com.domacoski.notas.data.sqlite.SqliteRepositoryImpl;

public class RepositoryFactory {

    public static RepositoryFactory instance;


    public static RepositoryFactory getInstance() {
        if(null == instance){
            instance = new RepositoryFactory();
        }
        return instance;
    }


    private Repository getSharedPreferencesRepositoryImpl(final Context context){
        return new SharedPreferencesRepositoryImpl(context);
    }
    private Repository getSqliteImpl(final Context context){
        return new SqliteRepositoryImpl( context );
    }

    public Repository getRepository(final Context context, boolean isSQlite){
        if(isSQlite){
            return getSqliteImpl( context );
        }else{
            return getSharedPreferencesRepositoryImpl( context );
        }
    }
}
