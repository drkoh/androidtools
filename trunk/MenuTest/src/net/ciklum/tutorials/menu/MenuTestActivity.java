package net.ciklum.tutorials.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MenuTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
        
    static final int MENU_NEW_GAME = 1;
    static final int MENU_QUIT = 2;

        /* Creates the menu items */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    		boolean result = super.onCreateOptionsMenu(menu);  	
            menu.add(0, MENU_NEW_GAME, 0, "New Game");
            menu.add(0, MENU_QUIT, 0, "Quit");
            return result;
        }

        /* Handles item selections */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case MENU_NEW_GAME:
                newGame();
                return true;
            case MENU_QUIT:
                quit();
                return true;
            default:
            return false;
                	
            }
        }
        
        private void newGame()
        {
        	TextView txtView = new TextView (this);
            txtView.setText("New game is started");
            setContentView(txtView);
        }
        
        private void quit()
        {
        	TextView txtView = new TextView (this);
            txtView.setText("Quit the application");
            setContentView(txtView);
        }
    
    
}