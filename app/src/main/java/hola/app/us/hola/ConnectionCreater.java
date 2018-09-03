package hola.app.us.hola;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

class ConnectionCreater implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {

        switch (tag) {
            case ConnectionSyncJob.TAG:
                return new ConnectionSyncJob();
            default:
                return null;
        }
    }
}
