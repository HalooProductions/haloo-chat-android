package com.haloo.chat.haloochat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class ConversationsArrayAdapter extends ArrayAdapter<ConversationInfo> {
    private ArrayList<ConversationInfo> list = new ArrayList<>();
    Context mContext;

    private static class ViewHolder {
        TextView nameTxt;
        TextView previewTxt;
        NetworkImageView image;
    }

    public ConversationsArrayAdapter(ArrayList<ConversationInfo> data, Context context) {
        super(context, R.layout.conversation_listview_item, data);
        this.list = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConversationInfo conversationInfo = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.conversation_listview_item, parent, false);

            viewHolder.nameTxt = convertView.findViewById(R.id.conversationNameTextView);
            viewHolder.previewTxt = convertView.findViewById(R.id.conversationPreviewTextView);
            viewHolder.image = convertView.findViewById(R.id.conversationImageView);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.nameTxt.setText(conversationInfo.getName());
        viewHolder.previewTxt.setText(conversationInfo.getPreview());

        return result;
    }
}
