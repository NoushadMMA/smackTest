package hola.app.us.hola.xmpp_extensions;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class MessageTypeInformation implements ExtensionElement {

    public static final String ELEMENT = "message_type";
    public static final String NAMESPACE = "urn:xmpp:message_type";


    private final int messageType;

    public MessageTypeInformation(int messageeType) {
        this.messageType = messageeType;
    }


    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }


    @Override
    public CharSequence toXML() {
        XmlStringBuilder xml = new XmlStringBuilder(this);
        xml.attribute("message_type", messageType);
        xml.rightAngleBracket();
        xml.closeElement(this);
        return null;
    }
}