package com.example.student_register.data.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.student_register.data.mvvm.FirebaseRepository;
import com.example.student_register.data.mvvm.model.StudentModel;

import java.util.List;

public class StudentViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<StudentModel>> studentModelData = new MutableLiveData<>();

    public LiveData<List<StudentModel>> getStudentModelData() {
        return studentModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public StudentViewModel() {
        firebaseRepository.getStudentData();
    }

    @Override
    public void studentDataAdded(List<StudentModel> studentModels) {
        studentModelData.setValue(studentModels);
    }

    @Override
    public void onError(Exception e) {

    }
}
