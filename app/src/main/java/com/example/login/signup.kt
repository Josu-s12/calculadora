package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import android.content.Intent
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editName = findViewById(R.id.edt_nombre)
        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btn_SingUp)

        btnSignUp.setOnClickListener{
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            singUp(name,email,password);
        }
    }

    private  fun singUp( name: String,email: String, password: String){
        //logica para crear usuarios
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@signup, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }else{
                    Toast.makeText(
                        this@signup,
                        "ha ocurrido un error.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun addUserToDatabase (name: String,email: String, uid: String){

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}