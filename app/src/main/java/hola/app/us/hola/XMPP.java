package hola.app.us.hola;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class XMPP {


    public static final String HOST = "server.holaapp.us";
    public static final int PORT = 5222;
    public static final String SERVICE = "server.holaapp.us";
    public static final String USERNAME = "971555532943";
    public static final String PASSWORD = "test1234";

    public static XMPP instance;
    Roster roster;
    private XMPPTCPConnection connection;
    private ArrayList<String> messages = new ArrayList<String>();
    private Handler mHandler = new Handler();
    private OnLoggedIn mOnLoggedIn;
    private OnConnected mOnConnected;
    private OnRosterListener mOnRosterListener;
    private RosterListener mRosterListener = new RosterListener() {
        @Override
        public void entriesAdded(Collection<Jid> addresses) {
            mOnRosterListener.entriesAdded(addresses);
        }

        @Override
        public void entriesUpdated(Collection<Jid> addresses) {
            mOnRosterListener.entriesUpdated(addresses);
        }

        @Override
        public void entriesDeleted(Collection<Jid> addresses) {
            mOnRosterListener.entriesDeleted(addresses);
        }

        @Override
        public void presenceChanged(Presence presence) {
            mOnRosterListener.presenceChanged(presence);
        }
    };
    private OnStanzaListener mOnStanzaListener;
    private StanzaListener mStanzaListener = new StanzaListener() {
        @Override
        public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
            mOnStanzaListener.processStanza(packet);
        }
    };

    public static XMPP getInstance() {
        if (instance == null) {
            synchronized (XMPP.class) {
                if (instance == null) {
                    instance = new XMPP();
                }
            }
        }
        return instance;
    }

    private XMPPTCPConnectionConfiguration buildConfiguration() throws XmppStringprepException {
        XMPPTCPConnectionConfiguration.Builder builder =
                XMPPTCPConnectionConfiguration.builder();


        builder.setHost(HOST);
        builder.setPort(PORT);
        builder.setCompressionEnabled(false);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        builder.setSendPresence(true);

        if (Build.VERSION.SDK_INT >= 14) {
            builder.setKeystoreType("AndroidCAStore");
            // config.setTruststorePassword(null);
            builder.setKeystorePath(null);
        } else {
            builder.setKeystoreType("BKS");
            String str = System.getProperty("javax.net.ssl.trustStore");
            if (str == null) {
                str = System.getProperty("java.home") + File.separator + "etc" + File.separator + "security"
                        + File.separator + "cacerts.bks";
            }
            builder.setKeystorePath(str);
        }
        DomainBareJid serviceName = JidCreate.domainBareFrom(HOST);
        builder.setXmppDomain(serviceName);


        return builder.build();
    }

    private XMPPTCPConnection getConnection() {
                Log.d(HOST, "Getting XMPP Connect");
                long l = System.currentTimeMillis();
                try {
                    if (connection != null) {
                        Log.d(HOST, "Connection found, trying to connect");
                        connection.connect();
                    } else {
                        Log.d(HOST, "No Connection found, trying to create a new connection");
                        XMPPTCPConnectionConfiguration config = buildConfiguration();
                        SmackConfiguration.DEBUG = true;
                        connection = new XMPPTCPConnection(config);
                        connection.connect();
                    }
                } catch (Exception e) {
                    Log.e(HOST, "some issue with getting connection :" + e.getMessage());

                }

                Log.d(HOST, "Connection Properties: " + connection.getHost() + " " + connection.getHost());
                Log.d(HOST, "Time taken in first time connect: " + (System.currentTimeMillis() - l));
        ConnectionSyncJob.schedulePeriodic();
        return connection;
    }

    public boolean isConnected() {
        return (this.connection != null) && (this.connection.isConnected());
    }

    public boolean openConnection() {
        return login(USERNAME, PASSWORD) ? true : false;
    }
    boolean isLoggedIn = false;
    private boolean login(final String username, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = getConnection();
                    connection.login(username, password);
                    if (connection.isAuthenticated()) {
                        isLoggedIn = true;
                        mOnLoggedIn.onLoggedIn(true);
                        roster = Roster.getInstanceFor(connection);
                        roster.addRosterListener(mRosterListener);
                        StanzaFilter stanzaFilter = new StanzaTypeFilter(Message.class);
                        connection.addAsyncStanzaListener(mStanzaListener, stanzaFilter);
                    }
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
       return isLoggedIn;
    }

    private boolean sendMessage(Jid to,Message.Type type, String messageBody){
        if (isConnected()){
            Message message = new Message(to, Message.Type.chat);
            message.setBody(messageBody);
            try {
                connection.sendStanza(message);
                return true;
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void setOnLoggedInListener(OnLoggedIn listener) {
        mOnLoggedIn = listener;
    }

    public void setOnConnectedListener(OnConnected listener) {
        mOnConnected = listener;
    }

    public void setOnRosterListener(OnRosterListener listener) {
        mOnRosterListener = listener;
    }

    public void setOnStanzaListener(OnStanzaListener listener) {
        mOnStanzaListener = listener;
    }

    public interface OnLoggedIn {
        void onLoggedIn(boolean isloggedIn);
    }

    public interface OnConnected {
        void onConnectionEstablished(boolean isConnectionEstablished);
    }

    public interface OnRosterListener {
        void entriesAdded(Collection<Jid> addresses);

        ;

        void entriesUpdated(Collection<Jid> addresses);

        void entriesDeleted(Collection<Jid> addresses);

        void presenceChanged(Presence presence);
    }

    public interface OnStanzaListener {
        void processStanza(Stanza packet);
    }


}