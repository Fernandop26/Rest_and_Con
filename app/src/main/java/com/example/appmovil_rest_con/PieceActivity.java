package com.example.appmovil_rest_con;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.widget.TextView;

public class PieceActivity extends AppCompatActivity {
    String id;
    TextView idLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_piece);
        getSupportActionBar().hide();

        id = getIntent().getStringExtra("id");
        idLayout = (TextView) findViewById(R.id.piece_name);
        idLayout.setText(id);


    }
}
