package com.rsd96.kidsgames;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Ramshad on 5/11/18.
 */

public class CardsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Card> cardsList;
    ArrayList<Integer> selectedCards;

    CardsAdapter(Context c, ArrayList<Card> cardsList) {
        this.context =c;
        this.cardsList = cardsList;
        selectedCards = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return cardsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int count = i;
        Log.d("CARDADAPTER", String.valueOf(i));
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.card_grid_item, null);
        }

        final ImageView imageView = view.findViewById(R.id.ivCardImage);
        if (cardsList.get(count).showCard) {
            imageView.setImageDrawable(cardsList.get(count).getCardImage());
        } else {
            if (!selectedCards.contains(Integer.valueOf(count)))
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.card_back));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCards.add(Integer.valueOf(count));
                if (selectedCards.size() == 2) {
                    ((CardActivity)context).compareCards(selectedCards);
                    selectedCards.clear();
                }
                imageView.setImageDrawable(cardsList.get(count).getCardImage());
            }
        });
        return view;
    }
}
