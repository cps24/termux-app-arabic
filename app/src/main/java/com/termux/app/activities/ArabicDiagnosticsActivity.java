package com.termux.app.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.TermuxAppSharedPreferences;

import java.io.File;

public class ArabicDiagnosticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arabic_diagnostics);

        TextView tvTashkeel = findViewById(R.id.test_case_tashkeel);
        TextView tvMixed = findViewById(R.id.test_case_mixed);
        TextView tvPunctuation = findViewById(R.id.test_case_punctuation);
        TextView tvLigatures = findViewById(R.id.test_case_ligatures);
        TextView tvInfo = findViewById(R.id.tv_info_text);
        Button btnCopy = findViewById(R.id.btn_copy);
        Button btnBack = findViewById(R.id.btn_back);

        // Standard monospace font styling for diagnostic comparison
        Typeface monospaceTypeface = Typeface.MONOSPACE;
        tvTashkeel.setTypeface(monospaceTypeface);
        tvMixed.setTypeface(monospaceTypeface);
        tvPunctuation.setTypeface(monospaceTypeface);
        tvLigatures.setTypeface(monospaceTypeface);

        // Displaying raw test case strings directly from resources (no layout reordering)
        tvTashkeel.setText(getString(R.string.arabic_diagnostics_sample_tashkeel_text));
        tvMixed.setText(getString(R.string.arabic_diagnostics_sample_mixed_text));
        tvPunctuation.setText(getString(R.string.arabic_diagnostics_sample_punctuation_text));
        tvLigatures.setText(getString(R.string.arabic_diagnostics_sample_ligatures_text));

        // Showing preferences details for Theme & custom font presence
        TermuxAppSharedPreferences preferences = TermuxAppSharedPreferences.build(this);
        String theme = (preferences != null) ? preferences.getTerminalTheme() : "default";

        File fontFile = new File("/data/data/com.termux/files/home/.termux/font.ttf");
        String fontInfo = fontFile.exists() && fontFile.length() > 0 ? "Custom font.ttf found" : "Default system font";

        String infoText = "Active Font: " + fontInfo + "\nActive Theme: " + theme;
        tvInfo.setText(infoText);

        // Action: Copy raw test cases to clipboard
        btnCopy.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Tashkeel: ").append(getString(R.string.arabic_diagnostics_sample_tashkeel_text)).append("\n");
            sb.append("Mixed: ").append(getString(R.string.arabic_diagnostics_sample_mixed_text)).append("\n");
            sb.append("Punctuation: ").append(getString(R.string.arabic_diagnostics_sample_punctuation_text)).append("\n");
            sb.append("Ligatures: ").append(getString(R.string.arabic_diagnostics_sample_ligatures_text));

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Arabic Display Test Cases", sb.toString());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Test cases copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
