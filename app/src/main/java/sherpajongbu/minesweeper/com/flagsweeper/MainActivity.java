package sherpajongbu.minesweeper.com.flagsweeper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity{
    LinearLayout areana;
    ImageView[][] tiles;
    LinearLayout[] rows;
    TextView uScore,oScore;
    Node[][] n;
    Button restart;
    int userPoints;
    int highestPoints=0;
    int opponentPoints=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uScore=(TextView)findViewById(R.id.userScore);
        oScore=(TextView)findViewById(R.id.oppScore);
        oScore.setText(String.valueOf(highestPoints));
        restart=(Button)findViewById(R.id.restart);
        userPoints=0;
        n=new Node[32][32];//creating a node for data structure
        areana=(LinearLayout)findViewById(R.id.areana);
        drawAreana();//to draw the board
        plantMines();//to plant the mines
        setMinesCount();//to set the mines count

        restart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCreate(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //to draw the playing board
    public void drawAreana(){
        int counter=0;
        rows=new LinearLayout[30];
        tiles=new ImageView[30][30];

        LinearLayout.LayoutParams rowViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams tileViewParams = new LinearLayout.LayoutParams(100,100);
        tileViewParams.setMargins(2,2,2,2);
        for(int i=0;i<30;i++){
            //to add rows
            rows[i]=new LinearLayout(this);
            rows[i].setLayoutParams(rowViewParams);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            rows[i].setBackgroundColor(Color.YELLOW);
            for(int j=0;j<30;j++){
                //to add the tiles in each row
                tiles[i][j]=new ImageView(this);
                tiles[i][j].setId(counter++);
                tiles[i][j].setLayoutParams(tileViewParams);
                tiles[i][j].setBackgroundColor(Color.GRAY);
                tiles[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        check(v);

                    }
                });
                rows[i].addView(tiles[i][j]);

            }
            //adding each row in the LinearLayout Areana
            areana.addView(rows[i]);
        }
    }

    public void plantMines(){
        int counter=0;
        for (int i = 0; i <32; i++) {
            for (int j = 0; j <32; j++) {
                n[i][j]=new Node();//creating a node for data structure

                if(Math.random() < 0.1){//if number between 0 and 1 is less than 2, we plant the mine
                    n[i][j].setMine(true);
                }else {//now we set the flag to the nodes with no bomb with the better probability than planting the mine
                    if (Math.random() < 0.3) {//if number between 0 and 1 is less than 3, we plant the bomb
                        n[i][j].setFlag(true);
                    }
                }

            }
        }
    }

    public void setMinesCount() {
        int index=0;
        int mCount=0;

        for (int i = 0; i <30; i++) {
            for (int j = 0; j <30; j++) {

                //for first row
                if(i==0&&j<30&&j>0) {//can't go up
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i; ii <= i + 1; ii++) {
                            for (int jj = j - 1; jj <= j + 1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(i==0&&j==0) {//can;t go up and left
                        if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                            for (int ii = i; ii <= i + 1; ii++) {
                                for (int jj = j; jj <= j + 1; jj++) {
                                    if (n[ii][jj].getMine()) {
                                        mCount++;
                                    }
                                }
                            }
                            n[i][j].setMineCount(mCount);
                            mCount = 0;
                        }
                }else if(i==0&&j==29) {//cant go up and right
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i; ii++) {
                            for (int jj = j; jj <= j + 1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(i==29&&j==29) {//can't go right and down
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i; ii++) {
                            for (int jj = j-1; jj <= j; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(i==29&&j==0) {//cant go left and down
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i; ii++) {
                            for (int jj = j; jj <= j+1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(j==0) {//can't go left
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i+1; ii++) {
                            for (int jj = j; jj <= j+1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(j==29) {//can't go right
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i+1; ii++) {
                            for (int jj = j-1; jj <= j; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else if(i==29&&j>0&&j<30) {//can't go down
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i-1; ii <= i; ii++) {
                            for (int jj = j-1; jj <= j+1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }else {
                    // (ii, jj) indexes neighboring cells
                    if (!n[i][j].getMine() && !n[i][j].getFlag()) {
                        for (int ii = i - 1; ii <= i + 1; ii++) {
                            for (int jj = j - 1; jj <= j + 1; jj++) {
                                if (n[ii][jj].getMine()) {
                                    mCount++;
                                }
                            }
                        }
                        n[i][j].setMineCount(mCount);
                        mCount = 0;
                    }
                }
            }
        }
    }

    public void check(View v){
        int index= v.getId();
        int r = (int)(index / 30);
        int c = index - (r * 30);
        if(n[r][c].getMine()==true){//if clicked display all mines
            tiles[r][c].setBackgroundColor(Color.RED);
            for (int i = 0; i <30; i++) {
                for (int j = 0; j < 30; j++) {
                    if(n[i][j].getMine()==true)
                        tiles[i][j].setImageResource(R.drawable.bomb);
                }
            }
            stopGame();//stops game;
            if(userPoints>highestPoints){
                highestPoints=userPoints;
                oScore.setText(String.valueOf(highestPoints));
            }
        }
        else if(n[r][c].getFlag()==true){
            v.setBackgroundResource(R.drawable.flag);
            uScore.setText(String.valueOf(++userPoints));
        }else{
            expand(r,c);
        }
        n[r][c].setOpened(true);
    }

    public void stopGame(){
        for(int i=0;i<30;i++) {
            for (int j = 0; j < 30; j++) {
                tiles[i][j].setOnClickListener(null);
            }
        }
    }
    public void expand(int r,int c){
        if(r>=0&&r<=29&&c>=0&&c<=29 && n[r][c].getChecked()==false) {//boundaries
            n[r][c].setChecked(true);//marking that we visited the node already
            if (n[r][c].getMineCount() > 0) {
                if(n[r][c].getMineCount()==1){
                    tiles[r][c].setBackgroundResource(R.drawable.one);
                }else if(n[r][c].getMineCount()==2){
                    tiles[r][c].setBackgroundResource(R.drawable.two);
                }else if(n[r][c].getMineCount()==3){
                    tiles[r][c].setBackgroundResource(R.drawable.three);
                }else if(n[r][c].getMineCount()==4){
                    tiles[r][c].setBackgroundResource(R.drawable.four);
                }else if(n[r][c].getMineCount()==5){
                    tiles[r][c].setBackgroundResource(R.drawable.five);
                }else if(n[r][c].getMineCount()==6){
                    tiles[r][c].setBackgroundResource(R.drawable.six);
                }else if(n[r][c].getMineCount()==7){
                    tiles[r][c].setBackgroundResource(R.drawable.seven);
                }else{
                    tiles[r][c].setBackgroundResource(R.drawable.eight);
                }

            }else if(n[r][c].getMine()==true){
                //do nothing
            }else if(n[r][c].getFlag()==true){
                //do nothing
            }  else{
                tiles[r][c].setBackgroundColor(Color.GREEN);//testing
                expand(r - 1, c - 1);
                expand(r, c - 1);
                expand(r + 1, c - 1);
                expand(r - 1, c);
                expand(r + 1, c);
                expand(r-1,c+1);
                expand(r-1,c+1);
                expand(r+1,c+1);
            }
        }

    }

}
