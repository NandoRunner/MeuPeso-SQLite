package fandradetecinfo.com.meupeso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import fandradetecinfo.com.meupeso.Views.BalancaDigitalFrag01;
import fandradetecinfo.com.meupeso.Views.BalancaDigitalFrag02;
import fandradetecinfo.com.meupeso.Views.BalancaDigitalFrag03;
import fandradetecinfo.com.meupeso.Views.BalancaDigitalFrag04;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends FragmentActivity {

    public static String usuario = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        SQLiteStudioService.instance().setPort(9999);
        SQLiteStudioService.instance().setPassword("12345678");
        SQLiteStudioService.instance().start(this);
    }

    @Override
    protected void onDestroy() {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return BalancaDigitalFrag01.newInstance("Cadastro");
                case 1:
                    return BalancaDigitalFrag02.newInstance("Registros do usuário: ");
                case 2:
                    return BalancaDigitalFrag03.newInstance("Médias do usuário: ");
                case 3:
                    return BalancaDigitalFrag04.newInstance("Totalizadores");
                default:
                    return BalancaDigitalFrag02.newInstance("Registros do usuário: ");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}
