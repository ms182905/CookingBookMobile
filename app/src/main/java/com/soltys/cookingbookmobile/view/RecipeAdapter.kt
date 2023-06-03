package com.soltys.cookingbookmobile.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soltys.cookingbookmobile.R
import com.soltys.cookingbookmobile.model.Recipe
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.logging.Handler

class RecipeAdapter(private val context : Activity, private val arrayList : ArrayList<Recipe>) : ArrayAdapter<Recipe>(
    context, R.layout.list_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)

        val imageView : ImageView = view.findViewById(R.id.recipe_picture)
        val recipeName : TextView = view.findViewById(R.id.recipeName)
        val description : TextView = view.findViewById(R.id.recipeDescription)

        val myExecutor = Executors.newSingleThreadExecutor()
        var bitmap : Bitmap? = null

        myExecutor.execute {
            bitmap = (mLoad("https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/427506.jpg"))
        }

        imageView.setImageBitmap(bitmap)
        recipeName.text = arrayList[position].name
        description.text = arrayList[position].description

        return view
    }

    // Function to establish connection and load image
    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            //Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
        }
        return null
    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
            return URL(string)
    }
}