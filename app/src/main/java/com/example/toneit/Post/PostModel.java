package com.example.toneit.Post;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.toneit.FragmentHome.HomeFragment;
import com.example.toneit.FragmentNotify.NotifyFragment;
import com.example.toneit.FragmentProfile.ProfileFragment;
import com.example.toneit.FragmentSearch.SearchFragment;
import com.example.toneit.FragmentStory.StoryFragment;
import com.example.toneit.R;
import com.example.toneit.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class PostModel {
    String image;
    String status;
    String userId;
    String profile;
    String userName;

    public PostModel(String image, String status, String profile, String userName) {
        this.image = image;
        this.status = status;
        this.profile = profile;
        this.userName = userName;
    }
    PostModel(){

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static class HomeActivity extends AppCompatActivity {

        ViewPager pagerId;
        TabLayout tabId;
        private long pressedTime;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            findView();

            setAdapterLayout();

            tabId.setupWithViewPager(pagerId);
            setTabIcon();

        }

        public void findView() {
            pagerId = findViewById(R.id.pagerId);
            tabId = findViewById(R.id.tabId);
        }

        public void setTabIcon() {
            tabId.getTabAt(0).setIcon(R.drawable.home);
            tabId.getTabAt(1).setIcon(R.drawable.search);
            tabId.getTabAt(2).setIcon(R.drawable.story);
            tabId.getTabAt(3).setIcon(R.drawable.bell);
            tabId.getTabAt(4).setIcon(R.drawable.user);

        }



        public void setAdapterLayout() {
            TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
            adapter.addFrame(new HomeFragment(), "");
            adapter.addFrame(new SearchFragment(), "");
            adapter.addFrame(new StoryFragment(), "");
            adapter.addFrame(new NotifyFragment(), "");
            adapter.addFrame(new ProfileFragment(), "");
            pagerId.setAdapter(adapter);

        }

        @Override
        public void onBackPressed() {

            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
    }
}
