package com.example.driverstudentregister.home;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.HomeHomeBinding;
import com.example.driverstudentregister.mvvm.StudentAdapter;
import com.example.driverstudentregister.mvvm.StudentModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Home extends Fragment {

    private @NonNull HomeHomeBinding binding;

    private NavController controller;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference student;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeHomeBinding.inflate(inflater, container, false);
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
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        AdapterSetup();
        RecyclerViewSetUp();
        adapter.startListening();

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_home2_to_create_Student);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void AdapterSetup() {

        student = firestore.collection("user").document(user.getUid())
                .collection("student");
        Query query = student.orderBy("name", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<StudentModel> options = new FirestoreRecyclerOptions.Builder<StudentModel>()
                .setQuery(query, StudentModel.class)
                .build();
        adapter = new StudentAdapter(options);
    }

    private void RecyclerViewSetUp() {
        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete student");
                builder.setMessage("Are you sure that you want to delete the following student")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.deleteItem(viewHolder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                adapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickedListener() {
            private static final String TAG = "";
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                StudentModel model = documentSnapshot.toObject(StudentModel.class);
                String id = documentSnapshot.getId();
                Log.d(TAG, "onItemClick: " + position);
                HomeDirections.ActionHome2ToMain action = HomeDirections.actionHome2ToMain();
                action.setName(model.getName());
                action.setPhone(model.getPhone());
                action.setStreet(model.getStreet());
                action.setZipCode(model.getZip_code());
                action.setCity(model.getCity());
                action.setCpr(model.getCpr());
                action.setStudentId(id);
                action.setPrice(model.getPrice());
                action.setDiscount(model.getDiscount());
                action.setNote(model.getNote());
                controller.navigate(action);
            }
        });




    }

}