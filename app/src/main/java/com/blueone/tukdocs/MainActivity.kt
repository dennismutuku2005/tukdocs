package com.blueone.tukdocs

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blueone.tukdocs.ui.theme.TukdocsTheme

class MainActivity : ComponentActivity() {

    private var keepSplashOnScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }

        window.decorView.postDelayed({
            keepSplashOnScreen = false
        }, 1000)

        setContent {
            TukdocsTheme {
                OnboardingNavigation()
            }
        }
    }
}

@Composable
fun OnboardingNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate("terms")
                }
            )
        }
        composable("terms") {
            TermsScreen(
                onAgree = {
                    // Start HomePageActivity and finish MainActivity
                    val intent = Intent(context, HomePageActivity::class.java)
                    context.startActivity(intent)
                    (context as? MainActivity)?.finish()
                }
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top spacer
            Box(modifier = Modifier.weight(0.5f))

            // Content
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(3f)
            ) {
                // Image
                Image(
                    painter = painterResource(id = R.drawable.landingimage),
                    contentDescription = "TukDocs illustration",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                // Title
                Text(
                    text = "Get started with TukDocs today",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 48.dp)
                )

                // Subtitle
                Text(
                    text = "Open Free Ad Free",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Get Started Button
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = "Open Docs",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Bottom spacer
            Box(modifier = Modifier.weight(0.3f))
        }
    }
}

@Composable
fun TermsScreen(
    onAgree: () -> Unit
) {
    val scrollState = rememberScrollState()
    var agreedToTerms by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Button(
                onClick = onAgree,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                enabled = agreedToTerms,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = "Agree & Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Terms & Conditions",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Please read and agree to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Terms Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Last updated: ${java.time.LocalDate.now()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Terms sections
                TermsSection(
                    title = "1. Acceptance of Terms",
                    content = "By accessing and using TukDocs, you accept and agree to be bound by the terms and provision of this agreement."
                )

                TermsSection(
                    title = "2. Use License",
                    content = "Permission is granted to temporarily use TukDocs for personal, non-commercial transitory viewing only."
                )

                TermsSection(
                    title = "3. Privacy",
                    content = "Your privacy is important to us. TukDocs is designed to be open and free, and we do not collect personal data without your consent."
                )

                TermsSection(
                    title = "4. Document Handling",
                    content = "TukDocs allows you to open, read, and organize your documents. You retain all rights to your documents."
                )

                TermsSection(
                    title = "5. No Warranty",
                    content = "TukDocs is provided 'as is' without any warranties, expressed or implied."
                )
            }

            // Agreement Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "I have read and agree to the Terms & Conditions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun TermsSection(title: String, content: String) {
    Column(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    TukdocsTheme {
        OnboardingScreen(onGetStarted = {})
    }
}

@Preview(showBackground = true)
@Composable
fun TermsScreenPreview() {
    TukdocsTheme {
        TermsScreen(onAgree = {})
    }
}