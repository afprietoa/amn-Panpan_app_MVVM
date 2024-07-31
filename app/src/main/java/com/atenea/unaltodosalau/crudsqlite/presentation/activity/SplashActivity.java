package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.atenea.unaltodosalau.crudsqlite.R;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private static final long SPLASH_DURATION = 5000; // 5 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ocultar la barra de estado de manera compatible con múltiples versiones de Android
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        // Inicializar LottieAnimationView
        animationView = findViewById(R.id.animationView);
        animationView.playAnimation();
        animationView.setRepeatCount(0);

        // Configurar Handler para cambiar de actividad después de 5 segundos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animationView.isAnimating()) {
                    // En caso de que la animación siga corriendo, la detenemos
                    animationView.cancelAnimation();
                }
                startNextActivity();
            }
        }, SPLASH_DURATION);

        // Configurar Listener para detectar el final de la animación
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {
                // No action needed
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                startNextActivity();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {
                // No action needed
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {
                // No action needed
            }
        });
    }

    private void startNextActivity() {
        Intent intent = new Intent(SplashActivity.this, RolesActivity.class);
        startActivity(intent);
        finish();
    }
}