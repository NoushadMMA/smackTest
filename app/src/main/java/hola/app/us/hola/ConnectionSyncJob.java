package hola.app.us.hola;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.support.v4.app.JobIntentService.enqueueWork;

class ConnectionSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

//        XMPP.getInstance().openConnection();/*
//                    getContext().sendBroadcast(new Intent("liveapp.loggedin"));
//                    Toast.makeText(getContext(), "sender: ", Toast.LENGTH_SHORT).show();
                /*} catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/
        return Result.SUCCESS;
    }

    static void schedulePeriodic() {
       int mLastJobId = new JobRequest.Builder(TAG)
               .setPeriodic(TimeUnit.MINUTES.toMillis(1), TimeUnit.MINUTES.toMillis(1))
                .setRequiresDeviceIdle(true)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

}
