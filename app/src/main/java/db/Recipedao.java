package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public  class Recipedao {
    private static Recipedao recipedao;
    private RecipeOpenhelp recipeOpenhelp;
    public Recipedao(Context context){
       recipeOpenhelp=new RecipeOpenhelp(context);
    }
    public Recipedao getRecipedao(Context context){
        if(recipedao==null){
            recipedao=new Recipedao(context);
        }
        return recipedao;
    }
    public void innest(){
        SQLiteDatabase db = recipeOpenhelp.getWritableDatabase();
    }
}
