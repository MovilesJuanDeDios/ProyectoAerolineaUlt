package cr.ac.una.escinf.proyectoaerolinea.adapters;

import cr.ac.una.escinf.proyectoaerolinea.R;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class BannerSliderAdapter extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide(R.drawable.rio_banner);
                break;
            case 1:
                viewHolder.bindImageSlide(R.drawable.newyork_banner);
                break;
            case 2:
                viewHolder.bindImageSlide(R.drawable.santiago_banner);
                break;
        }
    }
}


