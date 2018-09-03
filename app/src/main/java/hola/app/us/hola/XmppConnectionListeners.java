package hola.app.us.hola;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.Collection;
import java.util.Set;

public interface XmppConnectionListeners {
    void onServiceStarted();
    void onServiceStopped();
    void xmppOnRosterUpdatedLiteners(Collection<RosterEntry> entries);
}
