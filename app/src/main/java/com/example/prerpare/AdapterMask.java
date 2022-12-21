package com.example.prerpare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

    public class AdapterMask extends BaseAdapter {
        private Context mContext;
        List<Mask> maskList;

        public AdapterMask(Context mContext, List<Mask> listProduct) {
            this.mContext = mContext;
            this.maskList = listProduct;
        }

        @Override
        public int getCount() {
            return maskList.size();
        }

        @Override
        public Object getItem(int i) {
            return maskList.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return maskList.get(i).getID();
        }

        private Bitmap getUserImage(String encodedImg)
        {

            if(encodedImg!=null&& !encodedImg.equals("null")) {
                byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            else
            {
                return null;

            }
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = View.inflate(mContext,R.layout.mask,null);
            TextView Title = v.findViewById(R.id.title);
            TextView Cost = v.findViewById(R.id.cost);
            TextView StockAvailability = v.findViewById(R.id.stockAvailability);
            TextView AvailabilityInTheStore = v.findViewById(R.id.availabilityInTheStore);
            TextView Description = v.findViewById(R.id.description);
            TextView Rewiews = v.findViewById(R.id.rewiews);
            ImageView Image = v.findViewById(R.id.imageAvatar);

            Mask mask = maskList.get(i);
            Title.setText(mask.getTitle());
            Cost.setText(Integer.toString(mask.getCost()).toString());
            StockAvailability.setText(Integer.toString(mask.getStockAvailability()).toString());
            AvailabilityInTheStore.setText(Integer.toString(mask.getAvailabilityInTheStore()).toString());
            Description.setText(mask.getDescription());
            Rewiews.setText(mask.getRewiews());
            if(mask.getImage().toString().equals("null"))
            {
                Image.setImageResource(R.drawable.img);
            }
            else
            {
                Image.setImageBitmap(getUserImage(mask.getImage()));
            }


            return v;
        }}
