package shuja1497.bitsandpizzasmaterial;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shujareshi on 13/6/17.
 */

class CaptionedImagesAdapter  extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions;
    private int[] imageIds;

    private Listener listener;

    public static interface Listener {
//        This is the interface.
        public void onClick(int position);
    }

    public CaptionedImagesAdapter(String[] pizzaNames, int[] pizzaImages) {
        this.captions = pizzaNames;
        this.imageIds = pizzaImages;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }


    //Provide a reference to the views used in the recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Define the view holder
/*
        Our recycler view needs to display CardViews,
        so we specify that our ViewHolder contains
        CardViews. If you want to display another type
        of data in the recycler view, you define it here.*/
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }


    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create a new view
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(CaptionedImagesAdapter.ViewHolder holder,final int position) {
        //Set the values inside the given view

        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener!= null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //Return the number of items in the data set

        return captions.length;
    }
}
