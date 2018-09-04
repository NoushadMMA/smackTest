package hola.app.us.hola;

import android.app.Application;

import com.evernote.android.job.JobConfig;
import com.evernote.android.job.JobManager;

public class AppClass extends Application {
    private static AppClass instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        JobManager.create(this).addJobCreator(new ConnectionCreater());
//        JobConfig.setAllowSmallerIntervalsForMarshmallow(true);
    }

    public static AppClass getApplicationContextInstance(){
        return instance;
    }
}
