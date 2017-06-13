package shuja1497.bitsandpizzasmaterial;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private String[] titles;
    private ListView drawerList;
    private android.widget.ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;



    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectItem(position);
        }
    };


    private void selectItem(int position){

        currentPosition = position;
        Fragment fragment;
        switch (position){
            case 1:
                fragment = new PizzaMaterialFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();



        }

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//        This adds a tag of“visible_fragment” to the fragment as it’s added to the back stack.
        ft.replace(R.id.main_content, fragment,"visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position) {

        String title;
        if (position == 0){
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,titles);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }
        else
            selectItem(0);


        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer){

            //Called when a drawer has settled in a completely closed state
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                FragmentManager fragMan = getFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("visible_fragment");



                if (fragment instanceof TopFragment) {
                    currentPosition = 0;
                }
                if (fragment instanceof PizzaMaterialFragment) {
                           currentPosition = 1;
                }
                if (fragment instanceof PastaFragment) {
                    currentPosition = 2;
                }
                if (fragment instanceof StoresFragment) {
                    currentPosition = 3;
                }
                setActionBarTitle(currentPosition);
//                Set the action bar title and highlight the correct item in the drawer ListView
                drawerList.setItemChecked(currentPosition, true);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       /* Save the state of currentPosition if
        the activity’s going to be destroyed.*/
        outState.putInt("position", currentPosition);
    }

    //Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (android.widget.ShareActionProvider)menuItem.getActionProvider();
        setIntent("This is my app .. plz download it ..else u will die .");

        return super.onCreateOptionsMenu(menu);
    }
    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.settings:
                return true;

            case R.id.create_menu:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_share:


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*You need to add this method to
        your activity so that the state of
        the ActionBarDrawerToggle is in
        sync with the state of the drawer.*/
// Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /*You need to add this method
        to your activity so that any
        configuration changes get passed
        to the ActionBarDrawerToggle*/

        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
