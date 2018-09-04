package hola.app.us.hola;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConversationsListAdapter extends RecyclerView.Adapter<ConversationsListAdapter.ViewHolder> {


    List<RosterEntry> mRosterEntryList = new ArrayList<>();
    onClickedItemListener mOnClickedItemListener;
    private Collection<RosterEntry> mRosterEntryCollection;

    public ConversationsListAdapter(List<RosterEntry> rosterEntryCollection) {
        mRosterEntryList = rosterEntryCollection;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (view == null) {
            view = inflater.inflate(R.layout.conversations_list_items, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final RosterEntry entry = mRosterEntryList.get(i);
        viewHolder.contactName.setText(entry.getName() != null ? entry.getName() : entry.getJid().toString().replace("@server.holaapp.us", ""));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedItemListener.onClickedConversationItem(entry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRosterEntryList.size();
    }

    public void setOnClickedItemListener(onClickedItemListener listener) {
        mOnClickedItemListener = listener;
    }

    public interface onClickedItemListener {
        void onClickedConversationItem(RosterEntry entry);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contactDp;
        TextView contactName;
        TextView contactLastMessage;
        TextView contactLastMessageTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactDp = itemView.findViewById(R.id.contact_dp);
            contactName = itemView.findViewById(R.id.contact_user_name);
            contactLastMessage = itemView.findViewById(R.id.contact_last_message);
            contactLastMessageTime = itemView.findViewById(R.id.contact_last_message_time);
        }
    }
}
