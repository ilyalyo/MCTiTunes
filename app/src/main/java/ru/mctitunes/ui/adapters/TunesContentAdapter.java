package ru.mctitunes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import ru.mctitunes.R;
import ru.mctitunes.ui.interfaces.SearchResult;
import ru.mctitunes.utils.StringUtils;

public class TunesContentAdapter extends RecyclerView.Adapter<TunesContentAdapter.TContentViewHolder> {

    private Context context;
    private List<? extends SearchResult> items = new ArrayList<>();
    private LayoutInflater inflater;

    public TunesContentAdapter(Context context, List<? extends SearchResult> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tunes_content_item, parent, false);
        return new TContentViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(TContentViewHolder holder, int position) {
        holder.onBindViewHolder(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class TContentViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private LinearLayout rootLayout;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView avatarImageView;


        TContentViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            rootLayout = (LinearLayout) itemView.findViewById(R.id.root);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            avatarImageView = (ImageView) itemView.findViewById(R.id.avatar);
        }

        void onBindViewHolder(SearchResult tunesContent) {
            titleTextView.setText(tunesContent.getTitle());
            descriptionTextView.setText(StringUtils.getDescriptionForSearchResult(tunesContent, context));
            Picasso.with(context).load(tunesContent.getAvatar())
                    .transform(new CropCircleTransformation())
                    .into(avatarImageView);
        }
    }
}