package com.example.tripmate.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tripmate.model.Category

@Composable
fun ProfileScreen(
    tags: List<String>,
    categories: List<Category>,
    categoriesLoading: Boolean,
    categoriesError: String?,
    selectedTags: Set<String>,
    onTagClick: (String) -> Unit,
    onRetry: () -> Unit,
) {
    val visibleCategories = categories.map { category ->
        category to category.tags.filter { it in tags }
    }.filter { (_, categoryTags) -> categoryTags.isNotEmpty() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        Column(
            modifier = Modifier.padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "Mes tags de destination",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Choisis tes envies pour personnaliser l’accueil.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (categoriesLoading) {
            Text(
                text = "Chargement des catégories…",
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (categoriesError != null) {
            Text(
                text = categoriesError,
                color = MaterialTheme.colorScheme.error,
            )
            TextButton(onClick = onRetry) {
                Text("Réessayer")
            }
        }

        visibleCategories.forEachIndexed { index, (category, categoryTags) ->
            if (index > 0) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "${category.ordre}. ${category.nom}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    categoryTags.forEach { tag ->
                        val selected = tag in selectedTags
                        FilterChip(
                            selected = selected,
                            onClick = { onTagClick(tag) },
                            label = {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelLarge,
                                )
                            },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.secondaryContainer
                                    .copy(alpha = if (selected) 1f else 0.2f),
                            ),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme
                                    .secondaryContainer.copy(alpha = 0.12f),
                                labelColor = MaterialTheme.colorScheme.onSurface,
                                selectedContainerColor =
                                    MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor =
                                    MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                        )
                    }
                }
            }
        }
    }
}
