package hola.app.us.hola;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smackx.delay.packet.DelayInformation;
import org.jxmpp.jid.Jid;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hola.app.us.hola.XMPP.OnRosterListener;
import hola.app.us.hola.XMPP.OnStanzaListener;
import hola.app.us.hola.databinding.ActivityConversationsBinding;
import hola.app.us.hola.sqlite.AppDatabase;
import hola.app.us.hola.xmpp_extensions.MessageTypeInformation;

public class ConversationsActivity extends AppCompatActivity implements OnRosterListener, OnStanzaListener, XMPP.OnConnected, ConversationsListAdapter.onClickedItemListener {

    private static final String TAG = "ConversationsActivity";
    private static ConversationsActivity instance;
    private List<RosterEntry> mRosterEntries = new ArrayList<>();
    private ActivityConversationsBinding mBinding;

    public static ConversationsActivity getInstance() {
        return instance;
    }

    public static long getTimestamp(Stanza packet) {
        DelayInformation delay = packet.getExtension("delay", "urn:xmpp:delay");
        if (delay != null) {
            return new Timestamp(delay.getStamp().getTime()).getTime();
        }
        return 0;
    }

    //TODO
    public static int getMessageType(Stanza packet) {
        MessageTypeInformation messageType = packet.getExtension("message_type", "urn:xmpp:message_type");
        if (messageType != null) {
            return 0;
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversations);
        Intent intent = new Intent(this, XMPP.class);
        intent.putExtra("username", "971555532943");
        intent.putExtra("password", "test1234");
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
                for (Iterator iterator = roster.getEntries().iterator(); iterator.hasNext(); ) {
                    mRosterEntries.add((RosterEntry) iterator.next());
                }
                ConversationsListAdapter conversationListadapter = new ConversationsListAdapter(mRosterEntries);
                conversationListadapter.setOnClickedItemListener(ConversationsActivity.this);
                mBinding.conversationRecycler.setAdapter(conversationListadapter);
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

        if (((Message) packet).getBody() != null && !((Message) packet).getBody().isEmpty()) {
            Jid counterPart = packet.getFrom();
            long sentTime = getTimestamp(packet);
            long receivedTimeStamp = System.currentTimeMillis();
            String messageBody = ((Message) packet).getBody();
            String type = ((Message) packet).getType().toString();
            int messageType = getMessageType(packet);
            hola.app.us.hola.models.Message message = new hola.app.us.hola.models.Message(sentTime, messageBody, counterPart.toString(), receivedTimeStamp, 0, messageType, type);
            AppDatabase.getInstance(getApplicationContext()).chatHistory().insertNewChatMessage(message);
        }
    }

    @Override
    public void onConnectionEstablished(boolean isConnectionEstablished) {
        Log.d(TAG, "onConnectionEstablished");
    }

    @Override
    public void onClickedConversationItem(RosterEntry entry) {
        Toast.makeText(instance, "toas", Toast.LENGTH_SHORT).show();
        /*try {
            MamManager mamManager = MamManager.getInstanceFor(connection);
            boolean isSupported = mamManager.isSupportedByServer();
            if (isSupported) {
                MamManager.MamQueryResult mamQueryResult = mamManager.queryArchive(500);
                List<Forwarded> forwardedMessages = mamQueryResult.forwardedMessages;
                Forwarded d = forwardedMessages.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}