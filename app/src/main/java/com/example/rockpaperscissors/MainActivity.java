package com.example.rockpaperscissors;

import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView playerChoice;
    private ImageView cpuChoice;
    private TextView playerPoints;
    private TextView draws;
    private TextView cpuPoints;
    private ImageView p1;
    private ImageView p2;
    private ImageView p3;
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;

    private ImageButton selectRock;
    private ImageButton selectPaper;
    private ImageButton selectScissors;
    private int playerLives = 3;
    private int cpuLives = 3;
    private int jatekosPont = 0;
    private int gepPont = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init() { //API 26-ot használtam, Pixel 2-vel mert otthoni gépen nem tudtam, melyiket használjuk iskolai gépeken. Ha valamit eltérően csináltam, (pl. API verzió vagy eszköz) megköszönném, ha Teams visszajelzésben megírnák a helyes verziót vagy eszközöket.
        //válaszok
        playerChoice = findViewById(R.id.playerChoice);
        cpuChoice = findViewById(R.id.cpuChoice);
        //pontok
        playerPoints = findViewById(R.id.playerPoints);
        cpuPoints = findViewById(R.id.cpuPoints);
        //életek
        p1 = findViewById(R.id.P1);
        p2 = findViewById(R.id.P2);
        p3 = findViewById(R.id.P3);

        c1 = findViewById(R.id.C1);
        c2 = findViewById(R.id.C2);
        c3 = findViewById(R.id.C3);
        //gombok
        selectRock = findViewById(R.id.selectRock);
        selectPaper = findViewById(R.id.selectPaper);
        selectScissors = findViewById(R.id.selectScissors);
        //alapértékék
        playerLives = 3;
        cpuLives = 3;
        p1.setImageResource(R.drawable.heart2);
        p2.setImageResource(R.drawable.heart2);
        p3.setImageResource(R.drawable.heart2);
        c1.setImageResource(R.drawable.heart2);
        c2.setImageResource(R.drawable.heart2);
        c3.setImageResource(R.drawable.heart2);

        selectRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRound(0);
            }
        });
        selectPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRound(1);
            }
        });
        selectScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRound(2);
            }
        });
    }
    private void newRound(int index) {
        Random random = new Random();
        int cpu = random.nextInt(3);

        setImages(index, cpu);
        System.out.println("player:" + index);
        System.out.println("cpu:" + cpu);
        System.out.println("winner:" + decideWinner(index, cpu));
        int winner = decideWinner(index, cpu);
        if (winner == 1) {

            cpuLives--;

        } if (winner == 2) {

            playerLives--;

        }
        updateLiveImages();

    }
    private void setImages(int player, int cpu) {
        int[] srcset = {R.drawable.rock, R.drawable.paper, R.drawable.scissors};
        playerChoice.setImageResource(srcset[player]);
        cpuChoice.setImageResource(srcset[cpu]);
    }
    private int decideWinner(int player, int cpu) {
        //1 kő 2 papír 3 olló
        int winner = 3;
        player = player+1;
        cpu = cpu+1;
        if (player == cpu) {
            winner = 3;
            System.out.println("Döntetlen!");

        } if (player == 1 && cpu == 2) {
            winner = 2;
        } if (player == 1 && cpu == 3) {
            winner = 1;
        } if (player == 2 && cpu == 1) {
            winner = 1;
        } if (player == 2 && cpu == 3) {
            winner = 2;
        } if (player == 3 && cpu == 1) {
            winner = 2;
        } if (player == 3 && cpu == 2) {
            winner = 1;
        }
        return winner;
    }
    private void updateLiveImages() {
        int h1 = R.drawable.heart1;
        int h2 = R.drawable.heart2;
        if (playerLives == 2) {
            p3.setImageResource(h1);
        }if (playerLives == 1) {
            p2.setImageResource(h1);
        }if (playerLives == 0) {
            p1.setImageResource(h1);
            gepPont += 1;
            cpuPoints.setText(Integer.toString(gepPont));
            new AlertDialog.Builder(MainActivity.this).setMessage("Vesztettél! Szeretnél új játékot?").setCancelable(false).setPositiveButton("Igen", (dialog, which) -> {init();}).setNegativeButton("Nem", (dialog, which) -> {finish();}).create().show();
        }if (cpuLives == 2) {
            c3.setImageResource(h1);
        }if (cpuLives == 1) {
            c2.setImageResource(h1);
        }if (cpuLives == 0) {
            c1.setImageResource(h1);
            jatekosPont += 1;
            playerPoints.setText(Integer.toString(jatekosPont));
            new AlertDialog.Builder(MainActivity.this).setMessage("Nyertél! Szeretnél új játékot?").setCancelable(false).setPositiveButton("Igen", (dialog, which) -> {init();}).setNegativeButton("Nem", (dialog, which) -> {finish();}).create().show();
        }
    }
}

