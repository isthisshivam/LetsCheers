package com.shivam.world.mad.letscheers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



    private class Adapter extends RecyclerView.Adapter<Adapter.ProgViewHolder> {
        String[] data;

        public Adapter(String[] data) {
            this.data = data;

        }

        @Override
        public ProgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
            View view = inflater.inflate( R.layout.listviewi, parent, false );
            return new ProgViewHolder( view );
        }

        @Override
        public void onBindViewHolder(ProgViewHolder holder, int position) {
            String title = data[position];
            holder.txt.setText( title );

        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public class ProgViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView txt;

            public ProgViewHolder(View itemView) {
                super( itemView );

                img = (ImageView) itemView.findViewById( R.id.im );
                txt = (TextView) itemView.findViewById( R.id.txt );
            }
        }


    }
}
