package com.uz.country_name;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uz.country_name.databinding.ActivityMainBinding;
import com.uz.country_name.models.FlagQuiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private List<FlagQuiz> flagQuizList;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFlag();
        setQuestion();
    }

    private void setQuestion() {

        FlagQuiz flagQuiz = flagQuizList.get(index);
        binding.image.setImageResource(flagQuiz.getImage());
        String country = flagQuiz.getCountry();
        List<String> randomList = getRandomList(country);

        for (int i = 0; i < randomList.size() / 2; i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            button.setLayoutParams(layoutParams);
            button.setOnClickListener(this);
            button.setText(randomList.get(i));
            button.setId(i);
            binding.layout1.addView(button);
        }
        for (int i = randomList.size() / 2; i < randomList.size(); i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            button.setLayoutParams(layoutParams);
            button.setText(randomList.get(i));
            button.setId(i);
            button.setOnClickListener(this);
            binding.layout2.addView(button);
        }
    }

    public List<String> getRandomList(String country) {
        String a = "ABCDEFGHJIKLMNOPQRSTUVWXYZ";
        int b = country.length();
        int c = 18 - b;
        String d = a.substring(0, c);
        String str = country.toUpperCase().concat(d);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            list.add(str.substring(i, i + 1));
        }
        Collections.shuffle(list);
        return list;
    }

    private void loadFlag() {
        flagQuizList = new ArrayList<>();
        flagQuizList.add(new FlagQuiz(R.drawable.china, "China"));
        flagQuizList.add(new FlagQuiz(R.drawable.usa, "USA"));
        flagQuizList.add(new FlagQuiz(R.drawable.spain, "Spain"));
        flagQuizList.add(new FlagQuiz(R.drawable.england, "England"));
        flagQuizList.add(new FlagQuiz(R.drawable.germany, "Germany"));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String s = button.getText().toString();
        button.setVisibility(View.INVISIBLE);

        Button topButton = new Button(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        button.setLayoutParams(layoutParams);
        topButton.setText(s);
        binding.layout.addView(topButton);
        topButton.setOnClickListener(v1 -> {
            binding.layout.removeView(topButton);
            button.setVisibility(View.VISIBLE);
        });
        int childCount = binding.layout.getChildCount();
        FlagQuiz flagQuiz = flagQuizList.get(index);
        String country = flagQuiz.getCountry();
        if (childCount == country.length()) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < childCount; i++) {
                View childView = binding.layout.getChildAt(i);
                Button btn = (Button) childView;
                String s1 = btn.getText().toString();
                stringBuilder.append(s1);
            }
            if (stringBuilder.toString().equals(country.toUpperCase())) {
                Toast.makeText(this, "Barakalla", Toast.LENGTH_SHORT).show();
                index++;
                binding.layout1.removeAllViews();
                binding.layout2.removeAllViews();
                binding.layout.removeAllViews();
                setQuestion();
            } else {
                Toast.makeText(this, "Noto'g'ri", Toast.LENGTH_SHORT).show();
            }

        }
    }
}