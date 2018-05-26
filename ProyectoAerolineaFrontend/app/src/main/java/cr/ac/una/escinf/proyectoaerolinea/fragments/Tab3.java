package cr.ac.una.escinf.proyectoaerolinea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

import butterknife.ButterKnife;
import butterknife.BindView;

import butterknife.Unbinder;
import cr.ac.una.escinf.proyectoaerolinea.R;

public class Tab3 extends Fragment {

    @BindView(R.id.prev_tab3) Button prev;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab3, container, false);
        unbinder = ButterKnife.bind(this, view);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabHost host = (TabHost) getActivity().findViewById(android.R.id.tabhost);
                host.setCurrentTab(1);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
