package org.metabrainz.mobile.presentation.features.collection;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.metabrainz.mobile.R;
import org.metabrainz.mobile.data.sources.api.entities.mbentity.Label;
import org.metabrainz.mobile.intent.IntentFactory;
import org.metabrainz.mobile.presentation.features.label.LabelActivity;

import java.util.List;

public class CollectionAdapterLabel extends CollectionAdapter {
    private List<Label> data;

    public CollectionAdapterLabel(List<Label> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public CollectionAdapterLabel.LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_label, parent, false);
        return new CollectionAdapterLabel.LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CollectionAdapterLabel.LabelViewHolder viewHolder = (CollectionAdapterLabel.LabelViewHolder) holder;
        Label label = data.get(position);
        viewHolder.labelName.setText(label.getName());

        setViewVisibility(label.getType(), viewHolder.labelType);
        //temporary fix TODO: Seralize null objects
        if (label.getArea() != null)
            setViewVisibility(label.getArea().getName(), viewHolder.labelArea);
        else viewHolder.labelArea.setVisibility(View.GONE);
        setViewVisibility(label.getDisambiguation(), viewHolder.labelDisambiguation);
        setAnimation(viewHolder.itemView, position);
        viewHolder.itemView.setOnClickListener(v -> onClick(v, position));
    }

    private void onClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), LabelActivity.class);
        intent.putExtra(IntentFactory.Extra.LABEL, data.get(position).getMbid());
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class LabelViewHolder extends EntityViewHolder {
        TextView labelName, labelType, labelDisambiguation, labelArea;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            labelName = itemView.findViewById(R.id.label_name);
            labelType = itemView.findViewById(R.id.label_type);
            labelDisambiguation = itemView.findViewById(R.id.label_disambiguation);
            labelArea = itemView.findViewById(R.id.label_area);
        }

    }
}