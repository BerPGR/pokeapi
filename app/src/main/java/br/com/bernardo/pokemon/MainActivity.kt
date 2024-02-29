package br.com.bernardo.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import br.com.bernardo.pokemon.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendButton: Button = findViewById(R.id.button_send)
        var editPokename = findViewById<EditText>(R.id.edit_pokename)

        sendButton.setOnClickListener {
            var texto = editPokename.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                getPokemonByName(texto)
            }
        }
    }



    private suspend fun getPokemonByName(name: String) {
        var pokemon: Response<Pokemon>? = null

        try {
            val pokemonApi = retrofit.create(PokemonAPI::class.java)
            pokemon = pokemonApi.getPokemonByName(name)
        }
        catch (e: Exception) {
            print(e.toString())
        }

        if (pokemon != null) {
            if (pokemon.isSuccessful) {
                var result = pokemon.body()
                val image = Picasso.get().load(result?.sprites?.front_default).get()

                withContext(Dispatchers.Main) {
                    findViewById<ImageView>(R.id.image_pokemon).setImageBitmap(image)
                    findViewById<TextView>(R.id.txt_pokemon_name).text = result?.name
                }
            }
        }
    }
}