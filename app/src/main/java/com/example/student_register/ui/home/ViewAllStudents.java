package com.example.student_register.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.student_register.R;
import com.example.student_register.databinding.HomeHomeBinding;
import com.example.student_register.data.mvvm.adapter.StudentAdapter;
import com.example.student_register.data.mvvm.model.StudentModel;
import com.example.student_register.data.mvvm.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ViewAllStudents extends Fragment implements StudentAdapter.OnStudentItemClicked, View.OnClickListener {

    // this class is responsible for viewing all students
    private @NonNull
    HomeHomeBinding
            binding;
    private NavController controller;

    // initializing student view model to observe students, student model and put the values in an array list
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
        // setting up Navigation and calling a function to run recycler view
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // this function return all students and observe for real time updating issues
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
//        binding.settingsViewPager.setOnClickListener(this);
    }

    private void recyclerViewSetup() {
        // setting up the recycler view adapter
        recyclerView = binding.recyclerview;
        adapter = new StudentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // setting up the functionality of swiping an item from the recycler view
        // swiping to left
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // deciding what should happen when a user swipes an item
                // building an alert dialog to delete a student
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Student");
                // showing a message to the user with info about the swiped student
                builder.setMessage("Are you sure that you want to delete the student " +
                        studentModels2.get(viewHolder.getAdapterPosition()).getName() +
                        "?\n")
                        // if the user clicks on delete, then a student will be deleted and the data will be observed for updating purposes
                        .setPositiveButton("Yes", (dialog, id) -> {
                            DocumentReference studentRef = FirebaseFirestore.getInstance()
                                    .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("student").document(studentModels2.get(viewHolder.getAdapterPosition()).getStudentId());
                            studentRef.delete();
                            adapter.notifyDataSetChanged();
                        })
                        // the alert dialog will be cancelled if the user clicks on cancel
                        .setNegativeButton("No", (dialog, id) -> {
                            dialog.cancel();
                            adapter.notifyDataSetChanged();
                        }).setOnCancelListener(dialog -> adapter.notifyDataSetChanged());
                AlertDialog alert = builder.create();
                alert.show();

            }
        }).attachToRecyclerView(recyclerView);


    }

    @Override
    public void onItemClicked(int position) {
        // calling a function from the recycler view adapter to pass a student position
        // to view_edit_student screen
        ViewAllStudentsDirections.ActionHome2ToStudentViewPager action =
                ViewAllStudentsDirections.actionHome2ToStudentViewPager();
        action.setPosition(position);
        controller.navigate(action);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button:
                // transferring the user to a create a new student screen
                controller.navigate(R.id.action_home2_to_create_Student);
                break;
//            case R.id.settings_view_pager:
//                controller.navigate(R.id.action_home2_to_accountViewPager);
//                break;
            default:
        }
    }
}