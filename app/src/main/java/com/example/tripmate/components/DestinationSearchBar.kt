package com.example.tripmate.components

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.tripmate.R

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun DestinationSearchBar(value: String, onValueChange: (String) -> Unit) {

    
    // var query by rememberSaveable { mutableStateOf("") }
    
            OutlinedTextField(
            colors =
                OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedLeadingIconColor = Color.Black,
                ),
            value = value,
            onValueChange = onValueChange,
            label = { Text("Rechercher un destination") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "Recherche",
                )
            },

            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

   
    
    
}
