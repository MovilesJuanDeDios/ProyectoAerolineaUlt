package cr.ac.una.escinf.proyectoaerolinea.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class PicassoImageLoadingService implements ImageLoadingService {
    public Context context;

    public PicassoImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Picasso.with(context).load(url).fit().into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Picasso.with(context).load(resource).fit().into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Picasso.with(context).load(url).fit().placeholder(placeHolder).error(errorDrawable).into(imageView);
    }
}
