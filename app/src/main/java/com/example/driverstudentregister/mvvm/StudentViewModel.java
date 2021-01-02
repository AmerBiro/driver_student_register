package com.example.driverstudentregister.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    public void studentDataAdded(List<StudentModel> programListModels) {
        studentModelData.setValue(programListModels);
    }

    @Override
    public void onError(Exception e) {

    }
}
