package cr.ac.una.escinf.proyectoaerolinea.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import cr.ac.una.escinf.proyectoaerolinea.R;

public class Tab2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tab2, container, false);
    }

    public void ocuparAsiento(){

        for(int i=1;i<41;i++){
            final String s="seat"+i;
            /*int resID = getResources().getIdentifier(s,"id", getPackageName());
            ImageButton button = (ImageButton)findViewById(resID);
            final ImageButton finalButton = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(Asiento element:Data.listaAsientos){
                        if(element.getId().equals(s) && element.getBus().equals(bus)){
                            if(element.getOcupado()==0) {
                                element.setOcupado(1);

                                int pos=getIntent().getIntExtra("position",0);

                                String url;
                                String url2;

                                int cant=Data.listaBuses.get(pos).getCantidad()-1;
                                Data.listaBuses.get(pos).setCantidad(cant);
                                JsonConnection conexion=new JsonConnection();
                                JsonConnection conexion2=new JsonConnection();
                                url=Data.url+"setAsiento&id="+element.getId()+"&nombre="+element.getTitle()+"&bus="+element.getBus()+"&cant="+cant+"&ubicacion="+element.getUbicacion() + "&ocupado=" + 1;
                                url2=Data.url+"setOcupado&id="+element.getId()+"&nombre="+element.getTitle()+"&bus="+element.getBus()+"&cant="+cant+"&ubicacion="+element.getUbicacion() + "&ocupado=" + 1;
                                conexion.execute(new String[]{url,"POST"});
                                conexion2.execute(new String[]{url2,"POST"});

                                finalButton.setColorFilter(getResources().getColor(R.color.red));

                            }
                            else {
                                element.setOcupado(0);

                                int pos=getIntent().getIntExtra("position",0);

                                String url;
                                String url2;

                                int cant=Data.listaBuses.get(pos).getCantidad()+1;
                                Data.listaBuses.get(pos).setCantidad(cant);
                                JsonConnection conexion=new JsonConnection();
                                JsonConnection conexion2=new JsonConnection();
                                url=Data.url+"setAsiento&id="+element.getId()+"&nombre="+element.getTitle()+"&bus="+element.getBus()+"&cant="+cant+"&ubicacion="+element.getUbicacion() + "&ocupado=" + 1;
                                url2=Data.url+"setOcupado&id="+element.getId()+"&nombre="+element.getTitle()+"&bus="+element.getBus()+"&cant="+cant+"&ubicacion="+element.getUbicacion() + "&ocupado=" + 0;
                                conexion.execute(new String[]{url,"POST"});
                                conexion2.execute(new String[]{url2,"POST"});

                                finalButton.setColorFilter(getResources().getColor(R.color.green));
                            }
                        }
                    }
                }
            });*/
        }

    }

    public void llenarAvion(){

        for(int i=1;i<41;i++){
            String s="seat"+i;
            /*int resID = getResources().getIdentifier(s,"id", getPackageName());
            ImageButton button = (ImageButton)findViewById(resID);
            for(Asiento element:Data.listaAsientos){
                if(element.getId().equals(s) && element.getBus().equals(bus)){
                    if(element.getOcupado()==1) {
                        button.setColorFilter(getResources().getColor(R.color.red));
                    }
                    else {
                        button.setColorFilter(getResources().getColor(R.color.green));
                    }
                }
            }*/
        }


    }
}
