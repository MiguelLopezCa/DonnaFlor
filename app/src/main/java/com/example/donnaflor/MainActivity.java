package com.example.donnaflor;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private EditText editTextUsuario, editTextContra;
    private Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsuario = findViewById(R.id.txtUsuario);
        editTextContra = findViewById(R.id.txtContra);
        btnIngresar = findViewById(R.id.btnIngresar);




        //evento clic del boton ingresar
        btnIngresar.setOnClickListener(v -> {

            String usuario = editTextUsuario.getText().toString();
            String contrasena = editTextContra.getText().toString();

            // Validar usuario y contrasenna
            if (usuario.equals("a") && contrasena.equals("a")) {
                startActivity(new Intent(this, ProductosOfertados.class));
            } else {
                if (!usuario.equals("a") && !contrasena.equals("a")) {
                    Toast.makeText(this, "El usuario y la contraseña no corresponden", Toast.LENGTH_LONG).show();
                } else if (!usuario.equals("a")) {
                    Toast.makeText(this, "El usuario no corresponde", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "La contraseña no corresponde", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}