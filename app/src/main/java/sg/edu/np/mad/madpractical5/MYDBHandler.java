package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;
public class MYDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public MYDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_FOLLOWED + " INTEGER" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        initializeUsers(db);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    private void initializeUsers(SQLiteDatabase db) {
        for (int i = 0; i < 20; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "User" + i);
            values.put(COLUMN_DESCRIPTION, "Description" + i);
            values.put(COLUMN_FOLLOWED, new Random().nextBoolean() ? 1 : 0);
            db.insert(TABLE_USERS, null, values);
        }
    }
    public User getUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_FOLLOWED},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int followedIndex = cursor.getColumnIndex(COLUMN_FOLLOWED);

            User user = new User(
                    idIndex >= 0 ? cursor.getInt(idIndex) : -1,
                    nameIndex >= 0 ? cursor.getString(nameIndex) : null,
                    descriptionIndex >= 0 ? cursor.getString(descriptionIndex) : null,
                    followedIndex >= 0 && cursor.getInt(followedIndex) == 1
            );
            cursor.close();
            return user;
        } else {
            return null;
        }

    }
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.isFollowed() ? 1 : 0);

        db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            try {
                // Loop through the cursor and add each user to the list
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                    int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                    int followedIndex = cursor.getColumnIndex(COLUMN_FOLLOWED);

                    // Check if column indexes are valid
                    if (idIndex >= 0 && nameIndex >= 0 && descriptionIndex >= 0 && followedIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        String description = cursor.getString(descriptionIndex);
                        boolean followed = cursor.getInt(followedIndex) == 1;

                        User user = new User(id, name, description, followed);
                        userList.add(user);
                    } else {
                        Log.e("getUsers", "Invalid column index");
                    }
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return userList;
    }

}