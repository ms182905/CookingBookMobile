package com.soltys.cookingbookmobile.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.soltys.cookingbookmobile.model.Recipe

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

  override fun onCreate(db: SQLiteDatabase) {

    val query =
        ("CREATE TABLE " +
            TABLE_NAME +
            " (" +
            ID_COL +
            " INTEGER PRIMARY KEY, " +
            NAME_COl +
            " TEXT, " +
            DESCRIPTION_COL +
            " TEXT, " +
            PICTURE_URL_COL +
            " TEXT, " +
            NOTE_COL +
            " TEXT" +
            ")")

    db.execSQL(query)
  }

  override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
    db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    onCreate(db)
  }

  fun addRecipe(id: String, name: String, description: String, picture_url: String) {
    val values = ContentValues()
    values.put(ID_COL, id)
    values.put(NAME_COl, name)
    values.put(DESCRIPTION_COL, description)
    values.put(PICTURE_URL_COL, picture_url)

    val db = this.writableDatabase
    db.insert(TABLE_NAME, null, values)
    db.close()
  }

  @SuppressLint("Range")
  fun getRecipes(): List<Recipe> {
    val db = this.readableDatabase
    val recipeList = ArrayList<Recipe>()

    val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    if (cursor.moveToFirst()) {
      recipeList.add(
          Recipe(
              cursor.getString(cursor.getColumnIndex(NAME_COl)),
              cursor.getString(cursor.getColumnIndex(PICTURE_URL_COL)),
              cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)),
              cursor.getString(cursor.getColumnIndex(ID_COL)).toInt(),
          ))

      while (cursor.moveToNext()) {
        recipeList.add(
            Recipe(
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

  fun isInDatabase(id: String): Boolean {
    val db = this.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE id='$id'", null)

    if (cursor.moveToFirst()) {
      cursor.close()
      return true
    }

    cursor.close()
    return false
  }

  fun removeRecipeFromDatabase(id: String) {
    val db = this.readableDatabase
    db.delete(TABLE_NAME, "id = ?", arrayOf(id))
  }

  fun updateRecipeNotes(id: String, note: String) {
    if (this.isInDatabase(id)) {
      val db = this.readableDatabase
      val values = ContentValues()
      values.put(NOTE_COL, note)
      db.update(TABLE_NAME, values, "id = ?", arrayOf(id))
    }
  }

  @SuppressLint("Range")
  fun getRecipeNote(id: String): String {
    val db = this.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE id='$id'", null)

    if (cursor.moveToFirst() && cursor.getString(cursor.getColumnIndex(NOTE_COL)) != null) {
      val output = cursor.getString(cursor.getColumnIndex(NOTE_COL))
      cursor.close()
      return output
    }

    cursor.close()
    return ""
  }

  companion object {

    private const val DATABASE_NAME = "RECIPE_STORAGE"

    private const val DATABASE_VERSION = 3

    const val TABLE_NAME = "recipe_table"

    const val ID_COL = "id"

    const val NAME_COl = "name"

    const val DESCRIPTION_COL = "description"

    const val PICTURE_URL_COL = "picture_url"

    const val NOTE_COL = "note"
  }
}
