package com.example.student_register.viewpager;

import com.example.student_register.R;
import com.example.student_register.databinding.ViewpagerStudentViewPagerBinding;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;


public class StudentViewPager extends Fragment {

    private @NonNull
    ViewpagerStudentViewPagerBinding
     binding;
    private NavController controller;
    private int position;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StudentViewPagerAdapter adapter;
    private Info info;
    private Payment payment;
    private Theory theory;
    private Practise practise;
    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerStudentViewPagerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        info = new Info();
        payment = new Payment();
        theory = new Theory();
        practise = new Practise();
        note = new Note();
        tabLayout = binding.accountTabLayout;
        viewPager = binding.accountViewPager;
        tabLayout.setupWithViewPager(viewPager);
        viewpagerSetup(view);

        position = StudentViewPagerArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "onViewCreated: " + "ViewPager: " +  position);

        info.getPosition(position);
        payment.getPosition(position);
        note.getPosition(position);
        theory.getPosition(position);
    }

    private void viewpagerSetup(View view){
        adapter = new StudentViewPagerAdapter(getChildFragmentManager(), 0);

        adapter.addFragment(info, "Info");
        adapter.addFragment(payment, "Payment");
        adapter.addFragment(theory, "Theory");
        adapter.addFragment(practise, "Practise");
        adapter.addFragment(note, "Note");
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_info_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_payment_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_menu_book_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_directions_car_24);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_baseline_note_24);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}