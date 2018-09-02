package pe.edu.tecsup.firebaseapp2018.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.edu.tecsup.firebaseapp2018.R;
import pe.edu.tecsup.firebaseapp2018.models.Post;
import pe.edu.tecsup.firebaseapp2018.models.User;

public class PostRVAdapter extends RecyclerView.Adapter<PostRVAdapter.ViewHolder> {

    private static final String TAG = PostRVAdapter.class.getSimpleName();

    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public PostRVAdapter(){
        this.posts = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView displaynameText;
        TextView likesText;
        LikeButton likeButton;
        ImageView pictureImage;
        TextView titleText;
        TextView bodyText;

        ViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_picture);
            displaynameText = itemView.findViewById(R.id.user_displayname);
            likesText = itemView.findViewById(R.id.like_count);
            likeButton = itemView.findViewById(R.id.like_button);
            pictureImage = itemView.findViewById(R.id.post_picture);
            titleText = itemView.findViewById(R.id.post_title);
            bodyText = itemView.findViewById(R.id.post_body);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Post post = posts.get(position);

        holder.titleText.setText(post.getTitle());
        holder.bodyText.setText(post.getBody());

        FirebaseDatabase.getInstance().getReference("users").child(post.getUserid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        holder.displaynameText.setText(user.getDisplayName());

                        Picasso.with(holder.itemView.getContext())
                                .load(user.getPhotoUrl())
                                .placeholder(R.drawable.ic_profile)
                                .into(holder.userImage);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        holder.likesText.setText(String.format(Locale.getDefault(), "%d likes", post.getLikes().size()));



        // Recuperando la referencia de los likes del post actual
        final DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference("posts")
                .child(post.getId())
                .child("likes");

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Marcando el like button siempre y cuando el uid del usuario actual se encuentre en la lista de likes
        holder.likeButton.setLiked(post.getLikes().containsKey(currentUser.getUid()));

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likesRef.child(currentUser.getUid()).setValue(true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likesRef.child(currentUser.getUid()).removeValue();
            }
        });

        // Download photo from Firebase Storage
        if(post.getPhotourl() != null){
            Picasso.with(holder.itemView.getContext()).load(post.getPhotourl()).into(holder.pictureImage);
        }else{
            holder.pictureImage.setImageResource(R.drawable.ic_picture);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
