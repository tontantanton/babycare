package com.example.babycare.Chat.User;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.babycare.Chat.User.OnlyUserAdater;
import com.example.babycare.Model.Chat;
import com.example.babycare.Model.User;
import com.example.babycare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OnlyUserChatFragment extends Fragment {

    private RecyclerView recyclerView;

    private OnlyUserAdater onlyUserAdater;
    private List<User> mUser;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_only_user_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(fuser.getUid())){
                        usersList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(fuser.getUid())){
                        usersList.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        updateTonken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    /*private void updateTonken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }*/

    private void readChats(){
        mUser = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Doctor");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    for (String id : usersList){
                        if(user.getId().equals(id)){
                            if (mUser.size() != 0){
                                for (User user1 : mUser){
                                    if (!user.getId().equals(user1.getId())){
                                        mUser.add(user);
                                    }
                                }
                            } else {
                                mUser.add(user);
                            }
                        }
                    }
                }
                onlyUserAdater = new OnlyUserAdater(getContext(), mUser, true);
                recyclerView.setAdapter(onlyUserAdater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
