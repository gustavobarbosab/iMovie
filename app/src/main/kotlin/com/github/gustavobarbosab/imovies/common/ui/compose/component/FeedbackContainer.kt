package com.github.gustavobarbosab.imovies.common.ui.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.presentation.theme.Error
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun FeedbackContainer(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Error,
) {
    val context = LocalContext.current
    Surface(
        modifier
            .padding(MaterialTheme.spacing.medium)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth(),
    ) {
        Column(
            Modifier.padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = icon,
                contentDescription = context.getString(R.string.feedback_container_icon_content_description),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier.semantics {
                    contentDescription =
                        context.getString(R.string.feedback_container_message_content_description)
                },
                text = message,
                textAlign = TextAlign.Center,
            )

            OutlinedButton(
                modifier = Modifier.semantics {
                    contentDescription =
                        context.getString(R.string.feedback_container_button_content_description)
                },
                onClick = onRetry
            ) {
                Text(stringResource(R.string.detail_try_again_button))
            }
        }
    }
}


@Preview
@Composable
fun FeedbackContainerPreview() {
    FeedbackContainer(
        message = "An error occurred",
        onRetry = {},
    )
}