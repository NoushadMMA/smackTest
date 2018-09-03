package hola.app.us.hola;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jxmpp.jid.Jid;

import java.util.Collection;
import java.util.Random;

import hola.app.us.hola.XMPP.OnLoggedIn;
import hola.app.us.hola.XMPP.OnRosterListener;
import hola.app.us.hola.XMPP.OnStanzaListener;
import hola.app.us.hola.databinding.ActivityConversationsBinding;

public class ConversationsActivity extends AppCompatActivity implements   OnRosterListener, OnStanzaListener, XMPP.OnConnected{

    private static final String TAG = "ConversationsActivity";
    private ActivityConversationsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversations);
        Intent intent = new Intent(this, XMPP.class);
        intent.putExtra("username","971555532943");
        intent.putExtra("password","test1234");
        intent.setAction("keepConnection");
        XMPP xmpp = new XMPP(this);
        xmpp.setOnConnectedListener(this);
        xmpp.setOnStanzaListener(this);
        xmpp.setOnRosterListener(this);
        XMPP.enqueueWork(this, XMPP.class, 102, intent);
//        xmpp.openConnection();
    }


    @Override
    public void entriesAdded(Collection<Jid> addresses, final Roster roster) {
        Log.d(TAG, "entriesAdded");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBinding.conversationRecycler.setLayoutManager(new LinearLayoutManager(ConversationsActivity.this));
                mBinding.conversationRecycler.setAdapter(new ConversationsListAdapter((Collection) roster.getEntries()));
            }
        });

    }

    @Override
    public void entriesUpdated(Collection<Jid> addresses, Roster roster) {
        Log.d(TAG, "entriesUpdated");
    }

    @Override
    public void entriesDeleted(Collection<Jid> addresses, Roster roster) {
        Log.d(TAG, "entriesDeleted");
    }

    @Override
    public void presenceChanged(Presence presence, Roster roster) {
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