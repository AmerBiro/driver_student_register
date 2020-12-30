package com.example.driverstudentregister.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.ViewPagerViwerBinding;


public class ViewPagerViwer extends Fragment {

    private @NonNull ViewPagerViwerBinding binding;
    private Info info;
    private Payment payment;
    private Theory theory;
    private Practise practise;
    private Note note;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewPagerViwerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        info = new Info();
        payment = new Payment();
        theory = new Theory();
        practise = new Practise();
        note = new Note();

        binding.tabLayut.setupWithViewPager(binding.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0);


        adapter.addFragment(info, "info");
        adapter.addFragment(payment, "$");
        adapter.addFragment(theory, "theory");
        adapter.addFragment(practise, "practise");
        adapter.addFragment(note, "note");

        binding.viewPager.setAdapter(adapter);
//        binding.viewPager.setCurrentItem(1);
        binding.tabLayut.getTabAt(0).setIcon(R.drawable.ic_baseline_info_24);
        binding.tabLayut.getTabAt(1).setIcon(R.drawable.ic_baseline_payment_24);
        binding.tabLayut.getTabAt(2).setIcon(R.drawable.ic_baseline_menu_book_24);
        binding.tabLayut.getTabAt(3).setIcon(R.drawable.ic_baseline_directions_car_24);
        binding.tabLayut.getTabAt(4).setIcon(R.drawable.ic_baseline_note_24);


        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}