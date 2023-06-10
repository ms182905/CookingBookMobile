package com.soltys.cookingbookmobile.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.soltys.cookingbookmobile.model.Recipe

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT, " +
                DESCRIPTION_COL + " TEXT, " +
                PICTURE_URL_COL + " TEXT, " +
                NOTE_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addRecipe(id : String, name : String, description : String, picture_url : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(ID_COL, id)
        values.put(NAME_COl, name)
        values.put(DESCRIPTION_COL, description)
        values.put(PICTURE_URL_COL, picture_url)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    @SuppressLint("Range")
    fun getRecipes(): List<Recipe> {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        val recipeList = ArrayList<Recipe>()

        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        if (cursor.moveToFirst()) {
            recipeList.add(Recipe(
                cursor.getString(cursor.getColumnIndex(NAME_COl)),
                cursor.getString(cursor.getColumnIndex(PICTURE_URL_COL)),
                cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)),
                cursor.getString(cursor.getColumnIndex(ID_COL)).toInt(),
            ))

            while (cursor.moveToNext()) {
                recipeList.add(Recipe(
                    cursor.getString(cursor.getColumnIndex(NAME_COl)),
                    cursor.getString(cursor.getColumnIndex(PICTURE_URL_COL)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)),
                    cursor.getString(cursor.getColumnIndex(ID_COL)).toInt(),
                    ))
            }
        }

        cursor.close()

        return recipeList
    }

    fun isInDatabase(id : String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id='$id'", null)

        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        }

        cursor.close()
        return false
    }

    fun removeRecipeFromDatabase(id : String) {
        val db = this.readableDatabase
        db.delete(TABLE_NAME, "id = ?", arrayOf(id))
    }

    fun updateRecipeNotes(id : String, note : String) {
        if (this.isInDatabase(id)) {
            val db = this.readableDatabase
            val values = ContentValues()

            values.put(NOTE_COL, note)

            db.update(TABLE_NAME, values, "id = ?", arrayOf(id))
        }
    }

    @SuppressLint("Range")
    fun getRecipeNote(id : String) : String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id='$id'", null)

        if (cursor.moveToFirst()) {

            if (cursor.getString(cursor.getColumnIndex(NOTE_COL)) != null)
                return cursor.getString(cursor.getColumnIndex(NOTE_COL))
        }

        return ""
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "RECIPE_STORAGE"

        // below is the variable for database version
        private val DATABASE_VERSION = 3

        // below is the variable for table name
        val TABLE_NAME = "recipe_table"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for age column
        val DESCRIPTION_COL = "description"

        val PICTURE_URL_COL = "picture_url"

        val NOTE_COL = "note"
    }
}
