package com.example.pokemontypechecker;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.android.githubsearch.data.GitHubRepo;

import java.util.List;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeAdapter.PokemonTypeViewHolder> {
    private List<String> mTypes;
    OnTypeClickListener mTypeClickListener;

    public interface OnTypeClickListener {
        void onTypeClick(String type);
    }

    PokemonTypeAdapter(OnTypeClickListener typeClickListener) {
        mTypeClickListener = typeClickListener;
    }

    public void updateSearchResults(List<String> types) {
        mTypes = types;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTypes != null) {
            return mTypes.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public PokemonTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_type_item, parent, false);
        return new PokemonTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonTypeViewHolder holder, int position) {
        holder.bind(mTypes.get(position));
    }

    class PokemonTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mPokemonTypeTV;

        public PokemonTypeViewHolder(View itemView) {
            super(itemView);
            mPokemonTypeTV = itemView.findViewById(R.id.tv_pokemon_type_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String type = mTypes.get(getAdapterPosition());
            mTypeClickListener.onTypeClick(type);
        }

        public void bind(String repo) {
            mPokemonTypeTV.setText(repo);
        }
    }
}

