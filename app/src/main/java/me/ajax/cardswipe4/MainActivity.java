package me.ajax.cardswipe4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AvatarLayoutManger avatarLayoutManger;
    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView avatarRecyclerView = findViewById(R.id.avatar_recycler_view);
        avatarRecyclerView.setLayoutManager(avatarLayoutManger = new AvatarLayoutManger());
        avatarRecyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
                return childCount - i - 1;
            }
        });
        avatarRecyclerView.setAdapter(new AvatarAdapter());


        RecyclerView cardRecyclerView = findViewById(R.id.card_recycler_view);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int allOffset = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int width = recyclerView.getMeasuredWidth();

                allOffset += -dx;
                avatarLayoutManger.scrollHorizontally(-allOffset / width, -allOffset % width / (float) width);
            }
        });
        cardRecyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
                return childCount - i - 1;
            }
        });
        cardAdapter = new CardAdapter();
        cardRecyclerView.setAdapter(cardAdapter);
    }

    List<Integer> getDatas() {

        List<Integer> images = new ArrayList<>();

        images.add(R.mipmap.image1);
        images.add(R.mipmap.image2);
        images.add(R.mipmap.image3);
        images.add(R.mipmap.image4);
        images.add(R.mipmap.image5);
        images.add(R.mipmap.image6);
        images.add(R.mipmap.image1);
        images.add(R.mipmap.image2);
        images.add(R.mipmap.image12);
        images.add(R.mipmap.image9);
        images.add(R.mipmap.image10);
        images.add(R.mipmap.image11);

        return images;
    }
}
