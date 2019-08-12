package br.com.digitalhouse.firepizzaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private static final String TAG = "cadastrarActivity";

    private TextInputEditText emailEditText;
    private TextInputEditText senhaEditText;
    private TextInputEditText usuarioEditText;
    private FirebaseAuth firebaseAuth;
    private Button cadastrarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        firebaseAuth = FirebaseAuth.getInstance();

        usuarioEditText = findViewById(R.id.cadastro_usuario_edit_text);
        emailEditText = findViewById(R.id.cadastro_email_edit_text);
        senhaEditText = findViewById(R.id.cadastro_senha_edit_text);
        cadastrarButton = findViewById(R.id.cadastrar_button_id);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastrarUsuario();
            }
        });


    }

    private void CadastrarUsuario(){

        String email = emailEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Usuario cadastrado com sucesso");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UsuarioCadastrado();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Erro ao cadastrar o usuario", task.getException());
                            Toast.makeText(CadastroActivity.this, "Não foi possivel cadastrar o usuario",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void UsuarioCadastrado(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
