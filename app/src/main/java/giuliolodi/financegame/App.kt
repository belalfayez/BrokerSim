package giuliolodi.financegame

import android.app.Application
import giuliolodi.financegame.di.component.AppComponent
import giuliolodi.financegame.di.component.DaggerAppComponent
import giuliolodi.financegame.di.module.AppModule
import giuliolodi.financegame.model.StockDb
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        mAppComponent.inject(this)

        initRealm()
    }

    fun initRealm() {
        Realm.init(this)
        var defaultList: List<String> = listOf("GOOG", "GOOGL", "INTC", "AMZN", "AAPL", "YHOO")
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .initialData { realm ->
                    for (item in defaultList)
                        realm.createObject(StockDb::class.java, item)
                }
                .deleteRealmIfMigrationNeeded()
                .build())
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

}