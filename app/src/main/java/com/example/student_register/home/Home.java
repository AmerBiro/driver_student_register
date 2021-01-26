package com.example.student_register.home;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.student_register.R;
import com.example.student_register.databinding.HomeHomeBinding;
import com.example.student_register.mvvm.StudentAdapter;
import com.example.student_register.mvvm.StudentModel;
import com.example.student_register.mvvm.StudentViewModel;
import com.example.student_register.student.DeleteStudent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;



public class Home extends Fragment implements StudentAdapter.OnStudentItemClicked, View.OnClickListener {

    private @NonNull
    HomeHomeBinding
     binding;
    private NavController controller;

    private StudentViewModel studentViewModel;
    private List<StudentModel> studentModels2 = new ArrayList<>();
    private StudentAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentModels2 = studentModels;
                adapter.setStudentModels(studentModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.floatingButton.setOnClickListener(this);
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new StudentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Student");
                builder.setMessage("Are you sure that you want to delete the student " +
                        studentModels2.get(viewHolder.getAdapterPosition()).getName() +
                        "?\n")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DocumentReference studentRef = FirebaseFirestore.getInstance()
                                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("student").document(studentModels2.get(viewHolder.getAdapterPosition()).getStudentId());
                                studentRef.delete();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                adapter.notifyDataSetChanged();
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }).attachToRecyclerView(recyclerView);


    }

    @Override
    public void onItemClicked(int position) {
        HomeDirections.ActionHome2ToStudentViewPager action =
                HomeDirections.actionHome2ToStudentViewPager();
        action.setPosition(position);
        controller.navigate(action);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_button:
                controller.navigate(R.id.action_home2_to_create_Student);
                break;
            default:
        }
    }
}