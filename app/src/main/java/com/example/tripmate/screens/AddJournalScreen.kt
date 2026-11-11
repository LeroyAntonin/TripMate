package com.example.tripmate.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tripmate.R
import androidx.compose.ui.res.painterResource
import com.example.tripmate.api.DatabaseApi
import com.example.tripmate.model.NewJournal
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun AddJournalScreen(
    onJournalAdded: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var ville by rememberSaveable { mutableStateOf("") }
    var pays by rememberSaveable { mutableStateOf("") }
    var dateArrivee by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var dateDepart by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var avis by rememberSaveable { mutableStateOf("") }
    var isSaving by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
            if (imageUrl.isBlank()) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Image placeholder du journal",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Aperçu de l'image du journal",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background),
                    fallback = painterResource(R.drawable.ic_launcher_background),
                )
            }

            JournalField(
                value = ville,
                onValueChange = { ville = it },
            label = "Ville",
            )
        JournalField(
            value = pays,
            onValueChange = { pays = it },
            label = "Pays",
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DateField(
                value = dateArrivee,
                onValueChange = { dateArrivee = formatDateInput(it) },
                label = "Date d'arrivée",
                modifier = Modifier.weight(1f),
            )
            DateField(
                value = dateDepart,
                onValueChange = { dateDepart = formatDateInput(it) },
                label = "Date de départ",
                modifier = Modifier.weight(1f),
            )
        }
        JournalField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = "URL de l'image",
        )
        JournalField(
            value = avis,
            onValueChange = { avis = it },
            label = "Avis",
            singleLine = false,
        )
        if (error != null) {
            Text(error!!)
        }
        Button(
            onClick = {
                if (ville.isBlank() || pays.isBlank() || dateArrivee.text.isBlank() ||
                    dateDepart.text.isBlank() || avis.isBlank()
                ) {
                    error = "Veuillez remplir tous les champs obligatoires."
                } else if (!isDateFormatValid(dateArrivee.text) || !isDateFormatValid(dateDepart.text)) {
                    error = "Les dates doivent être au format dd/mm/yy ou dd/mm/yyyy."
                } else {
                    coroutineScope.launch {
                        isSaving = true
                        error = null
                        try {
                            DatabaseApi.addJournal(
                                NewJournal(
                                    id = 0,
                                    ville = ville.trim(),
                                    pays = pays.trim(),
                                    dateArrivee = dateArrivee.text.trim(),
                                    dateDepart = dateDepart.text.trim(),
                                    imageUrl = imageUrl.trim(),
                                    avis = avis.trim(),
                                )
                            )
                            onJournalAdded()
                        } catch (exception: Exception) {
                            error = "Impossible d'ajouter le journal : ${exception.message}"
                        } finally {
                            isSaving = false
                        }
                    }
                }
            },
            enabled = !isSaving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (isSaving) {
                CircularProgressIndicator()
            } else {
                Text("Ajouter")
            }
        }
    }
}

private fun isDateFormatValid(date: String): Boolean =
    date.matches(Regex("""\d{2}/\d{2}/\d{2}(\d{2})?"""))

private fun formatDateInput(input: TextFieldValue): TextFieldValue {
    val digits = input.text.filter(Char::isDigit).take(8)
    val formatted = buildString {
        append(digits.take(2))
        if (digits.length >= 2) {
            append('/')
        }
        append(digits.drop(2).take(2))
        if (digits.length >= 4) {
            append('/')
        }
        append(digits.drop(4))
    }
    return TextFieldValue(
        text = formatted,
        selection = TextRange(formatted.length),
    )
}

@Composable
private fun DateField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Composable
private fun JournalField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
    )
}
