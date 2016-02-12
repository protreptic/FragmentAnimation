package name.peterbukhal.android.fragmentanimation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import name.peterbukhal.android.fragmentanimation.CheckFragment.Check;
import name.peterbukhal.android.fragmentanimation.CheckFragment.CheckPosition;

public class MainActivity extends AppCompatActivity {

    private boolean active;
    private FragmentManager fm;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        if (fragment == null) {
            List<CheckPosition> positions = new ArrayList<>();
            positions.add(new CheckPosition("Время подачи", "18:25 Вт, 10 Нояб."));
            positions.add(new CheckPosition("Начало движения", "18:28 Вт, 10 Нояб."));
            positions.add(new CheckPosition("Фиксированная стоимость", "750 руб."));
            positions.add(new CheckPosition("Закрепление багажа в салоне", "100 руб."));
            positions.add(new CheckPosition("Провоз животных", "150 руб."));
            positions.add(new CheckPosition("Кузов \"Универсал\"", "200 руб."));
            positions.add(new CheckPosition("Детское кресло", "150 руб."));
            positions.add(new CheckPosition("Тариф", "Эконом"));

            Check check = new Check("1350 руб.", "150 баллов", positions);

            fragment = CheckFragment.newInstance("Чек", check);
        }

        if (!active)
            act();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act: {
                act();
            } break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void act() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);

        if (!active) {
            ft.add(R.id.content, fragment);
            active = true;
        } else {
            ft.remove(fragment);
            active = false;
        }

        ft.commit();
    }

}
