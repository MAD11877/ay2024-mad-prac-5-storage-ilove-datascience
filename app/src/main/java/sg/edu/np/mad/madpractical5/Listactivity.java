package sg.edu.np.mad.madpractical5;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;

import sg.edu.np.mad.excersise5.R;

public class Listactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MYDBHandler db = new MYDBHandler(this);
        ArrayList<User> userlist = db.getUsers();
        User user =userlist.get(1);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button follow = findViewById(R.id.button3);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateUser(user);
                boolean yes = user.isFollowed();
                if (yes) {
                    follow.setText("Unfollow");
                } else {
                    follow.setText("Follow");
                }
            }
        });

    }
}