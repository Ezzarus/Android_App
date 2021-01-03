package com.example.star_wars.presentation.characterDisplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.recyclerview.widget.RecyclerView;

import com.example.star_wars.R;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter d'un RecyclerView permettant d'afficher les Character
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<CharacterViewItem> characterViewItemList;
    private CharacterActionInterface characterActionInterface;
    private boolean asList;


    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private ImageButton characterImageButton;
        private TextView nameTextView;
        private View v;
        private CharacterViewItem characterViewItem;
        private CharacterActionInterface characterActionInterface;
        private boolean asList;

        public CharacterViewHolder(View v, final CharacterActionInterface characterActionInterface, boolean asList) {
            super(v);
            this.v = v;
            this.characterActionInterface = characterActionInterface;
            this.asList = asList;
            if(asList){
                this.characterImageButton = this.v.findViewById(R.id.character_nameList_image);
                this.nameTextView = this.v.findViewById(R.id.character_textView);
            } else {
                this.characterImageButton = v.findViewById(R.id.character_image_grid);
            }
            setupListener(asList);

        }

        private void setupListener(boolean asList) {
            characterImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    characterActionInterface.onCharacterClicked(characterViewItem.getCharacterId());
                }
            });
            if (asList) {
                nameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        characterActionInterface.onCharacterClicked(characterViewItem.getCharacterId());
                    }
                });
            }
        }

        public void bind(CharacterViewItem viewItem){
            this.characterViewItem = viewItem;
            Glide.with(v)
                    .load(viewItem.getImage())
                    .into(characterImageButton);

            if(asList){
                this.nameTextView.setText(characterViewItem.getName());
            }
        }
        
        public ImageButton getImageButton() {
            return characterImageButton;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CharacterAdapter(CharacterActionInterface characterActionInterface, boolean asList) {
        characterViewItemList = new ArrayList<>() ;
        this.characterActionInterface = characterActionInterface;
        this.asList = asList;
    }

    public void bindViewModels(List<CharacterViewItem> characterViewItemList){
        this.characterViewItemList.clear();
        this.characterViewItemList.addAll(characterViewItemList);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(asList ? R.layout.character_listview : R.layout.character_image, viewGroup, false);

        return new CharacterViewHolder(view, characterActionInterface, asList);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CharacterViewHolder holder, final int position) {

        holder.bind(characterViewItemList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return characterViewItemList.size();
    }


}
