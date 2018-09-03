package hola.app.us.hola;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public interface OnLoggedIn {
    void onLoggedIn(boolean isloggedIn, XMPPTCPConnection connection);
}
