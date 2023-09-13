package com.example.diaryapp.presentation.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.diaryapp.utils.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import java.lang.Exception

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    authenticated: Boolean,
    loadingState: Boolean,
    messageBarState: MessageBarState,
    oneTapState: OneTapSignInState,
    onButtonClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface).statusBarsPadding().navigationBarsPadding(),
        content = {
//            AuthenticationContent(loadingState = loadingState, onButtonClicked = onButtonClicked)
            ContentWithMessageBar(messageBarState = messageBarState) {
                AuthenticationContent(
                    loadingState = loadingState,
                    onButtonClicked = onButtonClicked
                )
            }
        }
    )

    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = {tokenId ->
            Log.d("Auth", tokenId)
//            messageBarState.addSuccess("Successfully Authenticated!")
            onTokenIdReceived(tokenId)
        },
        onDialogDismissed = {message ->
            Log.d("Auth", message)
            onDialogDismissed(message)
            messageBarState.addError(Exception(message))
        }
    )
    
    LaunchedEffect(key1 = authenticated ) {
        if (authenticated) {
            navigateToHome()
        }
    }
}