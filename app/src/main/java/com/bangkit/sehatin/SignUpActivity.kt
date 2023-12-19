package com.bangkit.sehatin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUp: Button = findViewById(R.id.NextStep)
        btnSignUp.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextUsername: EditText = findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)

        // Validasi email dan password
        val email = editTextEmail.text.toString()
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Data valid, lanjut ke tahap selanjutnya
            val intent = Intent(this, InformationActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("name", username)
            intent.putExtra("password", password)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}
