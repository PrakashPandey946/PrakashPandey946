import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "WeatherPref"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val LATITUDE = Pair("latitude", "")
    private val LONGITUDE = Pair("longitude", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var lat: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(LATITUDE.first, LATITUDE.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(LATITUDE.first, value)
        }

    var long: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(LONGITUDE.first, LONGITUDE.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(LONGITUDE.first, value)
        }
}