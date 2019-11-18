package kim.hyunwoo.chap21;

import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ItemAdapter adapter;
    ArrayList<Item> datas;

    EditText nameView, dateView;
    Button saveBtn;

    Uri uri;

    // update 위해서
    boolean isUpdate;
    String _id;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        nameView = findViewById(R.id.name);
        dateView = findViewById(R.id.date);
        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);

        // test17의 콘텐츠 프로바이더 이용 - 조회
        uri = Uri.parse("content://com.example.test17.Provider");

        datas = new ArrayList<>();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        while (cursor.moveToNext()) {
            Item item = new Item();
            item._id = cursor.getString(0);
            item.name = cursor.getString(1);
            item.date = cursor.getString(2);
            datas.add(item);
        }

        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDecoration());
    }

    @Override
    public void onClick(View v) {
        if (isUpdate) {
            String name = nameView.getText().toString();
            String date = dateView.getText().toString();
            if (!name.equals("") && !date.equals("")) {
                // test17의 콘텐츠 프로바이더 이용 - 수정


            }
            nameView.setText("");
            dateView.setText("");
            isUpdate = false;
        } else {
            Item item = new Item();
            item.name = nameView.getText().toString();
            item.date = dateView.getText().toString();

            if (!item.name.equals("") && !item.date.equals("")) {
                // test17의 콘텐츠 프로바이더 이용 - 삽입

            }
            nameView.setText("");
            dateView.setText("");
        }
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            view.setBackgroundColor(0xFFFFFFFF);
            ViewCompat.setElevation(view, 10.0f);
            outRect.set(20, 10, 20, 10);
        }
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item i = datas.get(position);
            holder.item_date.setText(i.date);
            holder.item_name.setText(i.name);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, item_date;
        Item item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_date = itemView.findViewById(R.id.item_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // item 클릭하여 수정

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // test17의 콘텐츠 프로바이더 이용- 삭제

                    return true;
                }
            });
        }
    }
}
