package com.example.driverstudentregister.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.ViewPagerViwerBinding;
import com.example.driverstudentregister.home.HomeDirections;
import com.example.driverstudentregister.mvvm.StudentModel;
import com.example.driverstudentregister.mvvm.StudentViewModel;

import java.util.List;

import static android.content.ContentValues.TAG;


public class ViewPagerViwer extends Fragment {

    private @NonNull ViewPagerViwerBinding binding;
    private Info info;
    private Payment payment;
    private Theory theory;
    private Practise practise;
    private Note note;
    private int position;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        position = ViewPagerViwerArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "Position: " + position);
    }


}