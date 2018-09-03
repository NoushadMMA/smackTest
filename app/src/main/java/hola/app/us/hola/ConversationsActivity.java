package hola.app.us.hola;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jxmpp.jid.Jid;

import java.util.Collection;
import java.util.Random;

import hola.app.us.hola.XMPP.OnLoggedIn;
import hola.app.us.hola.XMPP.OnRosterListener;
import hola.app.us.hola.XMPP.OnStanzaListener;
import hola.app.us.hola.databinding.ActivityConversationsBinding;

public class ConversationsActivity extends AppCompatActivity implements  OnLoggedIn, OnRosterListener, OnStanzaListener, XMPP.OnConnected{

    private static final String TAG = "ConversationsActivity";

    private ActivityConversationsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversations);
        XMPP xmpp = new XMPP();
        xmpp.setOnConnectedListener(this);
        xmpp.setOnLoggedInListener(this);
        xmpp.setOnStanzaListener(this);
        xmpp.setOnRosterListener(this);
        xmpp.openConnection();
    }

    @Override
    public void onLoggedIn(boolean isloggedIn) {
        Log.d(TAG, "logged in");
    }

    @Override
    public void entriesAdded(Collection<Jid> addresses) {
        Log.d(TAG, "entriesAdded");
    }

    @Override
    public void entriesUpdated(Collection<Jid> addresses) {
        Log.d(TAG, "entriesUpdated");
    }

    @Override
    public void entriesDeleted(Collection<Jid> addresses) {
        Log.d(TAG, "entriesDeleted");
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d(TAG, "presenceChanged");
    }

    @Override
    public void processStanza(Stanza packet) {
        Log.d(TAG, "processStanza");

        if(!((Message)packet).getBody().isEmpty()) {
            PendingIntent pi = PendingIntent.getActivity(this, 0,
                    new Intent(this, ConversationsActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("HOLA")
                    .setContentText(((Message) packet).getBody())
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setShowWhen(true)
                    .setColor(Color.RED)
                    .setLocalOnly(true)
                    .build();

            NotificationManagerCompat.from(this)
                    .notify(new Random().nextInt(), notification);

        }
    }

    @Override
    public void onConnectionEstablished(boolean isConnectionEstablished) {
        Log.d(TAG, "onConnectionEstablished");
    }
}