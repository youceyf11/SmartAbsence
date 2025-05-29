package com.example.emsismartpresence; // Your package name

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// Import ScrollView if you want to programmatically scroll the chat
// import android.widget.ScrollView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Activity_dashboard extends AppCompatActivity {

    // CardView declarations from your original file
    private CardView Location, card2, history, card4, card5, card6, card7;
    // CardView for the AI Assistant
    private CardView aiAssistantCard;
    private CardView cardAbsence;
    private TextView adminName;

    // API Key from your Assistant_virtuel.java
    // IMPORTANT: For a production app, this is a security risk.
    // Consider more secure ways to handle API keys.
    private final String API_KEY = "AIzaSyAn_-j1BgNIvtZ8fpt9po5klb4xfDqBdzY"; // From your Assistant_virtuel.java

    // OkHttpClient - create it once and reuse it for efficiency
    private final OkHttpClient httpClient = new OkHttpClient();

    // The @SuppressLint("MissingInflatedId") was in your original Activity_dashboard.java.
    // If IDs are indeed missing from your XML, the app might crash at runtime.
    // This annotation only suppresses the lint warning.
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure R.layout.dashboard is the correct XML file for your dashboard screen
        setContentView(R.layout.dashboard);

        // Initialisation des vues (finding views by their ID)
        Location = findViewById(R.id.Location);
        card2 = findViewById(R.id.card2);
        history = findViewById(R.id.history);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        adminName = findViewById(R.id.dashboard_adminName);

        // This ID (R.id.ai_assistant_card) must exist in your dashboard.xml layout file
        aiAssistantCard = findViewById(R.id.ai_assistant_card);

        if (adminName != null) {
            adminName.setText("Mr/Mme"); // Setting admin name as in your original code
        }

        // Setting up click listeners for each card
        // Added null checks before setting listeners as a good practice
        if (Location != null) {
            Location.setOnClickListener(v -> {
                // Intent to start Maps Activity (ensure Maps.class exists)
                Intent golocation = new Intent(Activity_dashboard.this, Maps.class);
                startActivity(golocation);
            });
        }

        if (card2 != null) {
            card2.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_dashboard.this, RattrapageActivity.class);
                startActivity(intent);
            });
        }

        if (history != null) {
            history.setOnClickListener(v -> {
                Toast.makeText(Activity_dashboard.this, "History Clicked", Toast.LENGTH_SHORT).show();
            });
        }

        if (card4 != null) {
            card4.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_dashboard.this, Activity_reclamation.class);
                startActivity(intent);
            });
        }

        if (card5 != null) {
            card5.setOnClickListener(v -> {
                Toast.makeText(Activity_dashboard.this, "Card 5 (A proximité) Clicked", Toast.LENGTH_SHORT).show();
            });
        }

        if (card6 != null) {
            card6.setOnClickListener(v -> {
                Toast.makeText(Activity_dashboard.this, "Card 6 (Planning) Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_dashboard.this, PlanningActivity.class);
                startActivity(intent);
            });
        }

        if (card7 != null) {
            card7.setOnClickListener(v -> {
                Toast.makeText(Activity_dashboard.this, "Card 7 (Documents) Clicked", Toast.LENGTH_SHORT).show();
            });
        }

        // Set OnClickListener for the AI Assistant Card
        if (aiAssistantCard != null) {
            aiAssistantCard.setOnClickListener(v -> showAiAssistantDialog());
        } else {
            // This Toast helps debug if 'ai_assistant_card' is not found in your dashboard.xml
            Toast.makeText(this, "AI Assistant Card (R.id.ai_assistant_card) not found!", Toast.LENGTH_LONG).show();
        }
        cardAbsence = findViewById(R.id.card_absence);
        cardAbsence.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_dashboard.this, PresenceActivity.class);
            startActivity(intent);
        });

    }

    private void showAiAssistantDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        // Ensure R.layout.dialog_ai_assistant is your correct dialog layout XML file
        View dialogView = inflater.inflate(R.layout.dialog_ai_assistant, null);
        builder.setView(dialogView);
        builder.setTitle("AI Assistant"); // Title for the dialog

        // Finding UI elements within the dialog's layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText dialogEditMessage = dialogView.findViewById(R.id.dialog_prompt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final TextView dialogTxtResponse = dialogView.findViewById(R.id.dialog_geminianswer);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button dialogBtnSend = dialogView.findViewById(R.id.dialog_btnSend);

        // Check if dialog UI elements were found correctly
        if (dialogEditMessage == null || dialogTxtResponse == null || dialogBtnSend == null) {
            Toast.makeText(this, "Error: Dialog UI elements not found in dialog_ai_assistant.xml.", Toast.LENGTH_LONG).show();
            return; // Exit if essential UI elements are missing
        }

        // Set an initial greeting or clear previous text in the dialog's response view
        dialogTxtResponse.setText("AI: Hello! How can I assist you today?\n\n");

        // Create the AlertDialog object
        final AlertDialog alertDialog = builder.create();

        // Set click listener for the send button within the dialog
        dialogBtnSend.setOnClickListener(v -> {
            String userMessage = dialogEditMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                appendToChat(dialogTxtResponse, "You: " + userMessage); // Display user's message
                dialogEditMessage.setText(""); // Clear the input field
                sendMessageToGeminiInDialog(userMessage, dialogTxtResponse); // Send message to AI
            } else {
                Toast.makeText(Activity_dashboard.this, "Please enter a message.", Toast.LENGTH_SHORT).show();
            }
        });

        // Add a "Close" button to the dialog.
        // This is set on the builder *before* calling create() or show().
        builder.setNegativeButton("Close", (d, which) -> d.dismiss());

        // Show the dialog
        // If you called setNegativeButton on the builder, you don't need to call alertDialog.setButton again.
        // Just show the dialog created by builder.create().
        alertDialog.show();
    }

    // Appends a message to the chat TextView in the dialog
    private void appendToChat(TextView chatView, String message) {
        chatView.append(message + "\n\n");
        // Optional: If chatView is inside a ScrollView and you want to auto-scroll
        // View parent = (View) chatView.getParent();
        // if (parent instanceof ScrollView) {
        //     ScrollView scrollView = (ScrollView) parent;
        //     scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        // }
    }

    // Removes the "AI: Thinking..." message from the chat
    private void removeThinkingMessage(TextView chatView) {
        String currentText = chatView.getText().toString();
        String thinkingText = "AI: Thinking...\n\n";
        if (currentText.endsWith(thinkingText)) {
            chatView.setText(currentText.substring(0, currentText.length() - thinkingText.length()));
        }
    }

    // Sends message to Gemini API and updates dialog; adapted from your Assistant_virtuel.java
    private void sendMessageToGeminiInDialog(String message, TextView targetTxtResponse) {
        appendToChat(targetTxtResponse, "AI: Thinking..."); // Show thinking placeholder

        // Constructing the JSON payload for Gemini API
        JSONObject jsonPayload = new JSONObject();
        try {
            JSONArray contentsArray = new JSONArray();
            JSONObject contentItem = new JSONObject();
            JSONArray partsArray = new JSONArray();
            JSONObject part = new JSONObject();
            part.put("text", message); // User's message
            partsArray.put(part);
            contentItem.put("parts", partsArray);
            contentsArray.put(contentItem);
            jsonPayload.put("contents", contentsArray);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                removeThinkingMessage(targetTxtResponse);
                appendToChat(targetTxtResponse, "Error: Could not create request payload for AI.");
            });
            return;
        }

        RequestBody body = RequestBody.create(
                jsonPayload.toString(),
                MediaType.parse("application/json") // Content type for the request
        );

        // API URL from your Assistant_virtuel.java
        // IMPORTANT: Ensure "gemini-2.0-flash" is the correct and current model for your API key.
        // Google may update model names (e.g., "gemini-1.5-flash-latest", "gemini-pro").
        String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        // Perform network request on a background thread
        new Thread(() -> {
            try (Response response = httpClient.newCall(request).execute()) {
                final String responseBodyString = response.body() != null ? response.body().string() : null;

                // Update UI on the main thread
                runOnUiThread(() -> {
                    removeThinkingMessage(targetTxtResponse); // Remove "Thinking..."
                    if (response.isSuccessful() && responseBodyString != null) {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBodyString);
                            // Parsing the response structure as in your Assistant_virtuel.java
                            JSONArray candidates = jsonResponse.getJSONArray("candidates");
                            if (candidates.length() > 0) {
                                JSONObject firstCandidate = candidates.getJSONObject(0);
                                if (firstCandidate.has("content")) {
                                    JSONObject content = firstCandidate.getJSONObject("content");
                                    if (content.has("parts")) {
                                        JSONArray parts = content.getJSONArray("parts");
                                        if (parts.length() > 0) {
                                            String text = parts.getJSONObject(0).getString("text");
                                            appendToChat(targetTxtResponse, "AI: " + text.trim());
                                        } else {
                                            appendToChat(targetTxtResponse, "AI: Received empty parts in response.");
                                        }
                                    } else {
                                        appendToChat(targetTxtResponse, "AI: Response missing 'parts' field.");
                                    }
                                } else {
                                    // Check for safetyRatings if content is missing (blocked prompt)
                                    if (firstCandidate.has("safetyRatings")) {
                                        appendToChat(targetTxtResponse, "AI: The response was blocked due to safety concerns. Please rephrase your prompt.");
                                    } else {
                                        appendToChat(targetTxtResponse, "AI: Response missing 'content' field.");
                                    }
                                }
                            } else {
                                appendToChat(targetTxtResponse, "AI: No candidates found in response.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            appendToChat(targetTxtResponse, "AI: Error parsing AI response.");
                        }
                    } else {
                        // Handle API error response
                        String errorMessageToShow = "API Error (" + response.code() + "): " + response.message();
                        if (responseBodyString != null) {
                            try {
                                // Try to get a more specific error message from Gemini's JSON error response
                                JSONObject errorJson = new JSONObject(responseBodyString);
                                if (errorJson.has("error") && errorJson.getJSONObject("error").has("message")) {
                                    errorMessageToShow = "API Error: " + errorJson.getJSONObject("error").getString("message");
                                } else {
                                    errorMessageToShow += "\nDetails: " + responseBodyString; // Append raw details if specific message not found
                                }
                            } catch (JSONException e) {
                                // If errorDetails is not a JSON or doesn't have the expected structure
                                errorMessageToShow += "\nDetails: " + responseBodyString;
                            }
                        }
                        appendToChat(targetTxtResponse, errorMessageToShow);
                    }
                });
            } catch (IOException e) { // Catch network errors
                e.printStackTrace();
                runOnUiThread(() -> {
                    removeThinkingMessage(targetTxtResponse);
                    appendToChat(targetTxtResponse, "Network Error: " + e.getMessage());
                });
            }
        }).start();
    }

    // Inflates the menu for the activity (from your original Activity_dashboard.java)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ensure R.menu.menu exists in your res/menu folder
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Handles menu item selections (from your original Activity_dashboard.java)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_about) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String uid = user.getUid();
                String info = "Email : " + email + "\nUID : " + uid;
                new AlertDialog.Builder(this)
                        .setTitle("Informations utilisateur")
                        .setMessage(info)
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Informations utilisateur")
                        .setMessage("Aucun utilisateur connecté.")
                        .setPositiveButton("OK", null)
                        .show();
            }
            return true;
        } else if (id == R.id.item_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Voulez-vous vraiment vous déconnecter ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, Activity_signin.class));
                        finish();
                    })
                    .setNegativeButton("Non", null)
                    .show();
            return true;
        } else if (id == R.id.item_help) {
            Toast.makeText(this, "Sub Item 1 clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}