import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.task_6graphslocaldb.roomDb.MyAppDatabase
import com.example.task_6graphslocaldb.roomDb.MyRepository
import com.example.task_6graphslocaldb.roomDb.NameEntity
import com.example.task_6graphslocaldb.roomDb.ValueEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope

@OptIn(DelicateCoroutinesApi::class)
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MyAppDatabase.getDatabase(application)
    private val repository: MyRepository
    val namesLiveData: LiveData<List<NameEntity>>
    val names: LiveData<List<NameEntity>>
        get() = this.namesLiveData

    init {
        val database = MyAppDatabase.getDatabase(application)
        repository = MyRepository(database.nameDao(), database.valueDao())
        namesLiveData = repository.namesLiveData
    }
    fun insertName(name: NameEntity) {
    GlobalScope.launch {
        try {
            repository.insertName(name)

        } catch (e: Exception) {
            // Handle the exception here, e.g., log it or show an error message to the user.
        }
    }
}

    fun updateName(nameEntity: NameEntity) {
        GlobalScope.launch {
            try {
                repository.updateName(nameEntity)
            } catch (e: Exception) {
                // Handle the exception here, e.g., log it or show an error message to the user.
            }
        }
    }

    fun insertValue(valueEntity: ValueEntity) {
        GlobalScope.launch {
            try {
                repository.insertValue(valueEntity)
            } catch (e: Exception) {
                // Handle the exception here, e.g., log it or show an error message to the user.
            }
        }
    }
    fun deleteNameAndValues(nameEntity: NameEntity) {
        GlobalScope.launch {
            try {
                repository.deleteNameAndValues(nameEntity)
            } catch (e: Exception) {
                // Handle the exception here, e.g., log it or show an error message to the user.
            }
        }
    }

    suspend fun getNameEntityById(id: Long): NameEntity? {
        return repository.getNameEntityById(id)
    }

    // Function to get values associated with a list ID
    suspend fun getValuesForList(listId: Long): List<ValueEntity> {
        return repository.getValuesForList(listId)
    }




}
