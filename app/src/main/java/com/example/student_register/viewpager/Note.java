package com.example.student_register.viewpager;

import com.example.student_register.R;
import com.example.student_register.databinding.ViewpagerNoteBinding;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.example.student_register.student.UpdateNote;

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

import java.util.List;

import static android.content.ContentValues.TAG;


public class Note extends Fragment implements View.OnClickListener {

    private @NonNull
    ViewpagerNoteBinding
     binding;
    private NavController controller;
    private int getAdapterPosition;
    private StudentViewModel studentViewModel;
    private String name, note, studentId;
    private UpdateNote updateNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerNoteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.updateNoteButton.setOnClickListener(this);
    }

    public void getPosition(int position){
        getAdapterPosition = position;
        Log.d(TAG, "onViewCreated: " + "Note: " +  getAdapterPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentId = studentModels.get(getAdapterPosition).getStudentId();
                name = studentModels.get(getAdapterPosition).getName();
                note = studentModels.get(getAdapterPosition).getNote();
                if (note.trim().isEmpty()){
                    binding.note.setHint("Add a note to " + name);
                }else{
                    binding.note.setText(note);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_note_button:
                updateNote = new UpdateNote();
                updateNote.updateNote(studentId, binding.note, binding.updateNoteButton, binding.noteProgressBar, getActivity());
                break;
            default:
        }
    }
}